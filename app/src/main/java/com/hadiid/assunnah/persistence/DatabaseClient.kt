package com.hadiid.assunnah.persistence

import android.content.Context
import androidx.room.Room

private const val dbName = "AssunnahUnifa28.db"

object DatabaseClient {


    //context setiap mmggil DB
    fun getService(context: Context): DatabaseService{
        return Room.databaseBuilder(
            context,
            DatabaseService::class.java,
            dbName
        ).fallbackToDestructiveMigration() //
            .build()
    }

}