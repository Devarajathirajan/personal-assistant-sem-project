package com.example.personalassistant.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalassistant.domain.wallet.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val walletRepository: WalletRepository): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun getBalanceCheck() {
        viewModelScope.launch {
            walletRepository.getBalanceCheck().collectLatest { newBalance ->
                _uiState.value = _uiState.value.copy(
                    income = "${newBalance.income}",
                    expense = "${newBalance.expense}",
                    balance = "${newBalance.balance}"
                )
            }
        }
    }
}