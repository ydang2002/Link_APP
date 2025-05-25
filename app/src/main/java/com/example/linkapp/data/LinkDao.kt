package com.example.linkapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LinkDao {
    @Query("SELECT * FROM LinkEntity")
    suspend fun getAll(): List<LinkEntity>

    @Insert
    suspend fun insert(link: LinkEntity)

    @Delete
    suspend fun delete(link: LinkEntity)
}