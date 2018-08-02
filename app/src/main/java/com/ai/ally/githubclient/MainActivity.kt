package com.ai.ally.githubclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ai.ally.githubclient.base.BaseFragment
import com.ai.ally.githubclient.ui.description.DescriptionFragment
import com.ai.ally.githubclient.ui.description.DescriptionPresenter
import com.ai.ally.githubclient.ui.authentication.AuthPresenter
import com.ai.ally.githubclient.ui.authentication.AuthFragment
import android.content.Intent
import android.net.Uri



class MainActivity : AppCompatActivity(), MainView {
    override fun onLogin() {
        val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize" + "?client_id=" + resources.getString(R.string.clientid) + "&scope=repo&redirect_uri=" + resources.getString(R.string.redirecturi)))
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //fragmentManager.beginTransaction().replace(android.R.id.content, RepositoriesFragment() as? Fragment).commit()

       // replaceFragment(RepositoriesFragment.newInstance(RepositoriesPresenter()), RepositoriesFragment.javaClass.canonicalName)
        replaceFragment(AuthFragment.newInstance(AuthPresenter()), AuthFragment.javaClass.canonicalName)
    }
    override fun onDescriptionPage() {

        replaceFragment(DescriptionFragment.newInstance(DescriptionPresenter()), DescriptionFragment.javaClass.canonicalName)
    }

    private fun replaceFragment(fragment: BaseFragment, tag: String) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_container, fragment, tag)
                .addToBackStack(fragment.tag)
                .commit()
    }
}
