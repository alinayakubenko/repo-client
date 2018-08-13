package com.ai.ally.githubclient.ui.repositories

import com.ai.ally.githubclient.base.BaseFragment
import com.ai.ally.githubclient.base.BasePresenter
import com.ai.ally.githubclient.models.Repository
import com.ai.ally.githubclient.MainView
import com.ai.ally.githubclient.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.support.v7.widget.RecyclerView

class RepositoriesFragment : BaseFragment(), RepositoriesView {
    lateinit var presenter: RepositoriesPresenter
    private var onDescriptionPageButton: Button? = null

    companion object {
        fun newInstance(P: RepositoriesPresenter, repoUrl: String): RepositoriesFragment {
            val fragment = RepositoriesFragment()
             P.repoUrl = repoUrl
             fragment.presenter = P
            return fragment
            Log.i("LOG", "URL fragment loadRepos: "+ repoUrl)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onDescriptionPageButton = view.findViewById(R.id.to_description_page)
        onDescriptionPageButton?.setOnClickListener {
            (activity as MainView).onDescriptionPage()
        }
    }

    override fun getPresenter(): BasePresenter<*> = presenter


    override fun showMessage(message: String) {
        Toast.makeText(activity?.let { it }, message, Toast.LENGTH_SHORT).show()
    }
    override fun setDataToRecycler(reposList: MutableList<Repository>) {
        // TODO create recycler + adapter and set list
    }

}