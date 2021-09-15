package com.hadiid.assunnah.persistence

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(courseEntity: CourseEntity)

    @Update
    suspend fun edit(courseEntity: CourseEntity)

    @Delete
    suspend fun remove(courseEntity: CourseEntity)

    //fun sdh support livedata
    @Query("SELECT * FROM tableDars")
    fun findAll(): LiveData<List<CourseEntity>>

    @Query("SELECT COUNT(id) FROM tableDars WHERE id=:id ")
    fun find(id: Int): Int

    @Query("SELECT COUNT(id) FROM tableDars")
    fun count(): Int




}