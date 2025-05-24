package com.ashkan.samplecompose.data.repository.home

import com.ashkan.samplecompose.data.api.PostsApiService
import com.ashkan.samplecompose.data.core.apiWrapper
import com.ashkan.samplecompose.data.model.post.PostModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: PostsApiService
): HomeRepository {

    override fun getArticles(): Flow<Result<List<PostModel>>> =
        apiWrapper {
            apiService.getPosts()
        }
}