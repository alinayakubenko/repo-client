package com.ai.ally.githubclient.models

import com.google.gson.annotations.SerializedName

class NewTokenRequest (@SerializedName("code") var code: String?,
                       @SerializedName("scopes") var scopes: String? = null,
                       @SerializedName("client_id") var clientId: String? = null,
                       @SerializedName("client_secret") var clientSecret: String? = null,
                       @SerializedName("redirect_uri") var redirectUri: String? = null)



class NewTokenResponse (@SerializedName("access_token") var accessToken: String?,
                        @SerializedName("token_type") var tokenType: String? = null,
                        @SerializedName("scopes") var scope: String? = null)

// Not sure if I still need this. TProbably will better handle revoke access instead.
class RefreshTokensRequest (@SerializedName("refresh_token") var refreshToken: String?,
                            @SerializedName("scopes") var repos: String? = null,
                            @SerializedName("client_id") var clientId: String? = null,
                            @SerializedName("client_secret") var clientSecret: String? = null,
                            @SerializedName("redirect_uri") var redirectUri: String? = null)

class RefreshTokensResponse (@SerializedName("access_token") var accessToken: String?,
                             @SerializedName("token_type") var tokenType: String? = null,
                             @SerializedName("scopes") var scope: String? = null)

