package com.ashkan.samplecompose

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ashkan.samplecompose.data.cache.DataStoreManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DataStoreTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun saveAndReadEmail() = runTest {
        val testEmail = "email@test.com"
        dataStoreManager.saveEmailAddress(testEmail)

        val result = dataStoreManager.getEmailAddress().first()
        assertEquals(testEmail, result)
    }
}