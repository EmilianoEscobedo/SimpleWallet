package com.istea.simplewallet.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.istea.simplewallet.model.Operation
import com.istea.simplewallet.model.Wallet
import androidx.navigation.NavController
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalancePage(
    wallets: List<Wallet>,
    operations: List<Operation>,
    onWithdraw: (walletId: Int, amount: Float) -> Unit,
    navController: NavController
) {
    val wallet = wallets.firstOrNull()
    var amount by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = { BalanceTopBar() },
        containerColor = Color(0xFFF3F3F3),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BalanceHeader(wallet)
                BalanceCard(wallet)
                AmountInput(amount) { amount = it }
                WithdrawButton(amount, wallet, onWithdraw) { error = it }
                if (error) {
                    Text(
                        "Invalid amount",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceTopBar() {
    TopAppBar(
        title = { Text("\uD83D\uDCB8 Wallet Balance") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF6200EE),
            titleContentColor = Color.White
        )
    )
}

@Composable
fun BalanceHeader(wallet: Wallet?) {
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Hello, ${wallet?.userName ?: ""}",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Composable
fun BalanceCard(wallet: Wallet?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBB86FC))
    ) {
        Column(modifier = Modifier.padding(36.dp)) {
            Text("Current Balance",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                "$${String.format(Locale.US, "%.2f", wallet?.balance ?: 0f)}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountInput(value: String,
                onValueChange: (String) -> Unit) {
    Spacer(modifier = Modifier.height(14.dp))
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            if (input.all { it.isDigit() || it == '.' }) onValueChange(input)
        },
        label = { Text("Withdraw Amount") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        leadingIcon = { Text("$", fontSize = 18.sp) }
    )
}

@Composable
fun WithdrawButton(amount: String,
                   wallet: Wallet?,
                   onWithdraw: (Int, Float) -> Unit,
                   onError: (Boolean) -> Unit) {
    Button(
        onClick = {
            val amt = amount.toFloatOrNull() ?: 0f
            val walletId = wallet?.id ?: 0
            if (wallet != null && amt > 0 && amt <= wallet.balance) {
                onWithdraw(walletId, amt)
                onError(false)
            } else {
                onError(true)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF15655D))
    ) {
        Text("Withdraw", color = Color.White, fontSize = 18.sp)
    }
}