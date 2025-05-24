package com.ashkan.samplecompose.data.api

import com.ashkan.samplecompose.data.model.post.PostModel
import retrofit2.http.GET

interface PostsApiService {

    @GET("posts")
    suspend fun getPosts(
    ): List<PostModel>
}