package com.ashkan.samplecompose.data.repository.home

import com.ashkan.samplecompose.data.model.post.PostModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getArticles(): Flow<Result<List<PostModel>>>
}