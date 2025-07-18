package com.amosh.pulse.core.domain.utils

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Response

fun <T> String?.toObject(classOfT: Class<T>): T? = Gson().fromJson(this, classOfT)

fun Any?.isNull() = this == null

fun Any?.toGson(): String = Gson().toJson(this)

fun <T> Flow<Response<T>>.mapDataOrThrow(): Flow<T> =
    map { it.body().orThrow(it) }

fun <T> T?.orThrow(
    response: Response<*>? = null,
    httpException: HttpException = HttpException(response),
): T = this ?: throw httpException

fun Boolean?.isTrue() = this != null && this == true

fun Boolean?.isFalse() = this != null && this == false

fun Boolean?.isNullOrFalse() = this == null || this == false

inline fun <reified T : Enum<T>> stringToEnum(type: String): T? {
    return enumValues<T>().firstOrNull { it.name.equals(type, ignoreCase = true) }
}

inline fun <reified T : Enum<T>>getEnumValue(strEnum: String?) = when {
    strEnum.isNullOrEmpty() -> null
    else -> stringToEnum<T>(strEnum)
}

fun Boolean?.isNullOrTrue() = this == null || this == true