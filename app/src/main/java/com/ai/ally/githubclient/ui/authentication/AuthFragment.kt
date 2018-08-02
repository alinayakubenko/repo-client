package com.ai.ally.githubclient.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ai.ally.githubclient.MainView
import com.ai.ally.githubclient.R
import com.ai.ally.githubclient.base.BaseFragment
import com.ai.ally.githubclient.base.BasePresenter

class AuthFragment : BaseFragment(), AuthView {

    lateinit var presenter: AuthPresenter
    private var loginButton: Button? = null

    companion object {
        fun newInstance(P: AuthPresenter): AuthFragment {
            val fragment = AuthFragment()
            fragment.presenter = P
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //To redirect to the GitHub login page automatically
        (activity as MainView).onLogin()
        // Redirecting to login page by clicking Login button in case of "back button" flow.
        loginButton = view.findViewById(R.id.login_button)
        loginButton?.setOnClickListener {
            (activity as MainView).onLogin()
        }

    }
    override fun getPresenter(): BasePresenter<*> = presenter
}