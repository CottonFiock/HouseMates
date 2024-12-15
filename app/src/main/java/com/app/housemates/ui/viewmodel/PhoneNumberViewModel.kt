package com.app.housemates.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.housemates.data.model.PhoneNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhoneNumberViewModel : ViewModel() {
    private val _numbers = MutableStateFlow<List<PhoneNumber>>(emptyList())
    val numbers: StateFlow<List<PhoneNumber>> = _numbers

    init{
        loadNumbers()
    }

    private fun loadNumbers() {
        viewModelScope.launch {
            // Replace with actual data loading logic
            _numbers.value = listOf(
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("idraulico", "+39 753249"),
                PhoneNumber("elettricista", "7524942")
            )
        }
    }

    fun addPhoneNumber(number : PhoneNumber) {
        _numbers.value += number
    }
}
