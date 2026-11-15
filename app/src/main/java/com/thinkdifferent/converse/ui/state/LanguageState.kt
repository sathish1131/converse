package com.thinkdifferent.converse.ui.state

import com.thinkdifferent.converse.domain.enum.Language

data class LanguageState(
    val source: Language = Language.HINDI,
    val target: Language = Language.ENGLISH
)