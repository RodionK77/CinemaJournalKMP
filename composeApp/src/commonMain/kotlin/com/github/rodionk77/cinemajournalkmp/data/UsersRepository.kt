package com.github.rodionk77.cinemajournalkmp.data

import com.github.rodionk77.cinemajournalkmp.data.database.UsersDAO
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.AuthResponse
import com.github.rodionk77.cinemajournalkmp.data.models.SignInRequest
import com.github.rodionk77.cinemajournalkmp.data.models.SignUpRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val PATH = "/api/auth"

class UsersRepository(private val usersDAO: UsersDAO, private val httpClient: HttpClient) {

    suspend fun signUpUser(request: SignUpRequest): AuthResponse =
        httpClient.post("$PATH/signup") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun signOutUser(): AuthResponse =
        httpClient.post("$PATH/signout").body()

    suspend fun signInUser(request: SignInRequest): AuthResponse =
        httpClient.post("$PATH/signin") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun saveUserToDatabase(user: User) = usersDAO.saveUser(user)

    suspend fun getAllUsers(): List<User> = usersDAO.getAllUsers()

    suspend fun deleteUserById(id: Int) = usersDAO.deleteUserById(id)

    fun deleteWatchedMovieById(id: Long) { }
    fun deleteMovieToWatchById(id: Long) { }
}
