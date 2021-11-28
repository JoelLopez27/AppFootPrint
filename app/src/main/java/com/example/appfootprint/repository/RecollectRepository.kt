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

    fun updateUserRecollectData(userRecollect: UserRecollect) = db.getRecollectDao().updateUserRecollectData(userRecollect)

    suspend fun getUserRecollectData(id: Int) = db.getRecollectDao().getUserRecollectData(id)

    suspend fun insertUserRecollectData(userRecollect: UserRecollect) = db.getRecollectDao().insertUserRecollectData(userRecollect)

    fun getSavedRecollect() = db.getRecollectDao().getAll()

    suspend fun deleteRecollect(userRecollect: UserRecollect) = db.getRecollectDao().deleteUserRecollectData(userRecollect)

    fun getSizeRows() : LiveData<Int> = db.getRecollectDao().getSizeRows()
}