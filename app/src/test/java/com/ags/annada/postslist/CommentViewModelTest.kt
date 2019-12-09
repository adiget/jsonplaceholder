package com.ags.annada.postslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.room.entities.PostWithUser
import com.ags.annada.postslist.viewmodel.comments.CommentViewModel
import com.annada.android.sample.jsonposts.vm.PostWithUserViewModel
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
class CommentViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: CommentViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        sut = CommentViewModel()
    }

    @Test
    fun `test members updated when model changes`() {
        val comment = Comment(1, "dummy_body", "dummy_email", "dummy_name", 0)
        sut.bind(comment)

        Assert.assertEquals(
            "body should be dummy_body",
            "dummy_body",
            sut.body.value
        )

        Assert.assertEquals(
            "email should be dummy_email",
            "dummy_email",
            sut.email.value
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
    }

    @Test
    fun `test livedata notifies when it is observed`() {
        val observer = mock<Observer<String>>()
        sut.name.observeForever(observer)
        sut.body.observeForever(observer)

        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        val comment = Comment(1, "dummy_body", "dummy_email", "dummy_name", 0)
        sut.bind(comment)

        argumentCaptor.run {
            Mockito.verify(observer, Mockito.times(2)).onChanged(capture())
        }
    }

}