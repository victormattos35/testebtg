package br.com.testebtg.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(date)
}
