package com.istea.simplewallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.istea.simplewallet.ui.theme.SimpleWalletTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.istea.simplewallet.model.Operation
import com.istea.simplewallet.model.OperationType
import com.istea.simplewallet.model.Wallet
import com.istea.simplewallet.page.BalancePage
import com.istea.simplewallet.page.WithdrawPage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleWalletTheme {
                val navController = rememberNavController()
                val wallets = remember { mutableStateListOf(
                    Wallet(1, "Tito García", 1000f))
                }
                val operations = remember { mutableStateListOf<Operation>() }
                var operationCounter = remember { 0 }

                fun processWithdrawal(walletId: Int, amount: Float) {
                    val wallet = wallets.find { it.id == walletId }
                    if (wallet != null && amount > 0 && amount <= wallet.balance) {
                        wallet.balance -= amount
                        operationCounter++
                        val op = Operation(
                            operationCounter,
                            walletId,
                            OperationType.WITHDRAWAL,
                            amount
                        )
                        operations.add(op)
                        navController.navigate("withdraw/${op.id}")
                    }
                }

                NavHost(navController, startDestination = "balance") {
                    composable("balance") {
                        BalancePage(wallets, operations, ::processWithdrawal, navController)
                    }
                    composable("withdraw/{operationId}") { backstackEntry ->
                        val operationId = backstackEntry.arguments?.getString("operationId")?.toInt() ?: 0
                        WithdrawPage(operationId, wallets, operations, navController)
                    }
                }
            }
        }
    }
}
