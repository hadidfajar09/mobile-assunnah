package com.hadiid.assunnah.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CourseEntity::class],
    exportSchema = false,
    version = 1

)

//krn abstract bisa dtmbhkan apa saja
abstract class DatabaseService : RoomDatabase(){
    abstract fun courseDao(): CourseDao
}