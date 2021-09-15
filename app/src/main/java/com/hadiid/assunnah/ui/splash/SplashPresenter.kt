package com.hadiid.assunnah.ui.splash

import android.util.Log
import com.hadiid.assunnah.preference.PrefManager

class SplashPresenter(
    private val view: SplashView, //construktor mewakili interface
    private val pref: PrefManager, //mewakili preference manager

) {

    //sesuatu yg pertama dikerjakan
    init {
        Log.e("SplashPresenter", "is login: ${pref.getInt("is_login")}")
        view.nextPage(pref.getInt("is_login")) //didpt dari session
    }

}

// interface utk tampilan utk digunakan di splash aktivity
interface SplashView {
    fun nextPage(isLogin: Int) //untuk melanjutkan page setelah splash
}