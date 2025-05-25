package com.ashkan.samplecompose.data.network.repository.home

import com.ashkan.samplecompose.data.network.api.PostsApiService
import com.ashkan.samplecompose.data.network.core.apiWrapper
import com.ashkan.samplecompose.data.network.model.post.PostModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: PostsApiService
): HomeRepository {

    override fun getPosts(): Flow<Result<List<PostModel>>> =
        apiWrapper {
            apiService.getPosts()
        }
}