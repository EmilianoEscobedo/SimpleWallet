package com.istea.simplewallet.model

enum class OperationType { WITHDRAWAL }

data class Operation(
    val id: Int,
    val walletId: Int,
    val operationType: OperationType,
    val amount: Float
)
