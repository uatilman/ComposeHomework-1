package ru.otus.marketsample.promo.feature

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

import ru.otus.marketsample.ui.UiText

@Immutable
data class PromoScreenState(
    val isLoading: Boolean = false,
    val promoListState: ImmutableList<PromoState> = persistentListOf(),
    val hasError: Boolean = false,
    val errorMessage: UiText? = null,
)

@Immutable
data class PromoState(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
)