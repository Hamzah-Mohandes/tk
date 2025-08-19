package com.example.tk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tk.data.repository.FakeRepository
import com.example.tk.domain.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReportViewModel : ViewModel() {
    // 1. Datenquelle - Hier wird die "Datenbank" (FakeRepository) initial geladen
    private val _allMessages = FakeRepository.getMessegae()

    // 2. StateFlow für die UI - Die beobachtbare Nachrichtenliste für Composables
    private val _messages = MutableStateFlow(_allMessages)
    val messages: StateFlow<List<Message>> = _messages // Öffentliche, schreibgeschützte Version

    // 3. StateFlow für Report-Text - Speichert den Text für Reports
    private val _reportText = MutableStateFlow("")
    val reportText: StateFlow<String> = _reportText

    // 4. Einfache Update-Funktion - Wird von der UI aufgerufen
    fun updateReport(text: String) {
        _reportText.value = text
    }

    // 5. Suchfunktionalität - StateFlow für Suchtext
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    // 6. Wichtige Business-Logik - Wird bei jeder Tasteneingabe aufgerufen
    fun onSearchTextChanged(text: String) {
        _searchText.value = text
        filterMessages(text) // Filtert Nachrichten in Echtzeit
    }

    // 7. Kernfilterlogik - Entscheidet, welche Nachrichten angezeigt werden
    private fun filterMessages(query: String) {
        _messages.value = if (query.isBlank()) {
            _allMessages // Keine Suche -> zeige alle Nachrichten
        } else {
            // Filtere nach Titel oder Inhalt (case-insensitive)
            _allMessages.filter { message ->
                message.title.contains(query, ignoreCase = true) ||
                        message.content.contains(query, ignoreCase = true)
            }
        }
    }

    // 8. Hilfsfunktion - Setzt Suche zurück
    fun clearSearch() {
        _searchText.value = ""
        _messages.value = _allMessages
    }

    // 9. Nachrichten-Detailabruf - Wichtig für Navigation zu Details
    fun getMessageById(id: Int): Message? {
        return _allMessages.find { it.id == id } // Lineare Suche (bei vielen Einträgen optimieren)
    }
}