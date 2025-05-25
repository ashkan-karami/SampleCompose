package com.ashkan.samplecompose.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ashkan.samplecompose.data.network.model.post.PostModel

@Entity(
    tableName = "posts",
)
data class PostEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "title", defaultValue = "")
    val title: String,
    @ColumnInfo(name = "body", defaultValue = "")
    val body: String,
)

fun PostEntity.toPostModel() = PostModel(
    id = id,
    userId = userId,
    title = title,
    body = body
)