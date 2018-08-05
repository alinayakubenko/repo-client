package com.ai.ally.githubclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ai.ally.githubclient.base.BaseFragment
import com.ai.ally.githubclient.ui.description.DescriptionFragment
import com.ai.ally.githubclient.ui.description.DescriptionPresenter
import com.ai.ally.githubclient.ui.repositories.RepositoriesFragment
import com.ai.ally.githubclient.ui.repositories.RepositoriesPresenter
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.ai.ally.githubclient.models.NewTokenRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.ai.ally.githubclient.utils.rest.Rest
import com.google.gson.JsonObject


class MainActivity : AppCompatActivity(), MainView, SharedPreferences.OnSharedPreferenceChangeListener {

    // TODO globally:
    // TODO mandatory
    // TODO Move all hardcoded strings to resource file
    // TODO add functionality presenting sorted and grouped repos
    // TODO add details for chosen repo
    // TODO add unit tests

    // TODO optional:
    // TODO implement rx
    // TODO save to DB and show data saved to DB

    private lateinit var token: JsonObject
    lateinit var sp: SharedPreferences
    override fun onLogin() {
        val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize" + "?client_id=" + resources.getString(R.string.clientid) + "&scope=repo&redirect_uri=" + resources.getString(R.string.redirecturi)))
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        //fragmentManager.beginTransaction().replace(android.R.id.content, RepositoriesFragment() as? Fragment).commit()
        // replaceFragment(RepositoriesFragment.newInstance(RepositoriesPresenter()), RepositoriesFragment.javaClass.canonicalName)
        // replaceFragment(AuthFragment.newInstance(AuthPresenter()), AuthFragment.javaClass.canonicalName)
    }

    override fun onDescriptionPage() {

        replaceFragment(DescriptionFragment.newInstance(DescriptionPresenter()), DescriptionFragment.javaClass.canonicalName)
    }

    override fun onReposPage() {
        replaceFragment(RepositoriesFragment.newInstance(RepositoriesPresenter()), RepositoriesFragment.javaClass.canonicalName)
    }

    private fun replaceFragment(fragment: BaseFragment, tag: String) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_container, fragment, tag)
                .addToBackStack(fragment.tag)
                .commit()
    }

    override fun onResume() {
        super.onResume()

        if ((sp.getString("access_token", "") == "")) { // TODO   token check and handling revoked access
            //To redirect to the GitHub login page is no access token retrieved and saved
            onLogin()
        } else {
            onReposPage()
        }

        // the intent filter defined in AndroidManifest handle the return from ACTION_VIEW intent
        val uri = intent.data
        if (uri != null && uri.toString().startsWith(resources.getString(R.string.redirecturi))) {

            // temporary access code
            val code = uri.getQueryParameter("code")
            Log.i("ONRESUME", "Non empty URI: " + code)
            if (code != null) {
                Log.i("ONRESUME", "CODE IS NOT NULL: " + code)
            // using code to request tokent
                Rest.retrofit.getAccessToken(BuildConfig.URL_TOKEN, "application/json", NewTokenRequest(code, "repo",
                        resources.getString(R.string.clientid), resources.getString(R.string.clientsecret), resources.getString(R.string.redirecturi))).enqueue(object: Callback<JsonObject> {

                    override fun onFailure(call: Call<JsonObject>, t: Throwable?) {
                        Log.i("ONRESUME", "On Response: failure "+ t.toString())
                    }

                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        Log.i("ONRESUME", "On Response: step in " + response!!.raw())
                        Log.i("ONRESUME", "On Response: step in body: " + response!!.body())

                        response!!.body()?.let { it ->
                            token = it
                            Log.i("ONRESUME", "On Response: step in body: TOKEN - " + token.get("access_token"))
                            sp.edit().putString("access_token", token.get("access_token").toString()).apply()
                            sp.edit().putString("oauth.loggedin", "true").apply()
                        }
                    }

                })
                Log.i("ONRESUME", "Token: " + sp.getString("oauth.accesstoken", ""))
                Log.i("ONRESUME", "User logged in: " + sp.getString("oauth.loggedin", ""))

            } else if (uri.getQueryParameter("error") != null) {
                // show an error message
                Toast.makeText(this, uri.getQueryParameter("error"), Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (intent.data.getQueryParameter("code") != "") {
           // onReposPage()
            Log.i("ONACTIVITYRES", "-----")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        sp.edit().remove("code").apply()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if ((sp.getString("oauth.loggedin", "") != "true")) { // TODO   token check and handling revoked access
            //To redirect to the GitHub login page if user is not authorized
            onLogin()
        } else {
            onReposPage()
        }
    }
}
