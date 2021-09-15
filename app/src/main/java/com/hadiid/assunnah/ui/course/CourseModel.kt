package com.hadiid.assunnah.ui.course

data class CourseResponse(
    val data: List<CourseData>
)


data class CourseData(
    val id: Int,
    val category: String,
    val mentor: String,
    val title: String,
    val description: String,
    val group: String,
    val thumbnail: String,

    )

data class CategoryResponse(
    val data: List<CategoryData>
)


data class CategoryData(
    val id: Int,
    val name: String,

    )