package com.example.navgraph.di

import com.example.navgraph.data.RewardRepository

object Injection {
    fun provideRepository():RewardRepository{
        return RewardRepository.getInstance()
    }
}