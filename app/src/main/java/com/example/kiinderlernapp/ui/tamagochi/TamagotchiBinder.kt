package com.example.kiinderlernapp.ui.tamagochi

import TamagotchiService
import android.os.Binder

class TamagotchiBinder(private val service: TamagotchiService) : Binder() {
    fun getService(): TamagotchiService {
        return service
    }
}
