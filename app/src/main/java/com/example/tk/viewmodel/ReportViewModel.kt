package com.example.tk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tk.data.repository.FakeRepository
import com.example.tk.domain.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReportViewModel : ViewModel() {

    private val _allMessages = FakeRepository.getMessegae() // ungefilterte Nachrichten
    private val _messegae = MutableStateFlow(FakeRepository.getMessegae())
    val messegae: StateFlow<List<Message>> = _messegae
    private val _reportText = MutableStateFlow("")
    val reportText: StateFlow<String> = _reportText

    fun updateReport(text: String) {
        _reportText.value = text
    }

}
