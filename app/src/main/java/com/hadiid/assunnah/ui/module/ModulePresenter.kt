package com.hadiid.assunnah.ui.module

import com.hadiid.assunnah.network.ApiService
import com.hadiid.assunnah.persistence.CourseDao
import com.hadiid.assunnah.persistence.CourseEntity
import com.hadiid.assunnah.ui.course.CourseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModulePresenter( //constr set view pertama kali
    private val view: ModuleView,
    private val api: ApiService,
    private val db: CourseDao,
) {

    init { //inisialisasi sprt oncreate
        view.setupListener()

    }




    fun fetchModule(id: Int){ //request ke api
        view.moduleLoading(true)
        GlobalScope.launch {
            val response =  api.courseById(id)
            if(response.isSuccessful){ //menampilakn perubahan pda tampilan sprt pesan/ubah data
                withContext(Dispatchers.Main){
                    view.moduleResponse(response.body()!!) //harus ada data
                    view.moduleLoading(false)
                }
            }else{
                view.moduleError("Terjadi Kesalahan")
            }
        }
    }


    fun find(id: Int){
        GlobalScope.launch {
            val bookmark =  db.find(id)
            withContext(Dispatchers.Main){
                view.isBookmark( bookmark )
            }
        }
    }


    fun addBookmark(data : CourseData){
        val course = CourseEntity(
            id = data.id,
            thumbnail = data.thumbnail,
            title = data.title,
            mentor = data.mentor
        )
        GlobalScope.launch {
            db.add( course )
            withContext(Dispatchers.Main){
                view.isBookmark(1)
            }
        }
    }



}

interface ModuleView{ //penghubung presente n aktvity
    fun setupListener() //onclik
    fun moduleLoading(loading: Boolean) //login status loading
    fun moduleResponse(response: ModuleResponse) //ketika berhasil
    fun moduleError(message: String) //ketika respon error
    fun isBookmark(bookmark: Int)  //untuk bookmark
}