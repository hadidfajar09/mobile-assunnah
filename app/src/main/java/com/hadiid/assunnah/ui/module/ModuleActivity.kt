package com.hadiid.assunnah.ui.module

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.hadiid.assunnah.R
import com.hadiid.assunnah.databinding.ActivityModuleBinding
import com.hadiid.assunnah.network.ApiClient
import com.hadiid.assunnah.persistence.DatabaseClient
import com.hadiid.assunnah.ui.BaseActivity
import com.hadiid.assunnah.ui.course.CourseData
import com.hadiid.assunnah.util.loadImage

class ModuleActivity : BaseActivity(), ModuleView {

    private val binding by lazy{ ActivityModuleBinding.inflate(layoutInflater)}
    private lateinit var presenter: ModulePresenter
    private lateinit var moduleAdapter: ModuleAdapter
    private val id by lazy{ intent.getIntExtra("id",0) } //krim id ketika membuka modul

    private lateinit var course: CourseData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = ModulePresenter(
            this,
            ApiClient.getService(),
            DatabaseClient.getService(this).courseDao()
            )
    }

    //stelah oncreate
    override fun onStart() {
        super.onStart()
        if(id != 0){
            presenter.fetchModule(id)
            presenter.find(id)
        }
    }

    override fun setupListener() {
        moduleAdapter = ModuleAdapter(arrayListOf(),object : ModuleAdapter.OnAdapterListener{
            override fun onCLick(detail: DetailData) {
                startActivity(Intent(this@ModuleActivity,DetailActivity::class.java)
                    .putExtra("id",detail.id))
            }
        })
        binding.listModule.adapter = moduleAdapter

        binding.swipe.setOnRefreshListener {
            if(id != 0){
                presenter.fetchModule(id)
                presenter.find(id)
            }
        }

        //simpan
        binding.imageBookmark.setOnClickListener {
            presenter.addBookmark( course )
        }
    }

    override fun moduleLoading(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    override fun moduleResponse(response: ModuleResponse) {
        course = response.data.course
        binding.textTitle.text = course.title
        binding.textMentor.text = course.mentor
        loadImage(binding.imageThumbnail, course.thumbnail)

        val module = response.data.detail

        moduleAdapter.addList(module)

        binding.textModules.text = "${module.size} Dars"

        var views = 0
        module.forEach {
            views += it.view.toInt()

        }
        binding.textViews.text = "${views}x diliat"

        binding.btnLink.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(course.group)
            startActivity(openUrl)
        }

    }

    override fun moduleError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun isBookmark(bookmark: Int) {
        when(bookmark){
            1 -> binding.imageBookmark.setImageResource(R.drawable.ic_bookmark_added)
            else -> binding.imageBookmark.setImageResource(R.drawable.ic_bookmark_add)
        }
    }
}