package com.ai.ally.githubclient.utils.rest

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import com.ai.ally.githubclient.BuildConfig
import com.ai.ally.githubclient.models.Repositories

interface RestService {

    companion object {

        private lateinit var okClient: OkHttpClient

        fun create(): RestService {

            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                })

                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory

                val logger = HttpLoggingInterceptor()
                if (BuildConfig.DEBUG) {
                    logger.level = HttpLoggingInterceptor.Level.BODY
                } else {
                    logger.level = HttpLoggingInterceptor.Level.NONE
                }
                okClient = OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(logger)
                        .sslSocketFactory(sslSocketFactory)
                        .hostnameVerifier { hostname, session -> true }
                        .addInterceptor { chain ->
                            val request = chain.request().newBuilder()
                                    .build()
                            chain.proceed(request)
                        }
                        .addInterceptor(Interceptor { chain ->
                            val response = chain.proceed(chain.request())

                            response
                        })
                        .build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(
                            GsonBuilder()
                                    .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                    .create()))
                    .client(okClient)
                    .build()

            return retrofit.create(RestService::class.java)
        }
    }

    @GET("/orgs/octokit/repos")
    fun getExampleRest(): Call<MutableList<Repositories>>

}