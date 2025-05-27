package com.ashkan.samplecompose.di

import android.content.Context
import androidx.room.Room
import com.ashkan.samplecompose.data.database.PostDao
import com.ashkan.samplecompose.data.database.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestPostDatabaseModule {

    @Provides
    fun providePostDatabase(@ApplicationContext context: Context): PostDatabase =
        Room.inMemoryDatabaseBuilder(context, PostDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Provides
    fun providePostDao(db: PostDatabase): PostDao = db.getPostDao()
}