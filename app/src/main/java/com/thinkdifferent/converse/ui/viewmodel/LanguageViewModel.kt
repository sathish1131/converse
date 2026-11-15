package com.thinkdifferent.converse.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.thinkdifferent.converse.domain.enum.Language
import com.thinkdifferent.converse.ui.state.LanguageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LanguageViewModel : ViewModel() {

    private val _state = MutableStateFlow(LanguageState())

    val state = _state.asStateFlow()

    fun update(source: Language, target: Language) {
        _state.update {
            it.copy(
                source = source,
                target = target
            )
        }
    }

    fun reverse() {
        _state.update {
            it.copy(
                source = it.target,
                target = it.source
            )
        }
    }

}