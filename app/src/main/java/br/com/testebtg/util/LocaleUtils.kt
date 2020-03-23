package br.com.testebtg.util

import android.content.Context
import br.com.testebtg.R
import java.util.*

fun returnLocale(context: Context?): Locale {
    return Locale(
        context?.getText(R.string.test_btg_language) as String,
        context?.getText(R.string.test_btg_country) as String
    )
}

fun returnLocaleString(context: Context): String {
    return "${context?.getText(R.string.test_btg_language)}-${context?.getText(R.string.test_btg_country)}"
}