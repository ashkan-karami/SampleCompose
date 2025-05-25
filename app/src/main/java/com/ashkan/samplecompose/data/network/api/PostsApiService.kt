package com.ashkan.samplecompose.data.network.api

import com.ashkan.samplecompose.data.network.model.post.PostModel
import retrofit2.http.GET

interface PostsApiService {

    @GET("posts")
    suspend fun getPosts(
    ): List<PostModel>
}