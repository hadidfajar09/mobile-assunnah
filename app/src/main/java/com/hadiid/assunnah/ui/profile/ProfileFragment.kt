package com.hadiid.assunnah.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.hadiid.assunnah.R
import com.hadiid.assunnah.databinding.FragmentProfileBinding
import com.hadiid.assunnah.network.ApiClient
import com.hadiid.assunnah.persistence.DatabaseClient
import com.hadiid.assunnah.preference.PrefManager
import com.hadiid.assunnah.ui.login.LoginActivity
import com.hadiid.assunnah.ui.login.LoginData
import com.hadiid.assunnah.ui.login.LoginResponse
import com.hadiid.assunnah.util.loadAvatar
import com.hadiid.assunnah.util.loadUri
import java.io.File


class ProfileFragment : Fragment(), ProfileView {

    private lateinit var binding: FragmentProfileBinding//cara menggunakan binding d fragment
    private lateinit var presenter: ProfilePresenter//cara menggunakan binding d fragment
    private lateinit var user: LoginData//cara menggunakan binding d fragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        presenter = ProfilePresenter(
            this,
            PrefManager(requireContext()),
            DatabaseClient.getService(requireContext()).courseDao(),
            ApiClient.getService()
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.count()
    }

    override fun setupListener() {
        binding.btnLogout.setOnClickListener {
            presenter.logout()
        }

        binding.imageAvatar.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .galleryOnly()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start()
        }
    }

    //set data ke view
    override fun user(user: LoginData) {
        this.user = user
        binding.textName.text = user.name
        binding.textEmail.text = user.email
        loadAvatar(binding.imageAvatar, user.avatar)

    }

    override fun logout() {
        Toast.makeText(requireContext(), "Logout", Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish() //back ke activity home trus finish
    }

    override fun jumlah(count: Int) {
        binding.textCourse.text = count.toString()
    }

    override fun uploadLoading(loading: Boolean) {
        binding.progress.visibility = if(loading) View.VISIBLE else View.GONE
    }

    override fun uploadResponse(avatar: AvatarResponse) {
        Toast.makeText(requireContext(), avatar.msg, Toast.LENGTH_SHORT).show()
        presenter.reLogin(user.email)
    }

    override fun uploadError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        binding.progress.visibility = View.GONE


    }

    override fun loginResponse(login: LoginResponse) {
        presenter.updateSession(login.data!!)
        loadAvatar(binding.imageAvatar, login.data.avatar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            val fileUri:Uri = data?.data!!
            loadUri(binding.imageAvatar, fileUri)

            val fileImage: File = ImagePicker.getFile(data)!!
            presenter.uploadAvatar(fileImage,user.id)
        }
    }


}