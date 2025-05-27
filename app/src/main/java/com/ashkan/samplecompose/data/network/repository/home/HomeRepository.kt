package com.ashkan.samplecompose.data.network.repository.home

import com.ashkan.samplecompose.data.network.model.post.PostModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun cachedPosts(): List<PostModel>

    suspend fun getRemotePosts(): Flow<Result<List<PostModel>>>
}