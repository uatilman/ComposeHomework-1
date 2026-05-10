package ru.otus.marketsample.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.otus.marketsample.R

/**
 * Класс, описывающий экраны приложения для навигации
 *
 * @property route Маршрут для NavHost
 * @property resourceId ID ресурса строки для заголовка
 * @property icon Иконка экрана
 */
@Immutable
sealed class Screen(val route: String, val resourceId: Int, val icon: ImageVector) {
    /** Экран списка продуктов */
    object Products : Screen("products", R.string.title_products, Icons.AutoMirrored.Filled.List)

    /** Экран промо-акций */
    object Promo : Screen("promo", R.string.title_promo, Icons.Default.Star)

    /** Экран деталей продукта */
    object Details :
        Screen("details/{productId}", R.string.title_details, Icons.AutoMirrored.Filled.List) {
        fun createRoute(productId: String) = "details/$productId"
    }
}
