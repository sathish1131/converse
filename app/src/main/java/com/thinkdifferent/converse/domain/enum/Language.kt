package com.thinkdifferent.converse.domain.enum

enum class Language(
    val displayName: String,
    val code: String
) {
    ENGLISH("English", "eng"),
    HINDI("Hindi", "hin"),
    KANNADA("Kannada", "kan"),
    MALAYALAM("Malayalam", "mal")
}