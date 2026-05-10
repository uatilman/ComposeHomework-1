package ru.otus.marketsample.products.feature

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

import ru.otus.marketsample.ui.UiText

@Immutable
data class ProductsScreenState(
    val isLoading: Boolean = false,
    val productListState: ImmutableList<ProductState> = persistentListOf(),
    val hasError: Boolean = false,
    val errorMessage: UiText? = null,
)

@Immutable
data class ProductState(
    val id: String,
    val name: String,
    val image: String,
    val price: String,
    val hasDiscount: Boolean,
    val discount: String,
)
