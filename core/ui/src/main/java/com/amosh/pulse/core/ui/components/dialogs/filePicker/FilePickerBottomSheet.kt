package com.amosh.pulse.core.ui.components.dialogs.filePicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.amosh.pulse.core.ui.R
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.extension.noRippleClickable
import com.amosh.pulse.core.ui.theme.LightColors.secondary
import com.amosh.pulse.core.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilePickerBottomSheet(
    openPickerState: MutableState<Boolean>,
    showGallery: Boolean,
    showCamera: Boolean,
    showPdf: Boolean,
    onSelectType: (FileType) -> Unit,
    onDismissRequest: (Boolean) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (openPickerState.value) {
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
                if (showCamera) {
                    SectionView(
                        text = stringResource(id = R.string.camera),
                        startIcon = Icons.Filled.CameraAlt,
                        type = FileType.CAMERA,
                        onSelectType
                    )
                }

                if (showGallery) {
                    SectionView(
                        text = stringResource(id = R.string.gallery),
                        startIcon = Icons.Filled.Camera,
                        type = FileType.GALLERY,
                        onSelectType
                    )
                }

                if (showPdf) {
                    SectionView(
                        text = stringResource(id = R.string.pdf_file),
                        startIcon = Icons.Filled.AttachFile,
                        type = FileType.PDF,
                        onSelectType
                    )
                }

                Spacer(Modifier.size(MaterialTheme.spacing.xLarge32))
            }
        }
    }
}

@Composable
private fun SectionView(
    text: String,
    startIcon: ImageVector,
    type: FileType,
    onSelectType: (FileType) -> Unit,
) {
    SimpleLabTextViewCompose(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.spacing.small8,
                vertical = MaterialTheme.spacing.special12
            )
            .fillMaxWidth()
            .noRippleClickable {
                onSelectType.invoke(type)
            },
        text = text,
        startIcon = startIcon,
        startIconColor = secondary,
    )

    HorizontalDivider(
        color = MaterialTheme.colorScheme.inversePrimary,
        thickness = MaterialTheme.spacing.special1
    )
}