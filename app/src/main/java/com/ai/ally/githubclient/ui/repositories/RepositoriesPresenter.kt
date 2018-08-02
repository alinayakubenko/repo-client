package com.ai.ally.githubclient.ui.repositories

import com.ai.ally.githubclient.base.BasePresenter
import com.ai.ally.githubclient.base.ResultListener
import com.ai.ally.githubclient.models.Repositories
import com.ai.ally.githubclient.utils.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoriesPresenter : BasePresenter<RepositoriesView>() {

    private lateinit var reposList: MutableList<Repositories>

    override fun onViewCreated() {

        // if ("some data available in the DB") { }
        // else  { loadRepos }

        loadRepos(object : ResultListener {
            override fun onSuccessResult() {
                view?.setDataToRecycler(reposList)
            }

            override fun onFailureResult(error: String?) {
                error?.let {
                    view?.showMessage(it)
                }
            }
        })
        // TODO view is ready to show the data
        // TODO check the models for the data.
    }

    private fun loadRepos(listener: ResultListener) {

        Rest.retrofit.getExampleRest().enqueue(object : Callback<MutableList<Repositories>> {
            override fun onFailure(call: Call<MutableList<Repositories>>, t: Throwable) {
                listener.onFailureResult(t.message)
            }

            override fun onResponse(call: Call<MutableList<Repositories>>, response: Response<MutableList<Repositories>>) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        reposList = it

                        // for example
                        println(reposList[0].id)
                        println(reposList[0].name)
                        println(reposList[0].owner?.id)
                        println(reposList[0].owner?.login)
                        println(reposList[0].owner?.type)
                        // for example
                        listener.onSuccessResult()
                    }
                }
            }
        })
    }
}