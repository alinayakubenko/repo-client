package com.ai.ally.githubclient.models

import com.google.gson.annotations.SerializedName

class Owner(@SerializedName("login") var login: String? = null,
            @SerializedName("id") var id: Int,
            @SerializedName("html_url") var htmlUrl: String? = null,
            @SerializedName("type") var type: String? = null)