package com.ai.ally.githubclient.ui.repositories

import android.util.Log
import com.ai.ally.githubclient.base.BasePresenter
import com.ai.ally.githubclient.base.ResultListener
import com.ai.ally.githubclient.models.Repository
import com.ai.ally.githubclient.utils.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RepositoriesPresenter : BasePresenter<RepositoriesView>() {

    private lateinit var reposList: MutableList<Repository>
    lateinit var repoUrl: String

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

        //  Unable to retrieve data to the model. Getting "Unresolved reference: response" error on request below, need to come up with and idea why and how to fix it.
        Rest.retrofit.getRepositories(repoUrl).enqueue(object : Callback<MutableList<Repository>> {
            override fun onFailure(call: Call<MutableList<Repository>>, t: Throwable) {
                listener.onFailureResult(t.message)
                Log.i("LOG", "Username loadRepos FAILED: "+ t.message)
            }
            override fun onResponse(call: Call<MutableList<Repository>>?, response: Response<MutableList<Repository>>?) {
                if (response?.body() != null) {
                    response.body()?.let {
                       reposList = it
                        Log.i("LOG", "Username loadRepos Response: "+ repoUrl)
                        Log.i("LOG", "loadRepos Response Headers: "+ reposList[0].id)
                        Log.i("LOG", "loadRepos Response Body: "+ reposList[0].name)

                        println(reposList[0].id)
                        println(reposList[0].name)
                       //  println(reposList[0].owner?.login)
                       // println(reposList[0].owner?.type)
                        Log.i("LOG", "REPO NAME 0: "+ it[0].name)
                        listener.onSuccessResult()
                    }
                }
            }
        })
    }
}