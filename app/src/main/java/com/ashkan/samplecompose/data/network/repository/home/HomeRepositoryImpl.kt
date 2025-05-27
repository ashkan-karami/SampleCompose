package com.ashkan.samplecompose.data.network.repository.home

import com.ashkan.samplecompose.data.database.PostDao
import com.ashkan.samplecompose.data.database.toPostModel
import com.ashkan.samplecompose.data.network.api.PostsApiService
import com.ashkan.samplecompose.data.network.core.apiWrapper
import com.ashkan.samplecompose.data.network.model.post.PostModel
import com.ashkan.samplecompose.data.network.model.post.asEntity
import com.ashkan.samplecompose.util.ConnectivityManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val postDao: PostDao,
    private val apiService: PostsApiService
) : HomeRepository {

    override suspend fun cachedPosts(): List<PostModel> =
        postDao.getOneOffPosts().map { it.toPostModel() }

    override suspend fun getRemotePosts(): Flow<Result<List<PostModel>>> {
        return apiWrapper {
            val freshPosts = apiService.getPosts()
            postDao.insertOrIgnorePosts(freshPosts.map { it.asEntity() })
            postDao.deleteOlderNotes()
            freshPosts // Return the response to handle errors.
        }
    }
}