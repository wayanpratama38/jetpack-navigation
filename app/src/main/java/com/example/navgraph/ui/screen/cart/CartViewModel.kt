package com.example.navgraph.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navgraph.data.RewardRepository
import com.example.navgraph.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: RewardRepository) : ViewModel() {
    private val _uiState : MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<CartState>> get() = _uiState


    fun getAddedOrderRewards(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderRewards()
                .collect{ orderReward ->
                    val totalRequirePoint =
                        orderReward.sumOf{ it.reward.requiredPoint * it.count}
                    _uiState.value = UiState.Success(CartState(orderReward,totalRequirePoint))
                }
        }
    }

    fun updateOrderReward(rewardId: Long, count : Int){
        viewModelScope.launch {
            repository.updateOrderReward(rewardId,count)
                .collect{isUpdated->
                    if(isUpdated){
                        getAddedOrderRewards()
                    }
                }
        }
    }
}