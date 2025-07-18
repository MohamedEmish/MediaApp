package com.amosh.pulse.core.domain.model

import android.os.Parcelable
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    @SerializedName("name") var userName: String = "",
    @SerializedName("profilePic") var profilePic: String = "",
    @SerializedName("language") var language: SupportedLanguages = SupportedLanguages.EN,
    @SerializedName("theme") var theme: SupportedTheme = SupportedTheme.SYSTEM,
): Parcelable
