package com.example.navgraph.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navgraph.R
import com.example.navgraph.RewardApp
import com.example.navgraph.ui.theme.NavGraphTheme
import com.example.navgraph.ui.theme.Shapes

@Composable
fun CartItem(
    rewardId : Long,
    image:Int,
    title : String,
    totalPoint : Int,
    count : Int,
    onProductionCountChange : (id : Long, count : Int) -> Unit,
    modifier : Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth()
    ){
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ){
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    R.string.required_point,
                    totalPoint
                ),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
        }
        // ProductCounter


    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun CartItemPreview(){
    NavGraphTheme {
        CartItem(
            4,R.drawable.reward_4,"Jaket Hoodie Dicoding",4000,0,
            onProductionCountChange ={rewardId, count ->}
        )
    }
}