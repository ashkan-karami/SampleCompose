package com.ashkan.samplecompose.repository

import com.ashkan.samplecompose.data.network.model.post.PostModel
import com.ashkan.samplecompose.data.network.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHomeRepository : HomeRepository {

    override suspend fun cachedPosts(): List<PostModel> =
        List(5) { PostModel(it,it,"Title #$it", "Body #$it") }

    override suspend fun getRemotePosts(): Flow<Result<List<PostModel>>> =
        flow {
            emit(
                Result.success(
                    List(5) { PostModel(it,it,"Title #$it", "Body #$it") }
                )
            )
        }
}