package com.example.appfootprint.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserRecollectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserRecollectData(userRecollect: UserRecollect): Long

    @Query("SELECT * FROM UserRecollect")
    fun getAll(): LiveData<List<UserRecollect>>

    @Query("SELECT * FROM UserRecollect WHERE id = :id")
    suspend fun getUserRecollectData(id: Int): UserRecollect

    @Update
    fun updateUserRecollectData(userRecollect: UserRecollect): Int

    @Delete
    suspend fun deleteUserRecollectData(userRecollect: UserRecollect): Int
}