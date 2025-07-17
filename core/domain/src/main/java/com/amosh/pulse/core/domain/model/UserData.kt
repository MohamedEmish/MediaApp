package com.amosh.pulse.core.domain.model

import android.os.Parcelable
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    @SerializedName("name") var userName: String = "",
    @SerializedName("profilePic") var profilePic: String = "",
    @SerializedName("language") var language: SupportedLanguages = SupportedLanguages.EN,
): Parcelable
