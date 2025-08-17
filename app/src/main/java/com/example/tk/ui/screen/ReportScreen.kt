package com.example.tk.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tk.data.repository.FakeRepository
import com.example.tk.viewmodel.ReportViewModel


@Composable
fun ReportScreen(viewModel: ReportViewModel = viewModel()) {
    val reportText by viewModel.reportText.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = reportText,
            onValueChange = { viewModel.updateReport(it) },
            label = { Text("Krankmeldung") }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}