package com.ashkan.samplecompose.repository

import com.ashkan.samplecompose.data.model.post.PostModel
import com.ashkan.samplecompose.data.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHomeRepository : HomeRepository {

    override fun getPosts(): Flow<Result<List<PostModel>>> =
        flow {
            emit(
                Result.success(
                    List(10) { PostModel(it,it,"Title #$it", "Body #$it") }
                )
            )
        }
}