package com.app.housemates.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.housemates.data.model.GroceryItem
import com.app.housemates.ui.viewmodel.GroceryItemViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryListScreen() {
    val groceryItemViewModel: GroceryItemViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    var newItemName by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        GroceryItemListScreen(groceryItemViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newItemName,
            onValueChange = { newItemName = it },
            label = { Text("Add new item") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                if (newItemName.isNotBlank()) {
                    coroutineScope.launch {
                        groceryItemViewModel.addGroceryItem(newItemName)
                        newItemName = ""
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Add Item")
        }
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp)  // Padding inside the row (around the content)
    ) {
        CheckboxMinimalExample(groceryItem.name)
    }
}

@Composable
fun CheckboxMinimalExample(prova : String) {
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
    }

    Text(
        prova
    )
}