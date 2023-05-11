package com.example.personalassistant.presentation.wallet.add_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalassistant.domain.wallet.Transaction
import com.example.personalassistant.domain.wallet.TransactionType
import com.example.personalassistant.domain.wallet.WalletRepository
import com.example.personalassistant.util.removeSpecialCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(private val repository: WalletRepository): ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val evenFlow = _eventFlow.asSharedFlow()

    fun updateTitle(title: String) {
        val isError = title.isEmpty()
        _uiState.value = _uiState.value.copy(
            title = title,
            titleEmptyError = isError
        )
    }

    fun updateType(type: String) {
        _uiState.value = _uiState.value.copy(
            type = type
        )
    }

    fun updateDate(date: String) {
        val isError = date.isEmpty()
        _uiState.value = _uiState.value.copy(
            date = date,
            dateEmptyError = isError
        )
    }

    fun updateTime(time: String) {
        val isError = time.isEmpty()
        _uiState.value = _uiState.value.copy(
            time = time,
            timeEmptyError = isError
        )
    }

    fun updateCategory(category: String) {
        val isError = category.isEmpty()
        _uiState.value = _uiState.value.copy(
            category = category,
            categoryEmptyError = isError
        )
    }

    fun updateAmount(amount: String) {
        //Need to update
        val isError = amount.isEmpty()
        _uiState.value = _uiState.value.copy(
            amount = amount,
            amountInvalidError = isError
        )
    }

    fun addTransaction() {
        viewModelScope.launch {
            uiState.value.apply {
                if (
                    titleEmptyError || dateEmptyError || timeEmptyError ||
                    categoryEmptyError || amountInvalidError
                ) {
                    return@launch
                }
            }
            repository.addTransaction(
                Transaction(
                    type =  if (uiState.value.type == "Income")
                        TransactionType.INCOME else TransactionType.EXPENSE,
                    title = uiState.value.title,
                    amount = removeSpecialCharacters(uiState.value.amount),
                    date = uiState.value.date,
                    time = uiState.value.time,
                    category = uiState.value.category
                )
            )
            _eventFlow.emit(UiEvent.SaveNote)
        }
    }
    sealed class UiEvent {
        object SaveNote: UiEvent()
    }
}