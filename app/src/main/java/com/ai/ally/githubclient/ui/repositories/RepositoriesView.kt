package com.ai.ally.githubclient.ui.repositories

import com.ai.ally.githubclient.base.BaseView
import com.ai.ally.githubclient.models.Repositories


interface RepositoriesView : BaseView {

    fun showMessage(message: String)

    fun setDataToRecycler(reposList: MutableList<Repositories>)

}