package ru.otus.marketsample.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.otus.marketsample.ui.theme.MarketSampleTheme

/**
 * Вспомогательный компонент для отображения структуры главного экрана
 *
 * @param navController Контроллер навигации
 * @param items Список элементов нижнего меню
 * @param content Содержимое экрана
 */
@Composable
fun MainScreenContent(
    navController: NavController,
    items: ImmutableList<Screen>,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = { MainBottomBar(navController, items, modifier = Modifier) }
    ) { innerPadding ->
        content(innerPadding)
    }
}

/**
 * Нижняя панель навигации приложения
 *
 * @param navController Контроллер навигации
 * @param items Список экранов для отображения в BottomBar
 */
@Composable
fun MainBottomBar(
    navController: NavController,
    items: ImmutableList<Screen>,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Показываем BottomBar только на главных экранах
    if (items.any { it.route == currentDestination?.route }) {
        NavigationBar(modifier = modifier) {
            items.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomBarPreview() {
    MarketSampleTheme {
        val items = persistentListOf(Screen.Products, Screen.Promo)
        
        // Для визуализации в превью отрисовываем NavigationBar напрямую,
        // так как NavController в превью не имеет текущего маршрута по умолчанию.
        NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = screen == Screen.Products,
                    onClick = {}
                )
            }
        }
    }
}
