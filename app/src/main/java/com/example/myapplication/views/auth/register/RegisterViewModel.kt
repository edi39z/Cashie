package com.example.myapplication.views.auth.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.format.DateTimeFormatter

class RegisterViewModel : ViewModel() {

    private val _firstname = MutableStateFlow("")
    val firstname: StateFlow<String> get() = _firstname

    private val _lastname = MutableStateFlow("")
    val lastname: StateFlow<String> get() = _lastname

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _toko = MutableStateFlow("")
    val toko: StateFlow<String> get() = _toko

    private val _dateOfBirth = MutableStateFlow("")
    val dateOfBirth: StateFlow<String> get() = _dateOfBirth


    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> get() = _gender

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> get() = _showDatePicker

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Update
    fun updateFirstname(newFirstname: String) {
        _firstname.value = newFirstname
    }

    fun updateLastname(newLastname: String) {
        _lastname.value = newLastname
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }
    fun updateToko(newToko: String) {
        _toko .value = newToko
    }

    fun updateDateOfBirth(newDateOfBirth: String) {
        _dateOfBirth.value = newDateOfBirth
    }
    fun updateGender(newGender: String) {
        _gender.value = newGender
    }
    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun updateShowDatePicker(showDatePicker: Boolean) {
        _showDatePicker.value = showDatePicker
    }

}