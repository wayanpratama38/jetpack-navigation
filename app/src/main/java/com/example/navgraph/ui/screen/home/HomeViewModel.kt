package com.example.navgraph.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navgraph.data.RewardRepository
import com.example.navgraph.model.OrderReward
import com.example.navgraph.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RewardRepository): ViewModel() {

    // Inisialisasi state Ui
    private val _uiState : MutableStateFlow<UiState<List<OrderReward>>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<List<OrderReward>>> get() = _uiState


    // Mendapatkan Semua Reward Yang ada di dalam Repository
    fun getAllRewards(){
        viewModelScope.launch {
            // Memanggil fungsi mendapatkan semua rewards dari Repository
            repository.getAllRewards()
                // Jika gagal akan mengirimkan pesan
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                // Jika berhasil diambil maka akan mengirimkan orderRewards
                .collect{ orderRewards->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }

}