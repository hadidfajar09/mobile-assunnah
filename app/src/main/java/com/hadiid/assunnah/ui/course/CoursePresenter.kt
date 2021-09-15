package com.hadiid.assunnah.ui.course

import com.hadiid.assunnah.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoursePresenter( //constr set view pertama kali
    private val view: CourseView,
    private val api: ApiService,
) {

    init { //inisialisasi sprt oncreate
        view.setupListener()
        fetchCategory() //lgsg request
        fetchCourse("")
    }

    private fun fetchCategory(){ //request ke api
        view.courseLoading(true)
        GlobalScope.launch {
            val response =  api.category()
            if(response.isSuccessful){ //menampilakn perubahan pda tampilan sprt pesan/ubah data
                withContext(Dispatchers.Main){
                    view.categoryResponse(response.body()!!) //harus ada data
                    view.courseLoading(false)
                }
            }else{
                view.courseError("Terjadi Kesalahan")
            }
        }
    }

    fun fetchCourse(keyword: String){ //request ke api
        view.courseLoading(true)
        GlobalScope.launch {
            val response =  api.course(keyword)
            if(response.isSuccessful){ //menampilakn perubahan pda tampilan sprt pesan/ubah data
                withContext(Dispatchers.Main){
                    view.courseResponse(response.body()!!) //harus ada data
                    view.courseLoading(false)
                }
            }else{
                view.courseError("Terjadi Kesalahan")
            }
        }
    }


    fun fetchCourseByCategory(category_id: Int){ //request ke api
        view.courseLoading(true)
        GlobalScope.launch {
            val response =  api.courseByCategory(category_id)
            if(response.isSuccessful){ //menampilakn perubahan pda tampilan sprt pesan/ubah data
                withContext(Dispatchers.Main){
                    view.courseResponse(response.body()!!) //harus ada data
                    view.courseLoading(false)
                }
            }else{
                view.courseError("Terjadi Kesalahan")
            }
        }
    }



}

interface CourseView{ //penghubung presente n aktvity
    fun setupListener() //onclik
    fun courseLoading(loading: Boolean) //login status loading
    fun courseResponse(response: CourseResponse) //ketika berhasil
    fun categoryResponse(response: CategoryResponse) //ketika berhasil
    fun courseError(message: String) //ketika respon error
}