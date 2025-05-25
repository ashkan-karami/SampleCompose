package com.ashkan.samplecompose.data.database

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

object DatabaseMigrations {

    @RenameColumn(
        tableName = "posts",
        fromColumnName = "postBody",
        toColumnName = "body",
    )
    class Schema1to2 : AutoMigrationSpec
}