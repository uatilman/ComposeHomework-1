package ru.otus.common.data.products

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productDataMapper: ProductDataMapper,
    private val dispatcher: CoroutineDispatcher,
) : ProductRepository {
    
    private val scope = CoroutineScope(
        SupervisorJob() + dispatcher + CoroutineExceptionHandler { _, t ->
            Log.e("ProductRepository", "Error in background sync", t)
        }
    )

    override fun consumeProducts(): Flow<List<ProductEntity>> {
        scope.launch(dispatcher) { // Используем внедренный диспетчер
            try {
                val products = productRemoteDataSource.getProducts()
                productLocalDataSource.saveProducts(
                    products.map(productDataMapper::toEntity)
                )
            } catch (e: Exception) {
                Log.e("ProductRepository", "Failed to sync products", e)
                // Здесь можно добавить оповещение через канал ошибок или Result
            }
        }
        return productLocalDataSource.consumeProducts()
    }
}
