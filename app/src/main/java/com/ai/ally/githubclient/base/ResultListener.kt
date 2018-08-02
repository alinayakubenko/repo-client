package com.ai.ally.githubclient.base

interface ResultListener {

    fun onSuccessResult()

    fun onFailureResult(error: String?)

}