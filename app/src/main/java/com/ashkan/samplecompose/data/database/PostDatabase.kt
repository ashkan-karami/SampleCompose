package com.ashkan.samplecompose.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PostEntity::class
    ],
    version = 2,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class)
//    ],
//    exportSchema = true
)
abstract class PostDatabase: RoomDatabase() {

    abstract fun getPostDao(): PostDao
}