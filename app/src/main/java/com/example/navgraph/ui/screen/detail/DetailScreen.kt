package com.example.navgraph.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navgraph.di.Injection
import com.example.navgraph.ui.ViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navgraph.R
import com.example.navgraph.ui.common.UiState
import com.example.navgraph.ui.components.OrderButton
import com.example.navgraph.ui.components.ProductCounter
import com.example.navgraph.ui.theme.NavGraphTheme


@Composable
fun DetailScreen(
    rewardId : Long,
    viewModel : DetailViewModel = viewModel(
        factory =ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack : () -> Unit,
    navigateToCart : () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState->
        when(uiState){
            is UiState.Loading->{
                viewModel.getRewardById(rewardId)
            }
            is UiState.Success->{
                val data = uiState.data
                DetailContent(
                    image = data.reward.image,
                    title = data.reward.title,
                    basePoint = data.reward.requiredPoint,
                    count = data.count,
                    onBackClick = navigateBack,
                    onAddToCart ={ count ->
                        viewModel.addToCart(data.reward,count)
                        navigateToCart()
                    }
                )
            }
            is UiState.Error->{}
        }
    }
}


@Composable
fun DetailContent(
    @DrawableRes image : Int,
    title : String,
    basePoint :Int,
    count : Int,
    onBackClick : () -> Unit,
    onAddToCart : (count : Int) -> Unit,
    modifier : Modifier = Modifier
){
    var totalPoint by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column (
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ){
            Box(){
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.padding(16.dp).clickable{ onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ){
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = stringResource(R.string.required_point, basePoint),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth().height(4.dp).background(LightGray))
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ProductCounter(
                    1,
                    orderCount,
                    onProductIncreased = { orderCount++ },
                    onProductDecreased = { if (orderCount > 0) orderCount-- },
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
                )
                totalPoint = basePoint * orderCount
                OrderButton(
                    text = stringResource(R.string.add_to_cart, totalPoint),
                    enabled = orderCount > 0,
                    onClick = {
                        onAddToCart(orderCount)
                    }
                )
            }
        }
    }

}


@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun DetailContentPreview(){
    NavGraphTheme {
        DetailContent(
            image = R.drawable.reward_4,
            title = "Jaket Hoodie Dicoding",
            basePoint = 7000,
            count = 1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}