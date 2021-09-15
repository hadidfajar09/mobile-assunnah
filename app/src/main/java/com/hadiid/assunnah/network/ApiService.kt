package com.hadiid.assunnah.network

import com.hadiid.assunnah.ui.course.CategoryResponse
import com.hadiid.assunnah.ui.course.CourseResponse
import com.hadiid.assunnah.ui.home.HomeResponse
import com.hadiid.assunnah.ui.login.LoginResponse
import com.hadiid.assunnah.ui.module.DetailResponse
import com.hadiid.assunnah.ui.module.ModuleResponse
import com.hadiid.assunnah.ui.profile.AvatarResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


//data akses objek endpoint API untuk login
interface ApiService {

    @POST("api/login") //link base
    //menggunakan courotines
    suspend fun login( //isi dgn param validasi
        @Query("email") email: String,
        @Query("password") password: String
    ) : Response<LoginResponse> //callback ke model

    @GET("api/category") //link base
    //menggunakan courotines
    suspend fun category() : Response<CategoryResponse> //callback ke model

    @GET("api/course") //link base
    //menggunakan courotines
    suspend fun course(
        @Query("keyword") keyword: String,
    ) : Response<CourseResponse> //callback ke model

    @POST("api/course-by-category") //link base
    //menggunakan courotines
    suspend fun courseByCategory(
        @Query("category_id") category_id: Int,
    ) : Response<CourseResponse> //callback ke model

    @GET("api/home") //link base
    //menggunakan courotines
    suspend fun home() : Response<HomeResponse> //callback ke model

    @POST("api/course-by-id") //link base
    //menggunakan courotines
    suspend fun courseById(
        @Query("id") id: Int,
    ) : Response<ModuleResponse> //callback ke model

    @POST("api/module-by-id") //link base
    //menggunakan courotines
    suspend fun moduleById(
        @Query("id") id: Int,
    ) : Response<DetailResponse> //callback ke model


    @Multipart
    @POST("api/avatar") //link base
    //menggunakan courotines
    suspend fun avatar(
        @Part avatar: MultipartBody.Part,
        @Query("id") id: Int,
    ) : Response<AvatarResponse> //callback ke model
}