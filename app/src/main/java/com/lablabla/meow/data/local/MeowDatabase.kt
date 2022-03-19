package com.lablabla.meow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lablabla.meow.data.local.entity.MeowUserEntity

@Database(
    entities = [MeowUserEntity::class],
    version = 1
)

abstract class MeowDatabase: RoomDatabase() {
    abstract val dao: MeowUserDao
}