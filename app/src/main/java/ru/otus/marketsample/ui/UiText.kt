package ru.otus.marketsample.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Класс-обертка для работы со строками в UI.
 * Позволяет передавать либо готовую строку, либо ID ресурса без привязки к Context во ViewModel.
 */
sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(@param:StringRes val resId: Int, vararg val args: Any) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}
