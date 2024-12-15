package com.app.housemates.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.housemates.data.model.PhoneNumber
import com.app.housemates.ui.viewmodel.PhoneNumberViewModel


@Composable
fun PhoneNumbersScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        PhoneNumbersListScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumbersListScreen(phoneNumberViewModel: PhoneNumberViewModel = viewModel()){
    val numbers = phoneNumberViewModel.numbers.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var phoneNumberText by remember { mutableStateOf(TextFieldValue("")) }
    var nameText by remember { mutableStateOf(TextFieldValue(""))}

    // Show dialog when floating action button is clicked
    if (showDialog) {
        AddPhoneNumberDialog(
            onDismissRequest = { showDialog = false },
            onAddPhoneNumber = {
                if (phoneNumberText.text.isNotBlank() && nameText.text.isNotBlank()) {
                    phoneNumberViewModel.addPhoneNumber(
                        PhoneNumber(name = nameText.text, number = phoneNumberText.text)
                    )
                    showDialog = false
                }
            },
            phoneNumberText = phoneNumberText,
            onPhoneNumberChange = { phoneNumberText = it },
            nameText = nameText,
            onNameChange = { nameText = it }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Phone Number")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            items(numbers.value) { phoneNumber ->
                NumberRow(phoneNumber)
            }
        }
    }
}

@Composable
fun NumberRow(number: PhoneNumber){
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp).weight(9f)) {
                Text(text = number.name)
                Text(text = number.number)
            }
            //Spacer(modifier = Modifier.weight(1f))  // This pushes the phone icon to the right

            // Add a phone icon button to dial the number
            IconButton(onClick = {
                // This will trigger the dialer intent to call the phone number
                dialPhoneNumber(context, number.number)
            }, modifier = Modifier.size(48.dp).padding(start = 8.dp).weight(1f)) {
                Icon(Icons.Filled.Phone, contentDescription = "Call Phone Number")
            }
        }
    }
}

fun dialPhoneNumber(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    context.startActivity(intent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPhoneNumberDialog(
    onDismissRequest: () -> Unit,
    onAddPhoneNumber: () -> Unit,
    phoneNumberText: TextFieldValue,
    onPhoneNumberChange: (TextFieldValue) -> Unit,
    nameText: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Add New Phone Number") },
        text = {
            Column {
                // TextField for Name
                TextField(
                    value = nameText,
                    onValueChange = onNameChange,
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // TextField for Phone Number
                TextField(
                    value = phoneNumberText,
                    onValueChange = onPhoneNumberChange,
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onAddPhoneNumber) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}