package com.ai.ally.githubclient.ui.repositories

import com.ai.ally.githubclient.base.BaseView
import com.ai.ally.githubclient.models.Repository


interface RepositoriesView : BaseView {

    fun showMessage(message: String)

    fun setDataToRecycler(reposList: MutableList<Repository>)

}