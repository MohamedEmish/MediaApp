package com.amosh.pulse.core.ui.components.uploadFiled

enum class FileType {
    FILE,
    IMAGE;

    val fileType
        get() = when (this) {
            FILE -> {}
            IMAGE -> {}
        }
}