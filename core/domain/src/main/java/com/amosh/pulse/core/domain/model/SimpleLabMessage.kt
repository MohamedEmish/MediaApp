package com.amosh.pulse.core.domain.model

data class SimpleLabMessage(
    val id: Int = PRIMARY,
    var message: String = "",
) {
    companion object {
        const val FAILURE = 0
        const val WARNING = 1
        const val SUCCESS = 2
        const val PRIMARY = 3
        const val INFO = 4
    }
}
