package com.example.personalassistant.presentation.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalassistant.domain.wallet.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(private val repository: WalletRepository): ViewModel() {

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState = _uiState.asStateFlow()

    private var getTransactionsJob: Job? = null
    private var getBalanceJob: Job? = null
    init {
        getTransactionHistory()
        getBalance()
    }

    private fun getTransactionHistory() {
        getTransactionsJob?.cancel()
        getTransactionsJob = viewModelScope.launch {
            repository.getTransactions().collectLatest {transaction ->
                _uiState.value = _uiState.value.copy(
                    transactions = transaction
                )
            }
        }
    }

    private fun getBalance() {
        getBalanceJob?.cancel()
        getBalanceJob = viewModelScope.launch {
            repository.getBalanceCheck().collectLatest {newBalance ->
                _uiState.value = _uiState.value.copy(
                    income = "${newBalance.income}",
                    expense = "${newBalance.expense}"
                )
            }
        }
    }
}