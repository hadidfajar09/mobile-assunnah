package com.hadiid.assunnah.ui.profile

import com.google.gson.Gson
import com.hadiid.assunnah.network.ApiService
import com.hadiid.assunnah.persistence.CourseDao
import com.hadiid.assunnah.preference.PrefManager
import com.hadiid.assunnah.ui.login.LoginData
import com.hadiid.assunnah.ui.login.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfilePresenter(
    private val view: ProfileView,
    private val pref: PrefManager,
    private val db: CourseDao,
    private val api: ApiService,

) {

    init {
        view.setupListener()
        view.user(user())
    }



    private fun user(): LoginData{ //ambil data di data model
        val json = pref.getString("user_login")
        return Gson().fromJson(json, LoginData::class.java) //diubah ke json model
    }


    fun logout(){
        pref.clear() //pref dihapus
        view.logout()   //akhiran view profile
    }

    fun count(){
        GlobalScope.launch {
            val count = db.count()
            withContext(Dispatchers.Main){
                view.jumlah(count)
            }
        }
    }

    fun uploadAvatar(avatar: File, id: Int){

        val requestBody: RequestBody = avatar.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("avatar",avatar.name,requestBody)

        view.uploadLoading(true)

        GlobalScope.launch {
            val response = api.avatar(multipartBody,id)
            if(response.isSuccessful){
                withContext(Dispatchers.Main){
                    view.uploadResponse(response.body()!!)
                    view.uploadLoading(false)
                }

            }else{
                view.uploadError("Terjadi Kesalahan")
            }
        }
    }

    fun reLogin( email: String ){
        GlobalScope.launch {
            val response = api.login( email,pref.getString("user_password")!! )
            if(response.isSuccessful){
                withContext(Dispatchers.Main){
                    view.loginResponse(response.body()!!)
                }

            }
        }
    }

    fun updateSession(data: LoginData){
        pref.put("user_login",Gson().toJson(data))
    }

}

interface ProfileView{
    fun setupListener()
    fun user(user: LoginData)
    fun logout()
    fun jumlah(count: Int)
    fun uploadLoading(loading: Boolean)
    fun uploadResponse(avatar: AvatarResponse)
    fun uploadError(message: String)
    fun loginResponse(login: LoginResponse)
}