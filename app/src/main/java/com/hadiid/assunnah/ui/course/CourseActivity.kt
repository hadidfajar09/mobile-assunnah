package com.hadiid.assunnah.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.hadiid.assunnah.databinding.ActivityCourseBinding
import com.hadiid.assunnah.network.ApiClient
import com.hadiid.assunnah.ui.module.ModuleActivity

class CourseActivity : AppCompatActivity(), CourseView {

    private val binding by lazy { ActivityCourseBinding.inflate(layoutInflater) }
    private lateinit var presenter: CoursePresenter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var courseAdapter: CourseAdapter
    private var keyword = "" //awal ketika buka course


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = CoursePresenter(this, ApiClient.getService())
    }

    override fun setupListener() {
        categoryAdapter = CategoryAdapter(arrayListOf(), object : CategoryAdapter.OnAdapterListener{
            override fun onCLick(category: CategoryData) { //ambil data api
                if(category.id != 0) presenter.fetchCourseByCategory(category.id)
                else presenter.fetchCourse(keyword)

            }

        } )

        binding.listCategory.adapter = categoryAdapter

        courseAdapter = CourseAdapter(arrayListOf(), object : CourseAdapter.OnAdapterListener{
            override fun onCLick(course: CourseData) { //ambil data course
                    startActivity(Intent(this@CourseActivity,ModuleActivity::class.java)
                        .putExtra("id",course.id))
            }

        } )

        binding.listCourse.adapter = courseAdapter

        //ketika text berubah di search
        binding.editSearch.doAfterTextChanged {
            keyword = it.toString() //ambil keyword dri sini
        }

        //untuk ime search icon
        binding.editSearch.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){ //ketika search
                presenter.fetchCourse(keyword)
            }
            false
        }

        //data baru
        binding.swipe.setOnRefreshListener {
            presenter.fetchCourse(keyword)
        }
    }

    override fun courseLoading(loading: Boolean) {
        binding.swipe.isRefreshing = loading //swipe saat refresh
    }

    override fun courseResponse(response: CourseResponse) {
        courseAdapter.addList(response.data) //pick data resnpon course
    }

    override fun categoryResponse(response: CategoryResponse) {
        categoryAdapter.addList(response.data) //pick data resnpon category
    }

    override fun courseError(message: String) {
        binding.swipe.isRefreshing = false //swipe saat error

    }
}