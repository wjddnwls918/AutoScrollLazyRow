package com.example.autoscrolllazyrow.common

data class PaymentEntity(
    val crdPdNm: String,
    val paymentDesc: String,
    val isCard: Boolean = true,
    val isSelected: Boolean = false
)