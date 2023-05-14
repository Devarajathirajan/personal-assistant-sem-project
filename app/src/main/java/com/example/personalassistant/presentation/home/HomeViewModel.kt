package com.example.personalassistant.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalassistant.domain.task.TaskRepository
import com.example.personalassistant.domain.wallet.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var getBalanceJob: Job? = null
    private var getMileStoneDetailsJob: Job? = null

    init {
        getBalanceCheck()
        getMileStoneDetails()
    }

    private fun getBalanceCheck() {
        getBalanceJob?.cancel()
        getBalanceJob = viewModelScope.launch {
            walletRepository.getBalanceCheck().collectLatest { newBalance ->
                _uiState.value = _uiState.value.copy(
                    income = "${newBalance.income}",
                    expense = "${newBalance.expense}",
                    balance = "${newBalance.balance}"
                )
            }
        }
    }

    private fun getMileStoneDetails() {
        getMileStoneDetailsJob?.cancel()
        getMileStoneDetailsJob = viewModelScope.launch {
            taskRepository.getMileStoneList().collectLatest {
                var todayCount = 0
                var todayCompleted = 0
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateObj = Date()
                val current = formatter.format(dateObj)
                it.forEach { milestone ->
                    milestone.deadLine?.let {deadline ->
                        val date = deadline.substringBefore("T")
                        if (date == current) {
                            todayCount++
                            if (milestone.isCompleted) ++todayCompleted
                        }
                    }
                }
                _uiState.value = uiState.value.copy(
                    today_milestones = todayCount,
                    completed_milestones = todayCompleted
                )
            }
        }
    }

}