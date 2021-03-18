package com.social.socialcommunication.service

import com.google.gson.GsonBuilder
import com.social.socialcommunication.common.Constant
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

public class ApiConnect {
    companion object {
        var retrofit: Retrofit? = null
        private var v1Instance: IRestService? = null
        private final val TIME_OUT = 10

        fun builderV1(): IRestService? {
            if (v1Instance == null) {
                val builder = Retrofit.Builder()
                val httpClient: OkHttpClient = ApiConnect.getUnsafeOkHttpClient()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val retrofit: Retrofit = builder
                    .baseUrl(Constant.BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                v1Instance = retrofit.create(IRestService::class.java)
            }
            return v1Instance
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts =
                    arrayOf<TrustManager>(object : X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                    )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts[0] as X509TrustManager
                )
                builder.hostnameVerifier { hostname: String?, session: SSLSession? -> true }
                builder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .connectionPool(ConnectionPool(0, TIME_OUT.toLong(), TimeUnit.SECONDS))
                builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }


}