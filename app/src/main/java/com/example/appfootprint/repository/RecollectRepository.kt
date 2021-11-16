package com.example.appfootprint.repository

import com.example.appfootprint.db.RecollectDatabase
import com.example.appfootprint.db.UserRecollect
import com.example.appfootprint.models.Article

class RecollectRepository(
    val db: RecollectDatabase
) {

    suspend fun updateUserRecollectData(userRecollect: UserRecollect) = db.getRecollectDao().updateUserRecollectData(userRecollect)

    suspend fun getUserRecollectData(id: Int) = db.getRecollectDao().getUserRecollectData(id)

    suspend fun insertUserRecollectData(userRecollect: UserRecollect) = db.getRecollectDao().insertUserRecollectData(userRecollect)

    fun getSavedRecollect() = db.getRecollectDao().getAll()

    suspend fun deleteRecollect(userRecollect: UserRecollect) = db.getRecollectDao().deleteUserRecollectData(userRecollect)
}