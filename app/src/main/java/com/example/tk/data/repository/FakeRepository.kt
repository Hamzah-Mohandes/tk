// Deklariert das Paket, zu dem diese Datei gehört.
package com.example.tk.data.repository

import com.example.tk.domain.model.Message

object FakeRepository{
    fun getMessegae(): List<Message>{
        return listOf(
            Message(
                id = 1,
                title = "Ihre Versichertenkarte ist unterwegs",
                content = "Ihre neue elektronische Gesundheitskarte wurde versendet und sollte in Kürze bei Ihnen eintreffen.",
                date = "17.08.2025"
            ),
            Message(
                id = 2,
                title = "Erinnerung: Vorsorgeuntersuchung",
                content = "Vergessen Sie nicht Ihren Termin für die Gesundheitsuntersuchung am 25.08.2025.",
                date = "15.08.2025"
            ),
            Message(
                id = 3,
                title = "Neue Leistungen im Überblick",
                content = "Entdecken Sie die neuen Leistungen Ihrer TK für das Jahr 2025, inkl. erweiterter Vorsorgeuntersuchungen.",
                date = "10.08.2025"
            ),
            Message(
                id = 4,
                title = "Ihre Erstattung wurde überwiesen",
                content = "Ihre Erstattung in Höhe von 85,50 € wurde auf Ihr Konto überwiesen.",
                date = "05.08.2025"
            ),
            Message(
                id = 5,
                title = "TK-Update: Neue App-Funktionen",
                content = "Die TK-App wurde aktualisiert. Jetzt mit digitalem Impfpass und Medikamentenplan.",
                date = "01.08.2025"
            ),
            Message(
                id = 6,
                title = "Willkommen bei der TK!",
                content = "Herzlich willkommen als neues Mitglied der Techniker Krankenkasse. Hier finden Sie alle wichtigen Informationen zu Ihrem Start.",
                date = "20.07.2025"
            )
        )
    }
}