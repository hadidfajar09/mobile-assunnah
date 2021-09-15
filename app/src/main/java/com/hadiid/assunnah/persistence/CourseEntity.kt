package com.hadiid.assunnah.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableDars")

data class CourseEntity(
    @PrimaryKey(autoGenerate = false) //off AI
    val id: Int,
    val thumbnail: String,
    val title: String,
    val mentor: String,
)