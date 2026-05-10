package ru.otus.marketsample.details.feature

import androidx.compose.runtime.Immutable

import ru.otus.marketsample.ui.UiText

@Immutable
data class DetailsScreenState(
    val isLoading: Boolean = false,
    val detailsState: DetailsState = DetailsState(),
    val hasError: Boolean = false,
    val errorMessage: UiText? = null,
)

@Immutable
data class DetailsState(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val price: String = "",
    val hasDiscount: Boolean = false,
    val discount: String = "",
)
