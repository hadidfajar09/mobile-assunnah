package com.hadiid.assunnah.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// base API

private const val baseUrl = "https://kelas.warungbelajar.com/"

object ApiClient{ //action apiclient
    fun getService(): ApiService{ //method untuk memanggil api service

        val logging = HttpLoggingInterceptor() //cek komunikasi api
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder() //sebagai client di retrofit
            .addInterceptor(logging)
            .build()

        val gson = GsonBuilder().serializeNulls() //ketika terjadi nullpointerexcept saat request
            .create()

        return Retrofit.Builder()
            .baseUrl(baseUrl) //url base pada postman web
            .addConverterFactory(GsonConverterFactory.create(gson)) //untuk convert json memakai gson
            .client(client)
            .build()
            .create(ApiService::class.java) //action ke apiservice
    }
}