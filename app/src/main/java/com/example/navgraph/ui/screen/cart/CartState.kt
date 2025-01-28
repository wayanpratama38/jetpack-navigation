package com.example.navgraph.ui.screen.cart

import com.example.navgraph.model.OrderReward

data class CartState(
    val orderReward: List<OrderReward>,
    val totalRequiredPoint : Int
)