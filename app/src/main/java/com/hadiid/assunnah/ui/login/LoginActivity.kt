package com.hadiid.assunnah.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hadiid.assunnah.R
import com.hadiid.assunnah.databinding.ActivityLoginBinding
import com.hadiid.assunnah.network.ApiClient
import com.hadiid.assunnah.preference.PrefManager
import com.hadiid.assunnah.ui.home.HomeActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) } //akan bekerja ketika variabelny dipake
    private lateinit var presenter: LoginPresenter //inisialisasi telat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter = LoginPresenter(
            this,
            ApiClient.getService(),
            PrefManager(this)
        )
    }

    override fun setupListener() {

        binding.editEmail.setText("tester@gmail.com")
        binding.editPassword.setText("tester@gmail.com")

        binding.btnLogin.setOnClickListener {
            presenter.fetchLogin(
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString()

            )
        }
    }

    override fun loginLoading(loading: Boolean) {
        binding.btnLogin.isEnabled = loading.not() //saat loading tombol dsable
        when(loading){
            true -> binding.btnLogin.text = "Loading..."
            false -> binding.btnLogin.text = "Login"
        }

    }

    override fun loginResponse(response: LoginResponse) { //respon saat login
        Toast.makeText(applicationContext,response.msg,Toast.LENGTH_SHORT).show()
        when(response.status){
            true -> {
                presenter.saveLogin(response.data!!,binding.editPassword.text.toString())
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            false -> {
                Toast.makeText(applicationContext,response.msg,Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun loginError(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }
}