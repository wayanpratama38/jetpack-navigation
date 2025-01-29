package com.example.navgraph.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navgraph.R
import com.example.navgraph.data.RewardRepository
import com.example.navgraph.di.Injection
import com.example.navgraph.model.OrderReward
import com.example.navgraph.ui.ViewModelFactory
import com.example.navgraph.ui.common.UiState
import com.example.navgraph.ui.components.RewardItem
import com.example.navgraph.ui.theme.NavGraphTheme


@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    viewModel : HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail : (Long) -> Unit
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let{ uiState ->
        when(uiState){
            is UiState.Loading -> viewModel.getAllRewards()
            is UiState.Success -> HomeContent(uiState.data,modifier,navigateToDetail)
            is UiState.Error -> { }
        }
    }

}


@Composable
fun HomeContent(
    orderReward : List<OrderReward>,
    modifier : Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(orderReward){ data ->
            RewardItem(
                image=data.reward.image,
                title = data.reward.title,
                requiredPoint = data.reward.requiredPoint,
                modifier = modifier.clickable{
                    navigateToDetail(data.reward.id)
                }
            )
        }
    }
}


@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun HomeScreenPreview(){
    NavGraphTheme {
        HomeScreen(navigateToDetail = {})
    }
}