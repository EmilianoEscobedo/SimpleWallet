package com.istea.simplewallet.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.istea.simplewallet.model.Operation
import com.istea.simplewallet.model.Wallet
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawPage(
    operationId: Int,
    wallets: List<Wallet>,
    operations: List<Operation>,
    navController: NavController
) {
    val operation = operations.find { it.id == operationId }
    val wallet = operation?.let { op -> wallets.find { it.id == op.walletId } }

    Scaffold(
        topBar = { WithdrawTopBar() },
        containerColor = Color(0xFFF3F3F3),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) { if (operation != null && wallet != null) {
                    WithdrawCard(operation, wallet)
                    BackButton(navController)
                } else {
                    Text("Operation not found", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawTopBar() {
    TopAppBar(
        title = { Text("\uD83D\uDCC3 Withdrawal Receipt") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF6200EE),
            titleContentColor = Color.White
        )
    )
}

@Composable
fun WithdrawCard(operation: Operation, wallet: Wallet) {
    Spacer(modifier = Modifier.height(12.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBB86FC))
    ) {
        Column(modifier = Modifier.padding(36.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Operation Complete!", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
            Text("User: ${wallet.userName}", color = Color.White)
            Text("Amount: $${String.format(Locale.US, "%.2f", operation.amount)}", color = Color.White)
            Text("Remaining Balance: $${String.format(Locale.US, "%.2f", wallet.balance)}", color = Color.White)
        }
    }
}

@Composable
fun BackButton(navController: NavController) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { navController.navigate("balance") },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF15655D))
    ) {
        Text("Back to Balance", color = Color.White, fontSize = 18.sp)
    }
}
