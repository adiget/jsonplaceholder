package com.ags.annada.postslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ags.annada.postslist.room.entities.PostWithUser
import com.ags.annada.postslist.viewmodel.postsusers.PostWithUserViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PostWithUserViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: PostWithUserViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        sut = PostWithUserViewModel()
    }

    @Test
    fun `test members updated when model changes`() {
        val postWithUser = PostWithUser(1, "dummy_title", "dummy_body", "dummy_name", "dummy_username")
        sut.bind(postWithUser)

        Assert.assertEquals(
            "id should be 1",
            1,
            sut.id.value
        )

        Assert.assertEquals(
            "title should be dummy_title",
            "dummy_title",
            sut.title.value
        )

        Assert.assertEquals(
            "title should be dummy_body",
            "dummy_body",
            sut.body.value
        )

        Assert.assertEquals(
            "title should be dummy_name",
            "dummy_name",
            sut.name.value
        )

        Assert.assertEquals(
            "title should be dummy_username",
            "dummy_username",
            sut.username.value
        )
    }

    @Test
    fun `test livedata notifies when it is observed`() {
        val observer = mock<Observer<String>>()
        sut.name.observeForever(observer)
        sut.body.observeForever(observer)

        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        val postWithUser = PostWithUser(1, "dummy_title", "dummy_body", "dummy_name", "dummy_username")
        sut.bind(postWithUser)

        argumentCaptor.run {
            Mockito.verify(observer, Mockito.times(2)).onChanged(capture())
        }
    }

}