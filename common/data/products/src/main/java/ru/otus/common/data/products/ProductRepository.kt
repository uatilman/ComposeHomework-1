package ru.otus.common.data.products

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с продуктами.
 * Соблюдает принцип инверсии зависимостей (DIP).
 */
interface ProductRepository {
    /**
     * Предоставляет поток списка продуктов.
     * Реализует Offline-first подход.
     */
    fun consumeProducts(): Flow<List<ProductEntity>>
}
