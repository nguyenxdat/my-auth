import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthStateManager {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _loginEvents = MutableSharedFlow<LoginEvent>()
    val loginEvents: SharedFlow<LoginEvent> = _loginEvents.asSharedFlow()

    suspend fun checkInitialAuth() {
        _authState.value = AuthState.Loading
        delay(2000)

        val isLoggedIn = checkTokenValidity()
        _authState.value = if (isLoggedIn) {
            AuthState.Authenticated
        } else {
            AuthState.Unauthenticated
        }
    }

    suspend fun login(username: String, password: String) {
        _authState.value = AuthState.Loading
        try {
            val result = performLoginApi(username, password)
            delay(200)
            if (result.isSuccess) {
                _authState.value = AuthState.Authenticated
                _loginEvents.emit(LoginEvent.Success)
            } else {
                _authState.value = AuthState.Error(result.error)
                _loginEvents.emit(LoginEvent.Error(result.error))
            }
        } catch (e: Exception) {
            _authState.value = AuthState.Error(e.message ?: "Unknown error")
            _loginEvents.emit(LoginEvent.Error(e.message ?: "Unknown error"))
        }
    }

    fun logout() {
        _authState.value = AuthState.Unauthenticated
        // Clear stored tokens, etc.
    }

    private fun checkTokenValidity(): Boolean {
        // Your logic to check stored token
        return false // Placeholder
    }

    private suspend fun performLoginApi(username: String, password: String): LoginResult {
        // Your API call logic
        GlobalUser.getInstance().saveData(username)
        return LoginResult(isSuccess = true, error = "")
    }
}

// Data classes
data class LoginResult(val isSuccess: Boolean, val error: String)

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class LoginEvent {
    object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}