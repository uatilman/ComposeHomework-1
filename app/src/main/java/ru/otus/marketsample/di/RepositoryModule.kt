package ru.otus.marketsample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.otus.common.data.products.ProductRepository
import ru.otus.common.data.products.ProductRepositoryImpl
import ru.otus.common.data.promo.PromoRepository
import ru.otus.common.data.promo.PromoRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Singleton
    @Binds
    fun bindPromoRepository(impl: PromoRepositoryImpl): PromoRepository
}
