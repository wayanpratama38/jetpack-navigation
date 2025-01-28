package com.example.navgraph.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.navgraph.R
import com.example.navgraph.ui.theme.NavGraphTheme


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(R.string.menu_profile)
        )
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun ProfileScreenPreview(){
    NavGraphTheme {
        ProfileScreen()
    }
}