package com.lablabla.meow.data.local

import androidx.room.*
import com.lablabla.meow.data.local.entity.MeowUserEntity

@Dao
interface MeowUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeowUsers(user: List<MeowUserEntity>)

    @Query("DELETE from meowuserentity")
    suspend fun deleteAllUsers()

    @Query("SELECT * from meowuserentity")
    suspend fun getMeowUsers(): List<MeowUserEntity>
}