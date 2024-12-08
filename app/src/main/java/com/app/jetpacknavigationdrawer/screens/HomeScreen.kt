package com.app.jetpacknavigationdrawer.screens

import UserViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.jetpacknavigationdrawer.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.app.jetpacknavigationdrawer.data.model.User

@Composable
@Preview
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        UserListScreen()
    }
}

@Composable
fun UserListScreen(userViewModel: UserViewModel = viewModel()) {
    val users = userViewModel.users.collectAsState()

    Column (modifier = Modifier
        .fillMaxWidth()) {
        users.value.forEach { user ->
            UserRow(user)
        }
    }
}

@Composable
fun UserRow(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)  // Padding around the entire card (row
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)  // Padding inside the row (around the content)
        ) {
            Image(
                painter = rememberAsyncImagePainter(user.imageUrl),
                contentDescription = "${user.name}'s profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
                Text(text = user.name)
                Text(text = user.status)
            }
        }
    }
}
