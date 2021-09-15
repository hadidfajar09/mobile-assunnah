package com.hadiid.assunnah.ui.login

import com.google.gson.Gson
import com.hadiid.assunnah.network.ApiService
import com.hadiid.assunnah.preference.PrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter( //constr set view pertama kali
    private val view: LoginView,
    private val api: ApiService,
    private val pref: PrefManager,
) {

    init { //inisialisasi sprt oncreate
        view.setupListener()
    }

    fun fetchLogin(email: String, password: String){ //request ke api
        view.loginLoading(true)
        GlobalScope.launch {
            val response =  api.login(email,password)
            if(response.isSuccessful){ //menampilakn perubahan pda tampilan sprt pesan/ubah data
                withContext(Dispatchers.Main){
                    view.loginResponse(response.body()!!) //harus ada data
                    view.loginLoading(false)
                }
            }else{
                view.loginError("Terjadi Kesalahan")
            }
        }
    }

    fun saveLogin(data: LoginData, password: String){
            pref.put("is_login",1) //simpan data int
            pref.put("user_login",Gson().toJson(data)) //simpan data string
            pref.put("user_password",password) //simpan data string pass
    }

}

interface LoginView{ //penghubung presente n aktvity
    fun setupListener() //onclik
    fun loginLoading(loading: Boolean) //login status loading
    fun loginResponse(response: LoginResponse) //ketika berhasil
    fun loginError(message: String) //ketika respon error
}