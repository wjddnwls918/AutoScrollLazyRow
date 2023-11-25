package com.example.autoscrolllazyrow.common

data class PaymentEntity(
    var crdPdNm: String,
    var paymentDesc: String,
    val isCard: Boolean = true,
    var isSelected: Boolean = false
)