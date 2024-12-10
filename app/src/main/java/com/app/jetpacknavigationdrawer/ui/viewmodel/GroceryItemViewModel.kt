package com.app.jetpacknavigationdrawer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.jetpacknavigationdrawer.data.model.GroceryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroceryItemViewModel : ViewModel() {
    private val _groceryItems = MutableStateFlow<List<GroceryItem>>(emptyList())
    val groceryItems : StateFlow<List<GroceryItem>> = _groceryItems

    init {
        loadGroceryItems()
    }

    private fun loadGroceryItems(){
        viewModelScope.launch {
            // Replace with actual data loading logic
            _groceryItems.value = listOf(
                GroceryItem("banane"),
                GroceryItem("mele")
            )
        }
    }
}