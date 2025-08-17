package com.example.tk.viewmodel
import androidx.lifecycle.ViewModel
import com.example.tk.data.repository.FakeRepository
import com.example.tk.domain.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class ReportViewModel : ViewModel() {
    // Enthält die ursprüngliche, ungefilterte Liste der Nachrichten
    private val _allMessages = FakeRepository.getMessegae()

    // StateFlow für die aktuell angezeigten (gefilterten) Nachrichten
    private val _messages = MutableStateFlow(_allMessages)
    val messages: StateFlow<List<Message>> = _messages

    // Fügen Sie diese Zeilen wieder hinzu:
    private val _reportText = MutableStateFlow("")
    val reportText: StateFlow<String> = _reportText

    fun updateReport(text: String) {
        _reportText.value = text
    }

    // StateFlow für den Suchtext
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    // Aktualisiert den Suchtext und filtert die Nachrichten
    fun onSearchTextChanged(text: String) {
        _searchText.value = text
        filterMessages(text)
    }

    // Filtert die Nachrichten basierend auf dem Suchtext
    private fun filterMessages(query: String) {
        if (query.isBlank()) {
            _messages.value = _allMessages
        } else {
            val filtered = _allMessages.filter { message ->
                message.title.contains(query, ignoreCase = true) ||
                        message.content.contains(query, ignoreCase = true)
            }
            _messages.value = filtered
        }
    }

    // Setzt den Suchtext zurück und zeigt alle Nachrichten an
    fun clearSearch() {
        _searchText.value = ""
        _messages.value = _allMessages
    }
}