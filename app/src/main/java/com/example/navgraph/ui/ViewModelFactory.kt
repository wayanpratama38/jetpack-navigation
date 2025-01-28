package com.example.navgraph.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navgraph.data.RewardRepository
import com.example.navgraph.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository : RewardRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel>create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
    }
}