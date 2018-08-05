package com.ai.ally.githubclient.utils.rest

object Rest {

    //Create singleton
    val retrofit by lazy {
        RestService.create()
    }

}