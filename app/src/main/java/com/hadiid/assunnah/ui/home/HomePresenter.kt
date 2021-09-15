package com.hadiid.assunnah.ui.home

import com.google.gson.Gson
import com.hadiid.assunnah.network.ApiService
import com.hadiid.assunnah.preference.PrefManager
import com.hadiid.assunnah.ui.login.LoginData
import com.hadiid.assunnah.util.userLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePresenter( //constr set view pertama kali
    private val view: HomeView,
    private val api: ApiService,
    private val pref: PrefManager,
) {

    init { //inisialisasi sprt oncreate
        view.setupListener()
        fetchHome() //lgsg request
        view.user(
            userLogin(pref)
        )
    }

    fun fetchHome(){ //request ke api
        view.homeLoading(true)
        GlobalScope.launch {
            val response =  api.home()
            if(response.isSuccessful){ //menampilakn perubahan pda tampilan sprt pesan/ubah data
                withContext(Dispatchers.Main){
                    view.homeResponse(response.body()!!) //harus ada data
                    view.homeLoading(false)
                }
            }else{
                view.homeError("Terjadi Kesalahan")
            }
        }
    }

}

interface HomeView{ //penghubung presente n aktvity
    fun setupListener() //onclik
    fun homeLoading(loading: Boolean) //login status loading
    fun homeResponse(response: HomeResponse) //ketika berhasil
    fun homeError(message: String) //ketika respon error
    fun user(user: LoginData)
}