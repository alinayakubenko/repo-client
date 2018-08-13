package com.ai.ally.githubclient.models

import com.google.gson.annotations.SerializedName

class Owner(@SerializedName("login") var login: String? = null,
            @SerializedName("repos_url") var reposUrl: String? = null,
            @SerializedName("type") var type: String? = null)