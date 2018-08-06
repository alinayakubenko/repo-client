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
import com.ai.ally.githubclient.models.NewTokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.ai.ally.githubclient.utils.rest.Rest
import com.google.gson.JsonObject


class MainActivity : AppCompatActivity(), MainView {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if ((sp.getString("access_token", "") == "")) { // TODO   token check and handling revoked access
            //To redirect to the GitHub login page is no access token retrieved and saved
            if (intent.data != null) {
                getToken(intent.data)
            } else {
                onLogin()
            }
        } else {
            onReposPage()
        }
    }

    override fun onLogin() {
        val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize" + "?client_id=" + resources.getString(R.string.clientid) + "&scope=repo&redirect_uri=" + resources.getString(R.string.redirecturi)))
        startActivity(intent)
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

    private fun getToken(uri: Uri?) {
        if (uri != null && uri.toString().startsWith(resources.getString(R.string.redirecturi))) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                Rest.retrofit.getAccessToken(BuildConfig.URL_TOKEN, "application/json", NewTokenRequest(code, "repo",
                        resources.getString(R.string.clientid), resources.getString(R.string.clientsecret), resources.getString(R.string.redirecturi))).enqueue(object : Callback<NewTokenResponse> {

                    override fun onFailure(call: Call<NewTokenResponse>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<NewTokenResponse>, response: Response<NewTokenResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let {
                                sp.edit().putString("access_token", it.accessToken).apply()
                                sp.edit().putString("oauth.loggedin", "true").apply()
                                Log.i("LOG", "Token: "+ it.accessToken)
                                onReposPage()
                            }
                        }
                    }
                })
            } else if (uri.getQueryParameter("error") != null) {
                Toast.makeText(this, uri.getQueryParameter("error"), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}