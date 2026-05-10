package ru.otus.marketsample.promo.feature

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.collections.immutable.persistentListOf
import ru.otus.marketsample.R

/**
 * Главный экран списка промо-акций
 *
 * @param viewModel ViewModel для управления состоянием экрана
 * @param modifier Модификатор для настройки макета экрана
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListScreen(
    modifier: Modifier = Modifier,
    viewModel: PromoListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val errorText = state.errorMessage?.asString() ?: stringResource(R.string.error_unknown)

    LaunchedEffect(state.hasError) {
        if (state.hasError) {
            Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
            viewModel.errorHasShown()
        }
    }

    PromoListScreenContent(
        state = state,
        onRefresh = { viewModel.refresh() },
        modifier = modifier
    )
}

/**
 * Вспомогательный компонент для отображения контента списка промо-акций
 *
 * @param state Состояние экрана
 * @param onRefresh Обработчик обновления списка
 * @param modifier Модификатор
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListScreenContent(
    state: PromoScreenState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize()
    ) {
        if (state.promoListState.isEmpty() && state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.promoListState, key = { it.id }) { promo ->
                    PromoItem(promo = promo)
                }
            }
        }
    }
}

/**
 * Компонент элемента списка промо-акций
 *
 * @param promo Состояние промо-акции для отображения
 */
@Composable
fun PromoItem(
    promo: PromoState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(250.dp)
    ) {
        AsyncImage(
            model = promo.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Градиент для текста
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                    )
                )
        )

        // Контент промо-акции
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text = promo.name,
                color = Color.White,
                fontSize = 24.sp
            )
            Text(
                text = promo.description,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PromoListScreenPreview() {
    val state = PromoScreenState(
        promoListState = persistentListOf(
            PromoState(
                id = "1",
                name = "Летняя распродажа",
                description = "Скидки до 50% на все товары",
                image = ""
            ),
            PromoState(
                id = "2",
                name = "Кэшбэк 10%",
                description = "Получите кэшбэк при оплате картой",
                image = ""
            )
        )
    )
    PromoListScreenContent(
        state = state,
        onRefresh = {}
    )
}
