package com.thinkdifferent.converse.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.thinkdifferent.converse.ui.components.LanguageDropdown
import com.thinkdifferent.converse.ui.viewmodel.LanguageViewModel

@Composable
fun SettingScreen(
    languageViewModel: LanguageViewModel,
    onSave: () -> Unit
) {
    val context = LocalContext.current
    val savedLanguageState by languageViewModel.savedState.collectAsState()
    var source by remember(savedLanguageState.source, savedLanguageState.target) {
        mutableStateOf(savedLanguageState.source)
    }
    var target by remember(savedLanguageState.source, savedLanguageState.target) {
        mutableStateOf(savedLanguageState.target)
    }
    val hasChanges = !(
        (source == savedLanguageState.source && target == savedLanguageState.target) ||
            (source == savedLanguageState.target && target == savedLanguageState.source)
        )
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        LanguageDropdown(
            label = "Source Language",
            current = source,
            onSelected = {
                source = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LanguageDropdown(
            label = "Target Language",
            current = target,
            onSelected = {
                target = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = hasChanges,
            onClick = {
                languageViewModel.update(source, target)
                onSave()
                Toast.makeText(
                    context,
                    "Language settings saved",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            Text("Save")
        }
    }
}