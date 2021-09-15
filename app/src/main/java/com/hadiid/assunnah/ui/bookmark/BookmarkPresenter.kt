package com.hadiid.assunnah.ui.bookmark

import com.hadiid.assunnah.persistence.CourseDao
import com.hadiid.assunnah.persistence.CourseEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookmarkPresenter(

    private val view: BookmarkView,
    private val db: CourseDao

) {

    val listBookmark = db.findAll()

    fun remove(course: CourseEntity){
        GlobalScope.launch {
            db.remove(course)
        }
    }

}
    interface BookmarkView{

        fun setupListener()
}