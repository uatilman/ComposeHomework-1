package ru.otus.marketsample.products.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import android.widget.Toast
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.collections.immutable.persistentListOf
import ru.otus.marketsample.R

/**
 * Главный экран списка продуктов
 *
 * @param viewModel ViewModel для управления состоянием экрана
 * @param onProductClick Обработчик нажатия на продукт
 * @param modifier Модификатор для настройки макета экрана
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = hiltViewModel(),
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

    ProductListScreenContent(
        state = state,
        onRefresh = { viewModel.refresh() },
        onProductClick = onProductClick,
        modifier = modifier
    )
}

/**
 * Вспомогательный компонент для отображения контента списка продуктов
 *
 * @param state Состояние экрана
 * @param onRefresh Обработчик обновления списка
 * @param onProductClick Обработчик нажатия на продукт
 * @param modifier Модификатор
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreenContent(
    state: ProductsScreenState,
    onRefresh: () -> Unit,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize()
    ) {
        if (state.productListState.isEmpty() && state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.productListState, key = { it.id }) { product ->
                    ProductItem(
                        product = product,
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}

/**
 * Компонент элемента списка продуктов
 *
 * @param product Состояние продукта для отображения
 * @param onClick Обработчик нажатия на элемент
 */
@Composable
fun ProductItem(
    product: ProductState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Изображение и промо-метка
        Box(
            modifier = Modifier
                .weight(1f)
                .height(130.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            if (product.hasDiscount) {
                Text(
                    text = product.discount,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color(0xFFFF0000), RoundedCornerShape(4.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Информация о продукте
        Column(
            modifier = Modifier
                .weight(1f)
                .height(130.dp)
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.price_with_arg, product.price),
                modifier = Modifier
                    .align(Alignment.End)
                    .background(Color(0xFFF3E5F5), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                color = Color(0xFF6200EE),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductListScreenPreview() {
    val state = ProductsScreenState(
        productListState = persistentListOf(
            ProductState(
                id = "1",
                name = "Смартфон Samsung Galaxy S23",
                image = "",
                price = "75 000",
                hasDiscount = true,
                discount = "-10%"
            ),
            ProductState(
                id = "2",
                name = "Наушники Sony WH-1000XM5",
                image = "",
                price = "35 000",
                hasDiscount = false,
                discount = ""
            )
        )
    )
    MaterialTheme {
        ProductListScreenContent(
            state = state,
            onRefresh = {},
            onProductClick = {}
        )
    }
}
