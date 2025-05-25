package com.ashkan.samplecompose.data.network.repository.home

import com.ashkan.samplecompose.data.network.model.post.PostModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getPosts(): Flow<Result<List<PostModel>>>
}