package com.ai.ally.githubclient.ui.authentication

import android.annotation.TargetApi
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ai.ally.githubclient.R
import com.ai.ally.githubclient.base.BaseFragment
import com.ai.ally.githubclient.base.BasePresenter

class AuthFragment : BaseFragment(), AuthView {

    lateinit var presenter: AuthPresenter
    private var loginButton: Button? = null
    lateinit var sp: SharedPreferences

    companion object {
        fun newInstance(P: AuthPresenter): AuthFragment {
            val fragment = AuthFragment()
            fragment.presenter = P
            return fragment
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sp = PreferenceManager.getDefaultSharedPreferences(context)
        /*
        if((sp.getString("code","") == "")) { // TODO remove. For testing purposes only. Replace with token checking and handling revoked access
            //To redirect to the GitHub login page automatically
            (activity as MainView).onLogin()
            // Redirecting to login page by clicking Login button in case of "back button" flow.
            loginButton = view.findViewById(R.id.login_button)
            loginButton?.setOnClickListener {
                (activity as MainView).onLogin() // TODO check if there is a token and show scopes page
            }
        } else {
            (activity as MainView).onReposPage()
        } */

    }
    override fun getPresenter(): BasePresenter<*> = presenter
}