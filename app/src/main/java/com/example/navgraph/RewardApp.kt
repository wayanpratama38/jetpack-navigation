package com.example.navgraph

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navgraph.ui.navigation.NavigationItem
import com.example.navgraph.ui.navigation.Screen
import com.example.navgraph.ui.screen.cart.CartScreen
import com.example.navgraph.ui.screen.detail.DetailScreen
import com.example.navgraph.ui.screen.home.HomeScreen
import com.example.navgraph.ui.screen.profile.ProfileScreen
import com.example.navgraph.ui.theme.NavGraphTheme

// Composable untuk bottom bar
// Mulai dari membuat list icon
// Hingga mengatur state destinasi pertama
@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


@Composable
fun RewardApp(
    modifier: Modifier = Modifier,
    navController : NavHostController = rememberNavController()
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        // Conditional menghilangkan bottom bar di dalam detail reward
        bottomBar = {
            if(currentRoute!=Screen.DetailReward.route){
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        // Tempat dimana akan disimpan Navigasinya
        NavHost(
            // Inisialisasi navigasi controller
            // Destinasi awal
            // Modifier yang menggunakan innerPadding karena
            // Menggunakan Scaffold
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            // Dimulai dengan membuat composable
            // Dari setiap Screen route yang sudah
            // Ditentukan
            composable(Screen.Home.route){
                HomeScreen(navigateToDetail = { rewardId->
                    navController.navigate(Screen.DetailReward.createRoute(rewardId))
                })
            }
            composable(Screen.Cart.route){
                val context = LocalContext.current
                CartScreen(
                    onOrderButtonClicked = { message ->
                        shareOrder(context,message)
                    }
                )
            }
            composable(Screen.Profile.route){
                ProfileScreen()
            }
            // Ini jika terdapat route lagi didalam route
            // Misal jika ingin ke detail dari sebuah reward
            composable(
                route = Screen.DetailReward.route,
                arguments = listOf(navArgument("rewardId"){ type = NavType.LongType })
            ){
                val id = it.arguments?.getLong("rewardId")?:-1L
                DetailScreen(
                    rewardId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    // Navigate dari Detail Reward ke Cart Screen
                    navigateToCart = {
                        // Menghilangkan Screen yang sudah pernah ditekan
                        // Sehingga langsung ke start destination
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}


// Fungsi untuk mengintent pesan
private fun shareOrder(
    context : Context,
    summary : String
){
    // Membuat intent yang dapat mengirim
    val intent = Intent(Intent.ACTION_SEND).apply{
        // Jenis file MIME intent
        type = "text/plain"
        // EXTRA SUBJECT untuk judul
        // EXTRA TEXT untuk isi dari pesan
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.dicoding_reward))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    // Memulai intent
    context.startActivity(
        // Membuat pilihan bagi pengguna
        // Untuk share ke mana saja
        Intent.createChooser(
            intent,
            context.getString(R.string.dicoding_reward)
        )
    )
}



// Preview
@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun RewardAppPreview(){
    NavGraphTheme {
        RewardApp()
    }
}
