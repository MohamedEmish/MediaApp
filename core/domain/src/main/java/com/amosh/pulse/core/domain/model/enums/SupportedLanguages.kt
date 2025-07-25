package com.amosh.pulse.core.domain.model.enums

import android.text.TextUtils
import java.util.Locale

enum class SupportedLanguages {
    EN,
    AR;

    companion object {
        /**
         * Use this method instead of valueOf.
         * @param strEnum Enum String
         * @return the corresponding enum to the String
         */
        fun getEnum(strEnum: String) = when {
            TextUtils.isEmpty(strEnum) -> null
            else -> try {
                valueOf(
                    strEnum.uppercase(
                        Locale.US
                    ).replace(" ".toRegex(), "_")
                )

            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}