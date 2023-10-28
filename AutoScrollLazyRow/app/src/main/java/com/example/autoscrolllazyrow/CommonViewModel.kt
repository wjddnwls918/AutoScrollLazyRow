package com.example.autoscrolllazyrow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoscrolllazyrow.common.OpenNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor() : ViewModel() {
    private val _openActivityNavigator = MutableStateFlow<OpenNavigator>(OpenNavigator.None)
    // The UI collects from this StateFlow to get its state updates
    val openActivityNavigator = _openActivityNavigator.asStateFlow()

    fun openSecondActivity() {
        viewModelScope.launch {
            _openActivityNavigator.emit(OpenNavigator.Open)
            setNavigatorStatus(OpenNavigator.None)
        }
    }

    private fun setNavigatorStatus(status: OpenNavigator) {
        viewModelScope.launch {
            _openActivityNavigator.emit(status)
        }
    }
}