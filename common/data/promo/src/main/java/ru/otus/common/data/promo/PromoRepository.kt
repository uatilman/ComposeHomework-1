package ru.otus.common.data.promo

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с промо-акциями.
 */
interface PromoRepository {
    /**
     * Предоставляет поток списка промо-акций.
     */
    fun consumePromos(): Flow<List<PromoEntity>>
}
