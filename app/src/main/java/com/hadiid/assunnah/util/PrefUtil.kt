package com.hadiid.assunnah.util

import com.google.gson.Gson
import com.hadiid.assunnah.preference.PrefManager
import com.hadiid.assunnah.ui.login.LoginData

fun userLogin(prefManager: PrefManager): LoginData { //ambil data di data model
    val json = prefManager.getString("user_login")
    return Gson().fromJson(json, LoginData::class.java) //diubah ke json model
}