package com.ai.ally.githubclient.utils.rest

object Rest {

    val retrofit by lazy {
        RestService.create()
    }

}