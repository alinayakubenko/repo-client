package com.ai.ally.githubclient.models

import com.google.gson.annotations.SerializedName

class Repositories(@SerializedName("id") var id: Int,
                   @SerializedName("node_id") var nodeId: String? = null,
                   @SerializedName("name") var name: String? = null,
                   @SerializedName("owner") var owner: Owner? = null)