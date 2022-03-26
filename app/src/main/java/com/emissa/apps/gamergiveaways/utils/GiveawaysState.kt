package com.emissa.apps.gamergiveaways.utils

import com.emissa.apps.gamergiveaways.model.Giveaways

sealed class GiveawaysState {
    object LOADING: GiveawaysState()
    class SUCCESS<T>(val giveaways: T, isLocal: Boolean = false) : GiveawaysState()
    class ERROR(val error: Throwable): GiveawaysState()
}
