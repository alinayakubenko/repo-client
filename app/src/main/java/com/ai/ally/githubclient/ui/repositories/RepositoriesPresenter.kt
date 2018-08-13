package com.ai.ally.githubclient.ui.repositories

import android.annotation.TargetApi
import android.os.Build
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
    private var mappedRepositories = mutableMapOf<String?,MutableList<Repository>>()

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

        Rest.retrofit.getRepositories(repoUrl).enqueue(object : Callback<MutableList<Repository>> {
            override fun onFailure(call: Call<MutableList<Repository>>, t: Throwable) {
                listener.onFailureResult(t.message)
                Log.i("LOG", "Username loadRepos FAILED: "+ t.message)
            }
            @TargetApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<MutableList<Repository>>?, response: Response<MutableList<Repository>>?) {
                if (response?.body() != null) {
                    response.body()?.let {
                        reposList = it
                        Log.d("LOG", "Username loadRepos Response: " + repoUrl)

                        println(reposList[0].id)
                        println(reposList[0].name)
                        Log.d("LOG", "REPOS SIZE " + reposList.size)


                        for (i in 0 until reposList.size) {
                            if (reposList[i] != null) {
                                if(mappedRepositories.containsKey(reposList[i].language)){
                                    mappedRepositories.get(reposList[i].language)?.add(reposList[i])
                                } else {
                                    mappedRepositories.put(reposList[i].language, mutableListOf(reposList[i]))
                                }
                                Log.d("LOG", "REPOS Language " + reposList[i].language)
                                Log.d("LOG", "REPO NAME: " + reposList[i].name)
                                /*
                                mappedRepositories.forEach { (k, v) ->
                                    println("$k = $v") */
                                }
                            continue
                            }

                        val sortMap = mappedRepositories.toSortedMap(compareBy { it?.length })
                        sortMap.forEach { (k, v) ->
                            Log.d("LOG", "REPOS SORTED " + "$k = $v" + " SIZE: "+v?.size)
                        }
                            listener.onSuccessResult()
                        }
                    }
                }
        })
    }
}