package com.hadiid.assunnah.ui.home

import com.hadiid.assunnah.ui.course.CourseData

data class HomeResponse(
    val status: Boolean,
    val data: HomeData
)

data class HomeData(
    val latest: List<CourseData>,
    val popular: List<CourseData>
)