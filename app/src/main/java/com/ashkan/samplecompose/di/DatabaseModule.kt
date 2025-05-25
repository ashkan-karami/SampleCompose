package com.ashkan.samplecompose.di

import android.content.Context
import androidx.room.Room
import com.ashkan.samplecompose.data.database.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): PostDatabase = Room.databaseBuilder(
        context,
        PostDatabase::class.java,
        "post-database",
    ).build()
}
