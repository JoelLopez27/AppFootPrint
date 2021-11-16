package com.example.appfootprint.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
        entities = [(UserRecollect::class)],
        version = 1,
        exportSchema = false)

abstract class RecollectDatabase : RoomDatabase() {

        abstract fun getRecollectDao(): UserRecollectDao

       companion object{
                @Volatile
                private var instance: RecollectDatabase? = null
                private val LOCK = Any()

                operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
                 instance ?: createDatabase(context).also { instance = it}
        }

                 private fun createDatabase(context: Context) =
                     Room.databaseBuilder(
                        context.applicationContext,
                     RecollectDatabase::class.java,
                        "recollect_db.db"
                     ).build()
        }
}