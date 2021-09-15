package com.hadiid.assunnah.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hadiid.assunnah.ui.home.HomeActivity
import com.hadiid.assunnah.R
import com.hadiid.assunnah.preference.PrefManager
import com.hadiid.assunnah.ui.BaseActivity
import com.hadiid.assunnah.ui.login.LoginActivity

class SplashActivity : BaseActivity(),SplashView { //extend splash interface n implemnt methodny

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SplashPresenter(this, PrefManager(this)) //menjalnkn presenter d dlm oncreate
    }

    override fun nextPage(isLogin: Int) {
        Handler(Looper.myLooper()!!).postDelayed({
            if(isLogin == 1) startActivity(Intent(this, HomeActivity::class.java )) //jika 1 = sudah login maka ke home
            else startActivity(Intent(this, LoginActivity::class.java )) // jika belum ke login

            finish()
        },3000)
    }
}