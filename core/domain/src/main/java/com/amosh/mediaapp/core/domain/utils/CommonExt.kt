package com.amosh.mediaapp.core.domain.utils

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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