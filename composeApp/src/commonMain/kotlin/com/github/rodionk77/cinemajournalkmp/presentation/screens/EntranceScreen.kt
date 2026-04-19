package com.github.rodionk77.cinemajournalkmp.presentation.screens

import io.github.aakira.napier.Napier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.email
import cinemajournalkmp.composeapp.generated.resources.email_is_busy
import cinemajournalkmp.composeapp.generated.resources.empty_fields
import cinemajournalkmp.composeapp.generated.resources.entrance
import cinemajournalkmp.composeapp.generated.resources.entrance_in_account
import cinemajournalkmp.composeapp.generated.resources.login_failed
import cinemajournalkmp.composeapp.generated.resources.password
import cinemajournalkmp.composeapp.generated.resources.registration
import cinemajournalkmp.composeapp.generated.resources.registration_account
import cinemajournalkmp.composeapp.generated.resources.registration_error
import cinemajournalkmp.composeapp.generated.resources.select_a_time
import cinemajournalkmp.composeapp.generated.resources.successful_registration
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.github.rodionk77.cinemajournalkmp.data.models.SignInRequest
import com.github.rodionk77.cinemajournalkmp.data.models.SignUpRequest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource


@Composable
fun EntranceScreen(navController: NavController, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel, galleryViewModel: GalleryViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (authViewModel.uiState.buttonRegistrationState) {
                checkAuth(snackbarHostState, authViewModel)
            }

            if (authViewModel.uiState.buttonLoginState) {
                checkLogin(navController, snackbarHostState, authViewModel, journalsViewModel, galleryViewModel)
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.uiState.emailText,
                label = { Text(text = stringResource(Res.string.email)) },
                onValueChange = {
                    authViewModel.changeEmailText(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.uiState.passwordText,
                label = { Text(text = stringResource(Res.string.password)) },
                onValueChange = {
                    authViewModel.changePasswordText(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            if (!authViewModel.uiState.isLoginScreen) {
                Row(
                    modifier = Modifier.align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = authViewModel.uiState.checkedState,
                        onCheckedChange = { authViewModel.changeCheckedState(it) }
                    )
                    Text("Детский аккаунт", fontSize = 18.sp, modifier = Modifier.padding(4.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                registrationScreen(snackbarHostState = snackbarHostState, authViewModel = authViewModel)
            } else {
                Spacer(modifier = Modifier.height(24.dp))
                loginScreen(navController = navController, snackbarHostState = snackbarHostState, authViewModel = authViewModel)
            }
        }
    }
}

@Composable
fun checkAuth(snackbarHostState: SnackbarHostState, authViewModel: AuthViewModel) {
    val emailBusyMsg = stringResource(Res.string.email_is_busy)
    val registrationErrorMsg = stringResource(Res.string.registration_error)
    val successfulRegistrationMsg = stringResource(Res.string.successful_registration)

    LaunchedEffect(authViewModel.uiState.returnMessage, authViewModel.uiState.buttonRegistrationState) {
        if (!authViewModel.uiState.buttonRegistrationState) return@LaunchedEffect
        val msg = authViewModel.uiState.returnMessage
        Napier.d("checkAuth: returnMessage='$msg'", tag = "EntranceScreen")
        when {
            msg.isEmpty() -> {
                Napier.d("checkAuth: waiting for server response...", tag = "EntranceScreen")
            }
            msg.contains("400") || msg.contains("already in use", ignoreCase = true) -> {
                Napier.w("Registration failed: email already in use. Message: $msg", tag = "EntranceScreen")
                snackbarHostState.showSnackbar(emailBusyMsg)
                authViewModel.changeButtonRegistrationState(false)
                authViewModel.changeReturnMessage("")
            }
            msg.contains("401") || msg.contains("error", ignoreCase = true) -> {
                Napier.w("Registration failed: server error. Message: $msg", tag = "EntranceScreen")
                snackbarHostState.showSnackbar(registrationErrorMsg)
                authViewModel.changeButtonRegistrationState(false)
                authViewModel.changeReturnMessage("")
            }
            else -> {
                Napier.d("Registration successful: $msg", tag = "EntranceScreen")
                snackbarHostState.showSnackbar(successfulRegistrationMsg)
                authViewModel.changeButtonRegistrationState(false)
                authViewModel.changeScreenState(!authViewModel.uiState.isLoginScreen)
                authViewModel.changeReturnMessage("")
            }
        }
    }
}

@Composable
fun checkLogin(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    authViewModel: AuthViewModel,
    journalsViewModel: JournalsViewModel,
    galleryViewModel: GalleryViewModel
) {
    val loginFailedMsg = stringResource(Res.string.login_failed)

    LaunchedEffect(authViewModel.uiState.user, authViewModel.uiState.isLoginProcess, journalsViewModel.uiState.user) {
        val authUser = authViewModel.uiState.user
        val journalsUser = journalsViewModel.uiState.user
        Napier.d("checkLogin: authUser=${authUser?.email}, journalsUser=${journalsUser?.email}, isLoginProcess=${authViewModel.uiState.isLoginProcess}", tag = "EntranceScreen")

        if (authUser != null) {
            if (journalsUser == null) {
                // Шаг 1: сервер вернул пользователя — ищем его в локальной БД
                Napier.d("Login succeeded, fetching user from local DB (id=${authUser.id})", tag = "EntranceScreen")
                journalsViewModel.getUserFromDB(authUser.id)
            } else {
                // Шаг 2: пользователь найден в локальной БД — сохраняем и переходим
                Napier.d("Local DB user found, saving and navigating", tag = "EntranceScreen")
                journalsViewModel.startUpdateLocalDB(journalsUser)
                authViewModel.saveUserToDatabase(authUser)
                galleryViewModel.refreshTops(authUser.role ?: 0)
                navController.navigate("JournalsScreen") { popUpTo(0) }
                authViewModel.changeButtonLoginState(false)
            }
        } else if (!authViewModel.uiState.isLoginProcess) {
            Napier.w("Login failed: no user returned and not in process", tag = "EntranceScreen")
            snackbarHostState.showSnackbar(loginFailedMsg)
            authViewModel.changeButtonLoginState(false)
        }
    }
}

@Composable
fun registrationScreen(snackbarHostState: SnackbarHostState, authViewModel: AuthViewModel) {
    val emptyErrorMessage = stringResource(Res.string.empty_fields)
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            if (authViewModel.uiState.emailText.isNotEmpty() && authViewModel.uiState.passwordText.isNotEmpty()) {
                val data = SignUpRequest(
                    username = authViewModel.uiState.emailText,
                    email = authViewModel.uiState.emailText,
                    password = authViewModel.uiState.passwordText,
                    role = authViewModel.uiState.checkedState.compareTo(false)
                )
                Napier.d("Sign up attempt: email=${data.email}, isKid=${authViewModel.uiState.checkedState}", tag = "EntranceScreen")
                authViewModel.signUpUser(data)
                authViewModel.changeButtonRegistrationState(true)
            } else {
                Napier.w("Sign up blocked: empty email or password", tag = "EntranceScreen")
                scope.launch {
                    snackbarHostState.showSnackbar(emptyErrorMessage)
                }
            }
        },
    ) {
        Column {
            Text(stringResource(Res.string.registration), fontSize = 24.sp)
        }
    }

    TextButton(onClick = {
        Napier.d("Switched to login screen", tag = "EntranceScreen")
        authViewModel.changeScreenState(!authViewModel.uiState.isLoginScreen)
    }) {
        Column {
            Text(stringResource(Res.string.entrance_in_account), fontSize = 18.sp)
        }
    }
}

@Composable
fun loginScreen(navController: NavController, snackbarHostState: SnackbarHostState, authViewModel: AuthViewModel) {
    val emptyErrorMessage = stringResource(Res.string.select_a_time)
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            if (authViewModel.uiState.emailText.isNotEmpty() && authViewModel.uiState.passwordText.isNotEmpty()) {
                val data = SignInRequest(
                    username = authViewModel.uiState.emailText,
                    password = authViewModel.uiState.passwordText
                )
                Napier.d("Sign in attempt: email=${data.username}", tag = "EntranceScreen")
                authViewModel.signInUser(data)
                authViewModel.changeButtonLoginState(true)
            } else {
                Napier.w("Sign in blocked: empty email or password", tag = "EntranceScreen")
                scope.launch {
                    snackbarHostState.showSnackbar(emptyErrorMessage)
                }
            }
        },
    ) {
        Column {
            Text(stringResource(Res.string.entrance), fontSize = 24.sp)
        }
    }

    TextButton(onClick = {
        Napier.d("Switched to registration screen", tag = "EntranceScreen")
        authViewModel.changeScreenState(!authViewModel.uiState.isLoginScreen)
    }) {
        Column {
            Text(stringResource(Res.string.registration_account), fontSize = 18.sp)
        }
    }
}
