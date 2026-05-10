package ru.otus.marketsample.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.collections.immutable.persistentListOf
import ru.otus.marketsample.R
import ru.otus.marketsample.details.feature.DetailsScreen
import ru.otus.marketsample.products.feature.ProductListScreen
import ru.otus.marketsample.promo.feature.PromoListScreen
import ru.otus.marketsample.ui.theme.MarketSampleTheme

/**
 * Главный экран приложения с навигацией и BottomBar.
 * Инъекция ViewModel происходит автоматически через Hilt.
 */
@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val items = persistentListOf(Screen.Products, Screen.Promo)

    MainScreenContent(
        navController = navController,
        items = items,
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Products.route
        ) {
            composable(Screen.Products.route) {
                ProductListScreen(
                    onProductClick = { productId ->
                        navController.navigate(Screen.Details.createRoute(productId))
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(Screen.Promo.route) {
                PromoListScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(
                Screen.Details.route,
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) {
                DetailsScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    val navController = rememberNavController()
    MarketSampleTheme {
        MainScreenContent(
            navController = navController,
            items = persistentListOf(Screen.Products, Screen.Promo)
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Products.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Products.route) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(R.string.preview_products_screen))
                    }
                }
            }
        }
    }
}
