package com.hadiid.assunnah.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hadiid.assunnah.R
import com.hadiid.assunnah.databinding.FragmentHomeBinding
import com.hadiid.assunnah.network.ApiClient
import com.hadiid.assunnah.preference.PrefManager
import com.hadiid.assunnah.ui.course.CourseActivity
import com.hadiid.assunnah.ui.course.CourseAdapter
import com.hadiid.assunnah.ui.course.CourseData
import com.hadiid.assunnah.ui.login.LoginData
import com.hadiid.assunnah.ui.module.ModuleActivity


class HomeFragment : Fragment(), HomeView {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var latestAdapter: CourseAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        presenter = HomePresenter(this,ApiClient.getService(), PrefManager(requireContext()))
        return binding.root
    }


    override fun setupListener() {
        binding.editSearch.setOnClickListener {
            startActivity(Intent(requireActivity(),CourseActivity::class.java))
        }

        binding.textPelajaran.setOnClickListener {
            startActivity(Intent(requireActivity(),CourseActivity::class.java))
        }

        popularAdapter = PopularAdapter(arrayListOf(),object: PopularAdapter.OnAdapterListener{
            override fun onCLick(course: CourseData) {
                startActivity(Intent(requireActivity(), ModuleActivity::class.java)
                    .putExtra("id",course.id))
            }

        })

        binding.listPopular.adapter = popularAdapter


        latestAdapter = CourseAdapter(arrayListOf(),object: CourseAdapter.OnAdapterListener{
            override fun onCLick(course: CourseData) {
                startActivity(Intent(requireActivity(),ModuleActivity::class.java)
                    .putExtra("id",course.id))
            }

        })

        binding.listNew.adapter = latestAdapter

        binding.swipe.setOnRefreshListener {
            presenter.fetchHome()
        }
    }

    override fun homeLoading(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    override fun homeResponse(response: HomeResponse) {
        popularAdapter.addList(response.data.popular)
        latestAdapter.addList(response.data.latest)
    }

    override fun homeError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        binding.swipe.isRefreshing = false
    }

    override fun user(user: LoginData) {
        binding.textTitle.text = "Ahlan Wa Sahlan, ${user.name}"
    }


}