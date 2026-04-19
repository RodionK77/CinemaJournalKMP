package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.userUseCases.DeleteUserByIdUseCase
import com.example.cinemajournal.Domain.userUseCases.GetAllUsersUseCase
import com.example.cinemajournal.Domain.userUseCases.SaveUserToDatabaseUseCase
import com.example.cinemajournal.Domain.userUseCases.SignInUserUseCase
import com.example.cinemajournal.Domain.userUseCases.SignOutUserUseCase
import com.example.cinemajournal.Domain.userUseCases.SignUpUserUseCase
import com.github.rodionk77.cinemajournalkmp.data.models.AuthResponse
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.SignInRequest
import com.github.rodionk77.cinemajournalkmp.data.models.SignUpRequest
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

data class EntranceUiState(
    val user: User? = null,
    val allUsers: List<User> = emptyList(),
    val isLoginScreen: Boolean = false,
    val isLoginProcess: Boolean = false,
    val isRefreshLoginProcess: Boolean = false,
    val buttonRegistrationState: Boolean = false,
    val buttonLoginState: Boolean = false,
    val emailText: String = "",
    val passwordText: String = "",
    val checkedState: Boolean = false,
    val returnMessage: String = ""
)

class AuthViewModel (
    private val signUpUserUseCase: SignUpUserUseCase,
    private val signInUserUseCase: SignInUserUseCase,
    private val signOutUserUseCase: SignOutUserUseCase,
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val saveUserToDatabaseUseCase: SaveUserToDatabaseUseCase, ) : ViewModel() {

    var uiState by mutableStateOf(EntranceUiState())
        private set

    fun changeScreenState(b: Boolean){
        uiState = uiState.copy(isLoginScreen = b)
    }

    fun changeButtonRegistrationState(b: Boolean){
        uiState = uiState.copy(buttonRegistrationState = b)
    }

    fun changeButtonLoginState(b: Boolean){
        uiState = uiState.copy(buttonLoginState = b)
    }

    fun changeCheckedState(b: Boolean){
        uiState = uiState.copy(checkedState = b)
    }

    fun changeReturnMessage(s: String){
        uiState = uiState.copy(returnMessage = "")
    }

    fun changeEmailText(s: String){
        uiState = uiState.copy(emailText = s)
    }

    fun changePasswordText(s: String){
        uiState = uiState.copy(passwordText = s)
    }

    fun changeUser(user: User?){
        uiState = uiState.copy(user = user)
    }

    fun signUpUser(request: SignUpRequest) {
        viewModelScope.launch {
            kotlin.runCatching {
                signUpUserUseCase(request) }
                .onSuccess { response ->
                    Napier.d("Пользователь успешно зарегестрирвован", tag = "AuthViewModel")
                    uiState = uiState.copy(returnMessage = response.message ?: "")
                }
                .onFailure {
                    Napier.e("Проблема при регистрации пользователя", tag = "AuthViewModel")
                    Napier.e("ONO - ${it.message}", tag = "AuthViewModel")
                    uiState = uiState.copy(returnMessage = it.message ?: "")
                }
        }
    }

    fun signInUser(request: SignInRequest): AuthResponse {
        uiState = uiState.copy(isLoginProcess = true)
        var message = AuthResponse()
        viewModelScope.launch {
            kotlin.runCatching { signInUserUseCase(request) }
                .onSuccess { response ->
                    uiState = uiState.copy(user = User(id = response.id!!, username = response.username?: "", email = response.email?: "", role = response.role?: 0))
                    message = response
                    Napier.d("Пользователь успешно вошёл", tag = "AuthViewModel")
                    Napier.d(message.email ?: "empty", tag = "AuthViewModel")
                    uiState = uiState.copy(isLoginProcess = false)
                }
                .onFailure {
                    message.message = it.message
                    Napier.e("Проблема при входе пользователя", tag = "AuthViewModel")
                    Napier.e(message!!.message ?: "empty", tag = "AuthViewModel")
                    uiState = uiState.copy(isLoginProcess = false)
                }
        }
        return message
    }

    fun signOutUser() {
        viewModelScope.launch {
            kotlin.runCatching { signOutUserUseCase() }
                .onSuccess { response ->
                    Napier.d(response.message ?: "empty", tag = "AuthViewModel")
                }
                .onFailure {
                    Napier.e(it.message ?: "empty", tag = "AuthViewModel")
                }
        }
    }

    fun saveUserToDatabase(user: User) {
        viewModelScope.launch {
            saveUserToDatabaseUseCase(user)
        }
    }

    fun getAllUser() {
        uiState = uiState.copy(isRefreshLoginProcess = true)
        viewModelScope.launch {
            val users = getAllUsersUseCase()
            Napier.d(users.toString(), tag = "AuthViewModel")
            if(users.isNotEmpty()){
                uiState = uiState.copy(user = users[0])
            }
        }
    }

    fun deleteUserById(id: Int){
        viewModelScope.launch {
            deleteUserByIdUseCase(id)
        }
    }

}
