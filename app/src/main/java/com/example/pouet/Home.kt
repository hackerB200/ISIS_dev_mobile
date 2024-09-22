package com.example.pouet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.*

@Composable
fun HomeScreen(windowClass: WindowSizeClass, viewModel: MainViewModel) {
    when (windowClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            Column(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ImageProfile()
                Description()
                Spacer(modifier = Modifier.height(20.dp))
                Contact()
                Spacer(modifier = Modifier.height(20.dp))
                MainButton(viewModel)
            }
        }

        else -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ImageProfile()
                    Description()
                }
                Spacer(modifier = Modifier.width(100.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Contact()
                    Spacer(modifier = Modifier.height(20.dp))
                    MainButton(viewModel)
                }
            }
        }

    }
}

@Composable
fun ImageProfile() {
    Image(
        painterResource(id = R.drawable.darkpouet),
        contentDescription = "Profil picture",
        modifier = Modifier
            .clip(CircleShape)
            .size(200.dp)
    )
}

@Composable
fun Description() {
    Text(
        text = stringResource(R.string.name_profile),
        style = MaterialTheme.typography.headlineMedium,
        color = Color.White
    )
    Text(
        text = stringResource(R.string.status_profile),
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White
    )
}

@Composable
fun Contact() {
    Column {
        Row {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.email_profile),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        Row {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.account_profile),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun MainButton(viewModel: MainViewModel) {
    Button(
        onClick = { viewModel.navigateTo(0) },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.teal_700)
        )
    ) {
        Text(text = stringResource(R.string.button_profile))
    }
}