package com.example.navgraph.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navgraph.ui.theme.NavGraphTheme


@Composable
fun OrderButton(
    text : String,
    modifier : Modifier = Modifier,
    enabled : Boolean = true,
    onClick : () -> Unit
){
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(52.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun OrderButtonPreview(){
    NavGraphTheme {
        OrderButton(
            text = "Order",
            onClick = {}
        )
    }
}