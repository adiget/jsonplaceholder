package com.ags.annada.postslist

import com.ags.annada.postslist.api.ApiService
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.room.entities.Post
import com.ags.annada.postslist.room.entities.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.internal.matchers.InstanceOf
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {
    lateinit var service: ApiService

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiService::class.java)
    }

    @Test
    fun should_callServiceWithCoroutine() {
        runBlocking {
            val posts = service.getPosts()

            Assert.assertTrue(posts.isNotEmpty())
        }
    }

    @Test
    fun should_postCallReturnsListOfPosts() {
        runBlocking {
            val posts = service.getPosts()

            Assert.assertTrue(posts is List<Post>)
        }
    }

    @Test
    fun should_userCallReturnsListOfUsers() {
        runBlocking {
            val users = service.getUsers()

            Assert.assertTrue(users is List<User>)
        }
    }

    @Test
    fun should_commentCallReturnsListOfComments() {
        runBlocking {
            val comments = service.getComments()

            Assert.assertTrue(comments is List<Comment>)
        }
    }
}