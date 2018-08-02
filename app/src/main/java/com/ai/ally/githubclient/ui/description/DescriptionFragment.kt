package com.ai.ally.githubclient.ui.description

import com.ai.ally.githubclient.base.BaseFragment
import com.ai.ally.githubclient.base.BasePresenter
import com.ai.ally.githubclient.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle

class DescriptionFragment : BaseFragment(), DescriptionView {

    lateinit var presenter: DescriptionPresenter

    companion object {
        fun newInstance(P: DescriptionPresenter): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.presenter = P
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun getPresenter(): BasePresenter<*> = presenter
}