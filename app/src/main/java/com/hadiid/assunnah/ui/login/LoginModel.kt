package com.hadiid.assunnah.ui.login

data class LoginResponse( //respon pertama ketika login status,msg
    val status : Boolean,
    val msg: String,
    val data: LoginData?
    )

data class LoginData( //respon berupa data peserta
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val gender: String,
    val phone: String,
    val avatar: String
)