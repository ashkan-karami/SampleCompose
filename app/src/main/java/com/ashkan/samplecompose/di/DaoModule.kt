package com.ashkan.samplecompose.di

import com.ashkan.samplecompose.data.database.PostDao
import com.ashkan.samplecompose.data.database.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun providesPostsDao(
        database: PostDatabase,
    ): PostDao = database.getPostDao()
}
