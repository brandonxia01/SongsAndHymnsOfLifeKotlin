package com.darrengu.songsandhymnsoflife.retrofit

import retrofit2.http.GET

interface RetrofitApi {
    @GET("")
    fun getRemoteDatabaseVersion()
}