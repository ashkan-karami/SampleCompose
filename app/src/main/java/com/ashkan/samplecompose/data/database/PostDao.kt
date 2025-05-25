package com.ashkan.samplecompose.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query(
        value = """
        SELECT * FROM posts
        WHERE id = :postId
    """,
    )
    fun getPostById(postId: Int): Flow<PostEntity>

    @Query(value = "SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query(value = "SELECT * FROM posts")
    suspend fun getOneOffPosts(): List<PostEntity>

    @Query(
        value = """
        SELECT * FROM posts
        WHERE id IN (:postIds)
    """,
    )
    fun getPosts(postIds: Set<Int>): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePosts(posts: List<PostEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(post: PostEntity)

    @Query(
        value = """
            DELETE FROM posts
            WHERE id in (:postIds)
        """,
    )
    suspend fun deletePosts(postIds: List<Int>)

    @Query(
        value = """
            DELETE FROM posts
            WHERE id = :postId
        """,
    )
    suspend fun deletePost(postId: Int)
}