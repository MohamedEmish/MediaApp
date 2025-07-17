package com.amosh.pulse.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amosh.pulse.core.data.di.IoDispatcher
import com.amosh.pulse.core.domain.useCases.AppLanguageUseCase
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appLanguageUseCase: AppLanguageUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _currentLanguage = MutableStateFlow(SupportedLanguages.EN)
    val currentLanguage = _currentLanguage.asStateFlow()

    init {
        getCurrentLanguage()
    }

    fun handleSetLanguage(language: SupportedLanguages) = viewModelScope.launch {
        appLanguageUseCase.updateAppLanguage(language)
    }

    private fun getCurrentLanguage() = appLanguageUseCase.getAppLanguage()
        .onEach { _currentLanguage.emit(it) }
        .catch { _currentLanguage.emit(SupportedLanguages.EN) }
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)
}
