package com.amosh.pulse.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amosh.pulse.core.data.di.IoDispatcher
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import com.amosh.pulse.core.domain.useCases.AppLanguageUseCase
import com.amosh.pulse.core.domain.useCases.AppThemeUseCase
import com.amosh.pulse.core.domain.useCases.GetUserDataUseCase
import com.amosh.pulse.core.domain.useCases.UpdateUserDataUseCase
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
    private val appThemeUseCase: AppThemeUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _currentLanguage = MutableStateFlow(SupportedLanguages.EN)
    val currentLanguage = _currentLanguage.asStateFlow()

    private val _currentTheme = MutableStateFlow(SupportedTheme.LIGHT)
    val currentTheme = _currentTheme.asStateFlow()

    private val _userDataState = MutableStateFlow(UserData())
    val userDataState = _userDataState.asStateFlow()

    init {
        getStoredUserData()
        getCurrentLanguage()
        getCurrentTheme()
    }

    fun handleSetLanguage(language: SupportedLanguages) = viewModelScope.launch {
        appLanguageUseCase.updateAppLanguage(language)
    }

    private fun getCurrentLanguage() = appLanguageUseCase.getAppLanguage()
        .onEach { _currentLanguage.emit(it) }
        .catch { _currentLanguage.emit(SupportedLanguages.EN) }
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)

    private fun getCurrentTheme() = appThemeUseCase.getAppTheme()
        .onEach { _currentTheme.emit(it) }
        .catch { _currentTheme.emit(SupportedTheme.SYSTEM) }
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)

    private fun getStoredUserData() = getUserDataUseCase.getUserData()
        .onEach { _userDataState.emit(it) }
        .catch {}
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)

    fun updateUserData(username: String, profilePic: String) {
        viewModelScope.launch {
            updateUserDataUseCase.updateName(username)
            profilePic.takeIf { it.isNotBlank() }?.let {
                updateUserDataUseCase.updateProfilePic(it)
            }
        }
    }

    fun handleSetTheme(theme: SupportedTheme)= viewModelScope.launch {
        appThemeUseCase.updateAppTheme(theme)
    }
}
