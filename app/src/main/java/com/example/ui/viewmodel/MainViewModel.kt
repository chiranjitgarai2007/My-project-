package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Course
import com.example.data.model.CourseCategory
import com.example.data.model.User
import com.example.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _categories = MutableStateFlow<List<CourseCategory>>(emptyList())
    val categories: StateFlow<List<CourseCategory>> = _categories.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadInitialState()
    }

    private fun loadInitialState() {
        if (FirebaseRepository.isUserLoggedIn()) {
            val uid = FirebaseRepository.getCurrentUserId()
            fetchUserProfile(uid)
        }
        loadCoursesAndCategories()
    }

    fun loadCoursesAndCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                FirebaseRepository.getCourseCategories { cats ->
                    _categories.value = cats
                    FirebaseRepository.getCourses { courseList ->
                        _courses.value = courseList
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = "Failed to load skills catalog: ${e.localizedMessage}"
            }
        }
    }

    fun checkAuthState(onCheckCompleted: (isLoggedIn: Boolean, isProfileCompleted: Boolean) -> Unit) {
        viewModelScope.launch {
            val loggedIn = FirebaseRepository.isUserLoggedIn()
            if (loggedIn) {
                val uid = FirebaseRepository.getCurrentUserId()
                FirebaseRepository.getUserProfile(uid) { user ->
                    if (user != null) {
                        _currentUser.value = user
                        onCheckCompleted(true, user.isProfileCompleted)
                    } else {
                        onCheckCompleted(true, false)
                    }
                }
            } else {
                onCheckCompleted(false, false)
            }
        }
    }

    fun loginWithGoogle(email: String, fullName: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            FirebaseRepository.signInWithGoogle(email, fullName) { success, msg ->
                if (success) {
                    val uid = FirebaseRepository.getCurrentUserId()
                    FirebaseRepository.getUserProfile(uid) { user ->
                        _currentUser.value = user
                        _isLoading.value = false
                        if (msg != null) {
                            _errorMessage.value = msg
                        }
                        onResult(true)
                    }
                } else {
                    _isLoading.value = false
                    _errorMessage.value = msg ?: "Google Authentication failed. Please retry."
                    onResult(false)
                }
            }
        }
    }

    fun loginWithEmailPassword(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            // Convert simple login into standard google login entry
            val defaultName = email.substringBefore("@").replaceFirstChar { it.uppercase() }
            FirebaseRepository.signInWithGoogle(email, defaultName) { success, msg ->
                if (success) {
                    val uid = FirebaseRepository.getCurrentUserId()
                    FirebaseRepository.getUserProfile(uid) { user ->
                        _currentUser.value = user
                        _isLoading.value = false
                        if (msg != null) {
                            _errorMessage.value = msg
                        }
                        onResult(true)
                    }
                } else {
                    _isLoading.value = false
                    _errorMessage.value = msg ?: "Login failed. Please retry."
                    onResult(false)
                }
            }
        }
    }

    fun completeProfile(
        fullName: String,
        phone: String,
        address: String,
        profileImage: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val currentEmail = FirebaseRepository.getCurrentUserEmail()
            val user = User(
                id = FirebaseRepository.getCurrentUserId(),
                fullName = fullName,
                email = currentEmail,
                phone = phone,
                address = address,
                profileImage = profileImage,
                isProfileCompleted = true
            )
            FirebaseRepository.saveUserProfile(user) { success, msg ->
                _isLoading.value = false
                if (success) {
                    _currentUser.value = user
                    onSuccess()
                } else {
                    _errorMessage.value = msg ?: "Failed to save profile. Please try again."
                }
            }
        }
    }

    fun logoutUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            FirebaseRepository.logout()
            _currentUser.value = null
            onSuccess()
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun fetchUserProfile(userId: String) {
        FirebaseRepository.getUserProfile(userId) { user ->
            if (user != null) {
                _currentUser.value = user
            }
        }
    }
}
