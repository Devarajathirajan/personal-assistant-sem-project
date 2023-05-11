package com.example.personalassistant.presentation.wallet

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.personalassistant.domain.wallet.Transaction
import com.example.personalassistant.domain.wallet.TransactionType
import com.example.personalassistant.presentation.util.AddButton
import com.example.personalassistant.ui.theme.*

@Composable
@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun WalletScreen(onAddButtonClick: ()->Unit = {}, viewModel: WalletViewModel = hiltViewModel()) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getTransactionHistory()
        viewModel.getBalance()
    }

    Scaffold(
        topBar = {},
        floatingActionButton = { AddButton(
            onClick = onAddButtonClick
        ) }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colors.background)
        ) {
            item {
                Text(
                    text = "Wallet", fontSize = 32.sp, modifier = Modifier.padding(vertical = 32.dp),
                    fontWeight = FontWeight.Bold
                )

                Row() {
                    BalanceSheetCard(
                        "Income",
                        state.income,
                        cardColor2,
                        Modifier.fillMaxWidth(0.5f)
                    )
                    BalanceSheetCard(
                        "Expense",
                        state.expense,
                        cardColor0,
                        Modifier.fillMaxWidth()
                    )
                }
                Text(
                    text = "History", fontSize = 32.sp, modifier = Modifier.padding(vertical = 32.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            if (state.transactions.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Empty transactions",
                            color = Color(0xFF52638B)
                        )
                    }
                }
            } else {
                items(state.transactions) { transaction ->
                    TransactionHistory(transaction = transaction)
                }
            }
        }
    }
}

@Composable
fun BalanceSheetCard(title: String, value: String, cardColor: Color, modifier: Modifier = Modifier) {
    Card(
        backgroundColor = cardColor,
        modifier = Modifier.padding(4.dp),
        shape = Shapes.large
    ) {
        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            Text(
                text = title, fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row {
                Text(text = "INR ", fontSize = 24.sp)
                Text(
                    text = value, fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionHistory(transaction: Transaction) {
    val bgColor = when(transaction.type) {
        TransactionType.INCOME -> cardColor2
        TransactionType.EXPENSE -> cardColor0
    }
    val chipColor = when(transaction.type) {
        TransactionType.INCOME -> chipColor1
        TransactionType.EXPENSE -> chipColor0
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = Shapes.large,
        backgroundColor = bgColor
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = transaction.title, fontSize = 20.sp, modifier = Modifier
                        .padding(4.dp)
                        .weight(1f, false)
                )
                Row(modifier = Modifier.weight(1f, false)) {
                    Chip(
                        colors = ChipDefaults.chipColors(backgroundColor= chipColor), onClick = {},
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 0.dp)
                    ) {
                        Text(text = transaction.category, fontSize = 15.sp)
                    }
                    Text(
                        text = "INR ${transaction.amount}", fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 4.dp)
                    )
                }
            }
            Row(verticalAlignment = Alignment.Top) {
                Text(text = transaction.date, fontSize = 12.sp, modifier = Modifier.padding(4.dp))
                Text(text = transaction.time, fontSize = 12.sp, modifier = Modifier.padding(4.dp))
            }
        }
    }
}