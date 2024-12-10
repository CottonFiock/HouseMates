package com.app.jetpacknavigationdrawer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.jetpacknavigationdrawer.data.model.GroceryItem
import com.app.jetpacknavigationdrawer.ui.viewmodel.GroceryItemViewModel

@Composable
fun GroceryListScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        GroceryItemListScreen()
    }
}

@Composable
fun GroceryItemListScreen(groceryItemViewModel: GroceryItemViewModel = viewModel()) {
    val groceryItems = groceryItemViewModel.groceryItems.collectAsState()

    Column (modifier = Modifier
        .fillMaxWidth()) {
        groceryItems.value.forEach { groceryItem ->
            GroceryItemRow(groceryItem)
        }
    }
}

@Composable
fun GroceryItemRow(groceryItem : GroceryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)  // Padding inside the row (around the content)
        ) {
            Text(text = groceryItem.name)
        }
    }
}