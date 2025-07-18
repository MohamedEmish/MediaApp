package com.amosh.pulse.ui.screens.changeLanguage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.extension.noRippleClickable
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.ui.ext.textRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeLanguageSheet(
    openLanguageSheetState: MutableState<Boolean>,
    languagesList: List<SupportedLanguages> = SupportedLanguages.entries,
    onSelectLanguage: (SupportedLanguages) -> Unit,
    onDismissRequest: (Boolean) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (openLanguageSheetState.value) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onDismissRequest.invoke(false) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.spacing.medium16,
                        end = MaterialTheme.spacing.medium16,
                        top = MaterialTheme.spacing.small8,
                        bottom = MaterialTheme.spacing.xXLarge40
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(languagesList.size) { position ->
                        LanguageListItem(
                            lang = languagesList[position],
                            onClick = { onSelectLanguage.invoke(it) }
                        )
                    }
                }
                Spacer(Modifier.size(MaterialTheme.spacing.xLarge32))
            }
        }
    }
}

@Composable
private fun LanguageListItem(
    lang: SupportedLanguages,
    onClick: (SupportedLanguages) -> Unit,
) {
    SimpleLabTextViewCompose(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.spacing.small8,
                vertical = MaterialTheme.spacing.special12
            )
            .fillMaxWidth()
            .noRippleClickable { onClick.invoke(lang) },
        text = stringResource(id = lang.textRes),
        textColor = MaterialTheme.colorScheme.onBackground
    )

    HorizontalDivider(
        color = MaterialTheme.colorScheme.inversePrimary,
        thickness = MaterialTheme.spacing.special1
    )
}