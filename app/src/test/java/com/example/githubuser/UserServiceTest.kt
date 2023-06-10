package com.example.githubuser

import com.example.githubuser.network.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserServiceTest {
    private fun createMockApiConfig(key: String): UserService {
        // Create retrofit configuration
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val modifiedRequest = originalRequest.newBuilder()
                    .header("Authorization", key)
                    .build()
                chain.proceed(modifiedRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        return retrofit.create(UserService::class.java)
    }

    @Test
    fun testGetUsers_SuccessfulResponse() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Update the Retrofit configuration to use the mock server's base URL
        val retrofit = RetrofitConfig.getUserService()
        val request = retrofit.getUsers("abyanhmd")

        // Execute the request
        val response = request.execute()

        // Verify the response
        assertTrue(response.isSuccessful)
        val responseBody = response.body()
        assertNotNull(responseBody)

        // Verify the response matches the expected response
        val expectedResponse = UserResponse(
            totalCount = 1,
            incompleteResults = false,
            items = listOf(
                ItemsItem(
                    login = "abyanhmd",
                    id = 74693948,
                    nodeId = "MDQ6VXNlcjc0NjkzOTQ4",
                    avatarUrl = "https://avatars.githubusercontent.com/u/74693948?v=4",
                    gravatarId = "",
                    url = "https://api.github.com/users/abyanhmd",
                    htmlUrl = "https://github.com/abyanhmd",
                    followersUrl = "https://api.github.com/users/abyanhmd/followers",
                    followingUrl = "https://api.github.com/users/abyanhmd/following{/other_user}",
                    gistsUrl = "https://api.github.com/users/abyanhmd/gists{/gist_id}",
                    starredUrl = "https://api.github.com/users/abyanhmd/starred{/owner}{/repo}",
                    subscriptionsUrl = "https://api.github.com/users/abyanhmd/subscriptions",
                    organizationsUrl = "https://api.github.com/users/abyanhmd/orgs",
                    reposUrl = "https://api.github.com/users/abyanhmd/repos",
                    eventsUrl = "https://api.github.com/users/abyanhmd/events{/privacy}",
                    receivedEventsUrl = "https://api.github.com/users/abyanhmd/received_events",
                    type = "User",
                    siteAdmin = false,
                    score = 1.0
                )
            )
        )
        // Match the items of the response with the expected items
        assertEquals(expectedResponse.totalCount, responseBody!!.totalCount)
        assertEquals(expectedResponse.incompleteResults, responseBody.incompleteResults)
        assertEquals(expectedResponse.items.size, responseBody.items.size)

        for (i in expectedResponse.items.indices) {
            val expectedItem = expectedResponse.items[i]
            val actualItem = responseBody.items[i]
            assertEquals(expectedItem.login, actualItem.login)
            assertEquals(expectedItem.id, actualItem.id)
            assertEquals(expectedItem.nodeId, actualItem.nodeId)
            assertEquals(expectedItem.avatarUrl, actualItem.avatarUrl)
            assertEquals(expectedItem.gravatarId, actualItem.gravatarId)
            assertEquals(expectedItem.url, actualItem.url)
            assertEquals(expectedItem.htmlUrl, actualItem.htmlUrl)
            assertEquals(expectedItem.followersUrl, actualItem.followersUrl)
            assertEquals(expectedItem.followingUrl, actualItem.followingUrl)
            assertEquals(expectedItem.gistsUrl, actualItem.gistsUrl)
            assertEquals(expectedItem.starredUrl, actualItem.starredUrl)
            assertEquals(expectedItem.subscriptionsUrl, actualItem.subscriptionsUrl)
            assertEquals(expectedItem.organizationsUrl, actualItem.organizationsUrl)
            assertEquals(expectedItem.reposUrl, actualItem.reposUrl)
            assertEquals(expectedItem.eventsUrl, actualItem.eventsUrl)
            assertEquals(expectedItem.receivedEventsUrl, actualItem.receivedEventsUrl)
            assertEquals(expectedItem.type, actualItem.type)
            assertEquals(expectedItem.siteAdmin, actualItem.siteAdmin)
        }

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUsers_EmptyResponse() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Update the Retrofit configuration to use the mock server's base URL
        val retrofit = RetrofitConfig.getUserService()
        val request = retrofit.getUsers("abyanhmd123")

        // Execute the request
        val response = request.execute()

        // Verify the response
        assertTrue(response.isSuccessful)
        val responseBody = response.body()
        assertNotNull(responseBody)
        assertTrue(responseBody!!.items.isEmpty())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUsers_Unauthorized() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(401)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Update the Retrofit configuration to use the mock server's base URL
        val request = createMockApiConfig(key = "token ").getUsers("abyanhmd")

        // Execute the request
        val response = request.execute()

        // Verify the response
        assertFalse(response.isSuccessful)
        assertEquals(401, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUserDetail_SuccessfulResponse() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Update the Retrofit configuration to use the mock server's base URL
        val retrofit = RetrofitConfig.getUserService()
        val request = retrofit.getUserDetail("abyanhmd")

        // Execute the request
        val response = request.execute()

        // Verify the response
        assertTrue(response.isSuccessful)
        val responseBody = response.body()
        assertNotNull(responseBody)

        // Verify the response matches the expected response
        // Verify the response matches the expected response
        val expectedResponse = UserDetailResponse(
            gistsUrl = "https://api.github.com/users/abyanhmd/gists{/gist_id}",
            reposUrl = "https://api.github.com/users/abyanhmd/repos",
            followingUrl = "https://api.github.com/users/abyanhmd/following{/other_user}",
            twitterUsername = null,
            bio = null,
            createdAt = "2020-11-19T02:50:57Z",
            login = "abyanhmd",
            type = "User",
            blog = "",
            subscriptionsUrl = "https://api.github.com/users/abyanhmd/subscriptions",
            updatedAt = "2023-06-03T04:13:18Z",
            siteAdmin = false,
            company = null,
            id = 74693948,
            publicRepos = 16,
            gravatarId = "",
            email = null,
            organizationsUrl = "https://api.github.com/users/abyanhmd/orgs",
            hireable = null,
            starredUrl = "https://api.github.com/users/abyanhmd/starred{/owner}{/repo}",
            followersUrl = "https://api.github.com/users/abyanhmd/followers",
            publicGists = 0,
            url = "https://api.github.com/users/abyanhmd",
            receivedEventsUrl = "https://api.github.com/users/abyanhmd/received_events",
            followers = 5,
            avatarUrl = "https://avatars.githubusercontent.com/u/74693948?v=4",
            eventsUrl = "https://api.github.com/users/abyanhmd/events{/privacy}",
            htmlUrl = "https://github.com/abyanhmd",
            following = 5,
            name = null,
            location = null,
            nodeId = "MDQ6VXNlcjc0NjkzOTQ4"
        )

        // Assert each attribute of the response matches the expected value
        assertEquals(expectedResponse.gistsUrl, responseBody!!.gistsUrl)
        assertEquals(expectedResponse.reposUrl, responseBody.reposUrl)
        assertEquals(expectedResponse.followingUrl, responseBody.followingUrl)
        assertEquals(expectedResponse.twitterUsername, responseBody.twitterUsername)
        assertEquals(expectedResponse.bio, responseBody.bio)
        assertEquals(expectedResponse.createdAt, responseBody.createdAt)
        assertEquals(expectedResponse.login, responseBody.login)
        assertEquals(expectedResponse.type, responseBody.type)
        assertEquals(expectedResponse.blog, responseBody.blog)
        assertEquals(expectedResponse.subscriptionsUrl, responseBody.subscriptionsUrl)
        assertEquals(expectedResponse.updatedAt, responseBody.updatedAt)
        assertEquals(expectedResponse.siteAdmin, responseBody.siteAdmin)
        assertEquals(expectedResponse.company, responseBody.company)
        assertEquals(expectedResponse.id, responseBody.id)
        assertEquals(expectedResponse.publicRepos, responseBody.publicRepos)
        assertEquals(expectedResponse.gravatarId, responseBody.gravatarId)
        assertEquals(expectedResponse.email, responseBody.email)
        assertEquals(expectedResponse.organizationsUrl, responseBody.organizationsUrl)
        assertEquals(expectedResponse.hireable, responseBody.hireable)
        assertEquals(expectedResponse.starredUrl, responseBody.starredUrl)
        assertEquals(expectedResponse.followersUrl, responseBody.followersUrl)
        assertEquals(expectedResponse.publicGists, responseBody.publicGists)
        assertEquals(expectedResponse.url, responseBody.url)
        assertEquals(expectedResponse.receivedEventsUrl, responseBody.receivedEventsUrl)
        assertEquals(expectedResponse.followers, responseBody.followers)
        assertEquals(expectedResponse.avatarUrl, responseBody.avatarUrl)
        assertEquals(expectedResponse.eventsUrl, responseBody.eventsUrl)
        assertEquals(expectedResponse.htmlUrl, responseBody.htmlUrl)
        assertEquals(expectedResponse.following, responseBody.following)
        assertEquals(expectedResponse.name, responseBody.name)
        assertEquals(expectedResponse.location, responseBody.location)
        assertEquals(expectedResponse.nodeId, responseBody.nodeId)

    }

    @Test
    fun testGetUserDetail_EmptyResponse() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Update the Retrofit configuration to use the mock server's base URL
        val retrofit = RetrofitConfig.getUserService()
        val request = retrofit.getUserDetail("abyanhmd123")

        // Execute the request
        val response = request.execute()

        // Verify the response
        assertFalse(response.isSuccessful)
        assertEquals(404, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUserDetail_Unauthorized() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(401)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Update the Retrofit configuration to use the mock server's base URL
        val request = createMockApiConfig(key = "token ").getUserDetail("abyanhmd")

        // Execute the request
        val response = request.execute()

        // Verify the response
        assertFalse(response.isSuccessful)
        assertEquals(401, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testTokenHiding() {
        val token = UserService.key
//        assertEquals(token, "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")
        assertNotEquals(token, "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")
    }
}