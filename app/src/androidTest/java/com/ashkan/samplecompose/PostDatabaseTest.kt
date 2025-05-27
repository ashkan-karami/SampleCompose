package com.ashkan.samplecompose

import com.ashkan.samplecompose.data.database.PostDao
import com.ashkan.samplecompose.data.database.PostDatabase
import com.ashkan.samplecompose.data.network.model.post.PostModel
import com.ashkan.samplecompose.data.network.model.post.asEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class PostDatabaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject lateinit var db: PostDatabase
    @Inject lateinit var dao: PostDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndReadNote() = runTest {
        val items = List(5) { PostModel(it,it,"Title #$it", "Body #$it") }

        dao.insertOrIgnorePosts(items.map { it.asEntity() })
        val posts = dao.getAllPosts().first()
        assertEquals(5, posts.size)
        assertEquals(items.first().title, posts.first().title)
    }

    @Test
    fun insertMoreThan10_andDeleteOldOnes() = runTest {
        val items = List(15) { PostModel(it,it,"Title #$it", "Body #$it") }

        dao.insertOrIgnorePosts(items.map { it.asEntity() })
        dao.deleteOlderNotes()

        val result = dao.getAllPosts().first()
        assertEquals(10, result.size)
    }
}