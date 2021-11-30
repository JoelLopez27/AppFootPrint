package com.example.appfootprint.repository

import androidx.lifecycle.LiveData
import com.example.appfootprint.db.RecollectDatabase
import com.example.appfootprint.db.UserRecollect
import com.example.appfootprint.models.Article

class RecollectRepository(
    val db: RecollectDatabase
) {
    fun getTotalMaterial(): LiveData<Double> {
       return db.getRecollectDao().getTotalMaterial()
    }

    fun getTotalKgCo2(): LiveData<Double> {
        return db.getRecollectDao().getTotalKgCo2()
    }

    suspend fun insertUserRecollectData(userRecollect: UserRecollect) = db.getRecollectDao().insertUserRecollectData(userRecollect)

    fun getSavedRecollect() = db.getRecollectDao().getAll()

    suspend fun deleteRecollect(userRecollect: UserRecollect) = db.getRecollectDao().deleteUserRecollectData(userRecollect)

    fun getSizeRows() : LiveData<Int> = db.getRecollectDao().getSizeRows()

    fun getDate() : LiveData<String> = db.getRecollectDao().getDate()
}