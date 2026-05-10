package ru.otus.marketsample.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Тема приложения MarketSample
 *
 * @param modifier Модификатор
 * @param content Содержимое, к которому будет применена тема
 */
@Composable
fun MarketSampleTheme(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
