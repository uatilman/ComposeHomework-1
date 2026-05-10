package ru.otus.common.data.promo

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
class PromoRepositoryImpl @Inject constructor(
    private val promoLocalDataSource: PromoLocalDataSource,
    private val promoRemoteDataSource: PromoRemoteDataSource,
    private val promoDataMapper: PromoDataMapper,
    private val dispatcher: CoroutineDispatcher,
) : PromoRepository {

    private val scope = CoroutineScope(
        SupervisorJob() + dispatcher + CoroutineExceptionHandler { _, t ->
            Log.e("PromoRepository", "Error in background sync", t)
        }
    )

    override fun consumePromos(): Flow<List<PromoEntity>> {
        scope.launch(dispatcher) {
            try {
                val promos = promoRemoteDataSource.getPromos()
                promoLocalDataSource.savePromos(
                    promos.map(promoDataMapper::toEntity)
                )
            } catch (e: Exception) {
                Log.e("PromoRepository", "Failed to sync promos", e)
            }
        }
        return promoLocalDataSource.consumePromos()
    }
}
