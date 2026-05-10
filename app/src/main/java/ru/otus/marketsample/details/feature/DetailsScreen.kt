package ru.otus.marketsample.details.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import android.widget.Toast
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import ru.otus.marketsample.R

/**
 * Экран деталей продукта
 *
 * @param viewModel ViewModel для управления состоянием экрана
 * @param modifier Модификатор для настройки макета экрана
 */
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
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

    DetailsScreenContent(
        state = state,
        modifier = modifier
    )
}

/**
 * Вспомогательный компонент для отображения контента деталей продукта
 *
 * @param state Состояние экрана
 * @param modifier Модификатор
 */
@Composable
fun DetailsScreenContent(
    state: DetailsScreenState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val details = state.detailsState
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = details.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = details.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    fontSize = 24.sp
                )

                if (details.hasDiscount) {
                    Text(
                        text = details.discount,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 16.dp)
                            .background(Color(0xFFFF0000), RoundedCornerShape(4.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }

                Text(
                    text = stringResource(R.string.price_with_arg, details.price),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp),
                    color = Color(0xFF6200EE),
                    fontSize = 18.sp
                )

                Text(
                    text = details.description,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    fontSize = 16.sp
                )

                Button(
                    onClick = { /* TODO: Add to cart */ },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = stringResource(R.string.action_add_to_cart), fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenPreview() {
    val state = DetailsScreenState(
        detailsState = DetailsState(
            id = "1",
            name = "Смартфон Samsung Galaxy S23",
            description = "Флагманский смартфон с отличной камерой и мощным процессором.",
            image = "",
            price = "75 000",
            hasDiscount = true,
            discount = "-10%"
        )
    )
    MaterialTheme {
        DetailsScreenContent(state = state)
    }
}
