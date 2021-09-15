package com.hadiid.assunnah.preference

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    private val prefName = "AssunnahUnifa28.pref" //nama preference
    private var sharedPref: SharedPreferences
    private var editor: SharedPreferences.Editor //melakukan edit

    init {
        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    //simpan data set
    fun put(key: String, value: String) { //utk menyimpan data string
        editor.putString(key, value)
            .apply()
    }

    fun put(key: String, value: Int) { //utk menyimpan data int
        editor.putInt(key, value)
            .apply()
    }

    //ambil data get
    fun getString(key: String): String? { //utk menyimpan data string
        return sharedPref.getString(key,"")
    }

    fun getInt(key: String): Int { //utk menyimpan data int
        return sharedPref.getInt(key,0)
    }

    fun clear(){
        editor.clear()
            .apply()
    }
}