package com.mredrock.cyxbs.mine

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object TestRetrofit {
    val testRetrofit = provideRetrofit()


    private fun provideRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor{
                    val build = it.request()
                        .newBuilder()
                        .addHeader("Authorization",
                            "Bearer eyJEYXRhIjp7ImdlbmRlciI6IueUtyIsInN0dV9udW0iOiIyMDIwMjExNTM0In0sIkRvbWFpbiI6Im1hZ2lwb2tlIiwiUmVkaWQiOiI3Y2QxYmJlZWY5NjRiN2FjMmQxNmFmNzY2NjZkMGRiOWI2MTIyZTkzIiwiZXhwIjoiNzM5ODg0NTYxNSIsImlhdCI6IjE2MjkwMzQzNjIiLCJzdWIiOiJ3ZWIifQ==.Lv1JFrexd+EMq952mJH5v96hXxInP7D4gpuZAR9j4CUINMBCCOGkhFQbp2QSgiOymtYafziXC6pUmKG+H+K+Zh4OU77xM7eN0TMJ5/8x7wl6P1cuE3NDft7Cd0HcnkX73FYtPub0BttyO2rF5rJt9Y9xlrlYZ+QJBWRyV2KY4X539Oc8Le/j/1FLopraP1FiVatyVPafN4bVy7cf4TaIC75YJmJpLSQIIFHuQ+6knk1j8qoQcVBLXUoghKTfKYqZLJddff/oaCnY3Vsvj1QId7XILysnwBYPPL6UlmRUUGjwGGNGaXHlYoirg6RAlpDh08jYIF80wwWGP9WP1GE60A==")
                        .build()
                return@addInterceptor it.proceed(build)
                }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://be-dev.redrock.cqupt.edu.cn")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}