package ru.otus.marketsample.promo.feature

import ru.otus.marketsample.promo.domain.Promo
import javax.inject.Inject

class PromoStateFactory @Inject constructor() {
    fun map(promo: Promo): PromoState {
        return PromoState(
            id = promo.id,
            name = promo.name,
            image = promo.image,
            description = promo.description,
        )
    }
}
