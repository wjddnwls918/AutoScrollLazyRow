package com.example.autoscrolllazyrow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoscrolllazyrow.common.OpenNavigator
import com.example.autoscrolllazyrow.common.PaymentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor() : ViewModel() {

    init {
        viewModelScope.launch {
            _paymentList.value = makeDummyData()
        }
    }

    private val _openActivityNavigator = MutableStateFlow<OpenNavigator>(OpenNavigator.None)
    // The UI collects from this StateFlow to get its state updates
    val openActivityNavigator = _openActivityNavigator.asStateFlow()

    private val _paymentList = MutableStateFlow<List<PaymentEntity>>(listOf())
    val paymentList = _paymentList.asStateFlow()

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


    private fun makeDummyData(): List<PaymentEntity> {
        return listOf(
            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825", true, true),
            PaymentEntity("신한카드 Deep Dream O…", "본인 9940"),
            PaymentEntity("신한카드 Deep Once Plus", "본인 9940"),
            PaymentEntity("신한은행 신한 주거래 우대통장", "신한은행 2715", false),
            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825"),
            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825"),
//            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825"),
//            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825"),
//            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
//            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
//            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
//            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
        )
    }

    fun updatePaymentListSelectedState(itemPosition: Int) {
        paymentList.value.forEachIndexed { paymentListIndex, paymentEntity ->
            paymentEntity.isSelected = paymentListIndex == itemPosition
        }
    }
}