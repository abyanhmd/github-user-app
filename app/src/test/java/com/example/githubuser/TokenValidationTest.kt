package com.example.githubuser

import com.example.githubuser.network.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenValidationTest {
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
    fun testGetUsers_TokenValid_Correct() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Create request using testUserService
        val request =
            createMockApiConfig(key = "token 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").getUsers("abyanhmd")

        // Execute the request
        val response = request.execute()

        // Verify the response
        Assert.assertTrue(response.isSuccessful)
        Assert.assertEquals(200, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUsers_TokenHasWhitespaces_Correct() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Token has leading and trailing spaces
        val key = " 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 "

        // Create request using testUserService
        val request = createMockApiConfig(key = "token $key").getUsers("abyanhmd")

        // Execute the request
        val response = request.execute()

        // Verify the response
        Assert.assertTrue(response.isSuccessful)
        Assert.assertEquals(200, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUsers_TokenEmpty_Incorrect() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(401)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Create request using testUserService
        val request = createMockApiConfig(key = "token ").getUsers("abyanhmd")

        // Execute the request
        val response = request.execute()

        // Verify the response
        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(401, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUsers_TokenExpired_Incorrect() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(401)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Create request using testUserService
        val request = createMockApiConfig(key = "token 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").getUsers("abyanhmd")

        // Execute the request
        val response = request.execute()

        // Verify the response
        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(401, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUsers_TokenInvalidFormat_Incorrect() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(401)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Create request using testUserService
        val request = createMockApiConfig(key = "Bearer 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").getUsers("abyanhmd")

        // Execute the request
        val response = request.execute()


        // Verify the response
        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(401, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUsers_TokenExceedsLimit_Incorrect() {
        // Set up the mock web server
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up the mock response
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(401)

        // Enqueue the mock response
        mockWebServer.enqueue(mockResponse)

        // Create request using testUserService
        val request = createMockApiConfig(key = "token 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YdBvMx66kSG60lOdF6kSG6kSG60").getUsers("abyanhmd")

        // Execute the request
        val response = request.execute()


        // Verify the response
        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(401, response.code())

        // Stop the MockWebServer
        mockWebServer.shutdown()
    }
}