package com.thinkdifferent.converse.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.thinkdifferent.converse.data.preferences.LanguagePreferencesRepository
import com.thinkdifferent.converse.domain.enum.Language
import com.thinkdifferent.converse.ui.state.LanguageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LanguageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LanguagePreferencesRepository(application)
    private val _state = MutableStateFlow(LanguageState())
    val state = _state.asStateFlow()
    private val _savedState = MutableStateFlow(LanguageState())
    val savedState = _savedState.asStateFlow()
    private var hasSessionChanges = false

    init {
        viewModelScope.launch {
            val savedLanguageState = repository.state.first()
            _savedState.value = savedLanguageState
            if (!hasSessionChanges) {
                _state.value = savedLanguageState
            }
        }
    }

    fun update(source: Language, target: Language) {
        val languageState = LanguageState(source, target)
        hasSessionChanges = true
        _state.value = languageState
        _savedState.value = languageState
        viewModelScope.launch {
            repository.update(source, target)
        }
    }

    fun reverse() {
        hasSessionChanges = true
        _state.update {
            it.copy(
                source = it.target,
                target = it.source
            )
        }
    }
}