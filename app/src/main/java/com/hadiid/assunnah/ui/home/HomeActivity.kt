package com.hadiid.assunnah.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hadiid.assunnah.R
import com.hadiid.assunnah.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityHomeBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.navView.setupWithNavController( //navigasi ke home parent dgn fragmen host
            findNavController(R.id.nav_host_fragment)
        )
    }
}