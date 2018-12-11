package com.puzzlebench.cleanMarvelKotlin.data.service

import com.puzzlebench.cleanMarvelKotlin.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

const val HASH = "hash"
const val APIKEY = "apikey"
const val TS = "ts"
const val TS_VALUE = "1"

class MarvelRequestGenerator {

    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val defaultRequest = chain.request()
        val hashSignature = "$TS_VALUE${BuildConfig.PRIVATE_API_KEY_VALUE}${BuildConfig.PUBLIC_API_KEY_VALUE}".md5()
        val defaultHttpUrl = defaultRequest.url()
        val httpUrl = defaultHttpUrl.newBuilder()
                .addQueryParameter(TS, TS_VALUE)
                .addQueryParameter(APIKEY, BuildConfig.PUBLIC_API_KEY_VALUE)
                .addQueryParameter(HASH, hashSignature)
                .build()

        val requestBuilder = defaultRequest.newBuilder().url(httpUrl)

        chain.proceed(requestBuilder.build())
    }

    private val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.MARVEL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}