package com.example.navgraph.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navgraph.R
import com.example.navgraph.di.Injection
import com.example.navgraph.ui.ViewModelFactory
import com.example.navgraph.ui.common.UiState
import com.example.navgraph.ui.components.CartItem
import com.example.navgraph.ui.components.OrderButton


@Composable
fun CartScreen(
    modifier : Modifier = Modifier,
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> viewModel.getAddedOrderRewards()
            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { rewardId,count ->
                        viewModel.updateOrderReward(rewardId,count)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state : CartState,
    onProductCountChanged : (id : Long,count : Int)->Unit,
    modifier: Modifier = Modifier
){
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderReward.count(),
        state.totalRequiredPoint
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.menu_cart),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(state.orderReward,key = {it.reward.id}){item->
                CartItem(
                    rewardId = item.reward.id,
                    image = item.reward.image,
                    title = item.reward.title,
                    totalPoint = item.reward.requiredPoint * item.count,
                    count = item.count,
                    onProductionCountChange = onProductCountChanged
                )
                Divider()
            }
        }
        OrderButton(
            text = stringResource(R.string.total_order,state.totalRequiredPoint),
            enabled = state.orderReward.isNotEmpty(),
            onClick = {

            },
            modifier = Modifier.padding(16.dp)
        )
    }
}