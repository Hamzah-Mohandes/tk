package com.example.tk.ui.screen

import androidx.compose.ui.graphics.Color

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import coil.compose.rememberImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailScreen(navController: NavController) {
    val context = LocalContext.current
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val tkBlue = Color(0xFF0061A5)
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(GetContent()) { uri ->
        photoUri = uri
    }

    fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:service@tk.de")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
            if (photoUri != null) {
                putExtra(Intent.EXTRA_STREAM, photoUri)
            }
        }
        context.startActivity(Intent.createChooser(emailIntent, "E-Mail senden mit"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("E-Mail an TK") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = tkBlue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Betreff") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tkBlue,
                    focusedLabelColor = tkBlue,
                    cursorColor = tkBlue
                )
            )

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Ihre Nachricht") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tkBlue,
                    focusedLabelColor = tkBlue,
                    cursorColor = tkBlue
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            if (photoUri != null) {
                Image(
                    painter = rememberImagePainter(photoUri),
                    contentDescription = "Photo",
                    modifier = Modifier.size(200.dp)
                )
            } else {
                Button(
                    onClick = { pickImageLauncher.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tkBlue,
                        disabledContainerColor = tkBlue.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        "Foto auswählen",
                        color = Color.White
                    )
                }
            }

            Button(
                onClick = { sendEmail() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = subject.isNotBlank() && message.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = tkBlue,
                    disabledContainerColor = tkBlue.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    "E-Mail senden",
                    color = Color.White
                )
            }
        }
    }
}