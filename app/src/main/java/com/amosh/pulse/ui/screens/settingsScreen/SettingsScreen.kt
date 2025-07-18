package com.amosh.pulse.ui.screens.settingsScreen

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amosh.pulse.R
import com.amosh.pulse.core.ui.components.action.SimpleLabCircularImageView
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.components.dialogs.filePicker.FilePickerBottomSheet
import com.amosh.pulse.core.ui.components.dialogs.filePicker.FileType
import com.amosh.pulse.core.ui.components.inputFields.DataInputStatus
import com.amosh.pulse.core.ui.components.inputFields.DataInputType
import com.amosh.pulse.core.ui.components.inputFields.SimpleLabDataInputFieldCompose
import com.amosh.pulse.core.ui.extension.noRippleClickable
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.ui.screens.MainViewModel
import com.amosh.pulse.ui.screens.changeLanguage.ChangeLanguageSheet
import com.amosh.pulse.ui.screens.changeLanguage.ChangeThemeSheet
import com.amosh.pulse.utils.LocalNavHostController
import com.amosh.pulse.utils.getFilePathFromContentUri
import com.amosh.pulse.utils.saveBitmapToFile

@Preview
@Composable
fun SettingsScreen(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val navHost = LocalNavHostController.current
    val isRTL = LocalLayoutDirection.current == LayoutDirection.Rtl

    val userDataState = viewModel.userDataState.collectAsStateWithLifecycle()

    val showLanguageSheet = remember { mutableStateOf(false) }
    val showThemeSheet = remember { mutableStateOf(false) }

    var userImage by rememberSaveable { mutableStateOf(userDataState.value.profilePic) }
    var userNameText by rememberSaveable { mutableStateOf(userDataState.value.userName) }
    val showFilePickerSheet = remember { mutableStateOf(false) }

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                val filePath = getFilePathFromContentUri(context, uri)

                userImage = when {
                    filePath != null -> filePath
                    else -> {
                        context.contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                        uri.toString()
                    }
                }
                viewModel.updateUserData(userNameText, userImage)
            }
        }
    )
    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                val uri = saveBitmapToFile(context, bitmap)
                userImage = uri.toString()
                viewModel.updateUserData(userNameText, userImage)
            }
        }
    )

    LaunchedEffect(userDataState.value) {
        userNameText = userDataState.value.userName
        userImage = userDataState.value.profilePic
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageSize = MaterialTheme.spacing.special100
            ConstraintLayout(
                modifier = Modifier
                    .size(imageSize)
            ) {
                val (centerView, circularButton) = createRefs()

                SimpleLabCircularImageView(
                    imageUrl = userImage,
                    placeholder = Icons.Rounded.Person,
                    size = imageSize
                )

                IconButton(
                    modifier = Modifier.constrainAs(circularButton) {
                        if (isRTL) {
                            start.linkTo(centerView.start, margin = -((imageSize / 2).minus(10.dp)))
                            top.linkTo(centerView.top, margin = imageSize - 40.dp)
                        } else {
                            start.linkTo(centerView.start, margin = (imageSize / 2).plus(10.dp))
                            top.linkTo(centerView.top, margin = (imageSize / 2).plus(10.dp))
                        }
                    },
                    onClick = {
                        showFilePickerSheet.value = true
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(MaterialTheme.spacing.large24)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when {
                                userImage.isBlank() -> Icons.Filled.Add
                                else -> Icons.Filled.Edit
                            },
                            modifier = Modifier.size(MaterialTheme.spacing.medium16),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium16))

            SimpleLabDataInputFieldCompose(
                labelText = stringResource(id = R.string.username),
                value = userNameText,
                dataInputType = DataInputType.TEXT,
                startIcon = Icons.Rounded.Person,
                dataInputStatus = DataInputStatus.ERROR
            ) {
                userNameText = it
                viewModel.updateUserData(userNameText, userImage)
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium16))

            SimpleLabTextViewCompose(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.small8,
                        vertical = MaterialTheme.spacing.special12
                    )
                    .fillMaxWidth()
                    .noRippleClickable { showLanguageSheet.value = true },
                text = stringResource(id = R.string.language),
                endIcon = Icons.Outlined.KeyboardArrowDown,
                startIcon = Icons.Outlined.Language,
                endIconColor = MaterialTheme.colorScheme.secondary,
                startIconColor = MaterialTheme.colorScheme.secondary,
                alignEndIconToViewEnd = true,
                textColor = MaterialTheme.colorScheme.onBackground
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.inversePrimary,
                thickness = MaterialTheme.spacing.special1
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium16))

            SimpleLabTextViewCompose(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.small8,
                        vertical = MaterialTheme.spacing.special12
                    )
                    .fillMaxWidth()
                    .noRippleClickable { showThemeSheet.value = true },
                text = stringResource(id = R.string.theme),
                endIcon = Icons.Outlined.KeyboardArrowDown,
                startIcon = Icons.Filled.Contrast,
                endIconColor = MaterialTheme.colorScheme.secondary,
                startIconColor = MaterialTheme.colorScheme.secondary,
                alignEndIconToViewEnd = true,
                textColor = MaterialTheme.colorScheme.onBackground
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.inversePrimary,
                thickness = MaterialTheme.spacing.special1
            )

        }
    }

    ChangeLanguageSheet(
        showLanguageSheet,
        onSelectLanguage = {
            viewModel.handleSetLanguage(it)
            showLanguageSheet.value = false
        },
        onDismissRequest = {
            showLanguageSheet.value = false
        }
    )

    ChangeThemeSheet(
        showThemeSheet,
        onSelectTheme = {
            viewModel.handleSetTheme(it)
            showThemeSheet.value = false
        },
        onDismissRequest = {
            showThemeSheet.value = false
        }
    )

    FilePickerBottomSheet(
        showFilePickerSheet,
        showGallery = true,
        showCamera = true,
        showPdf = false,
        onDismissRequest = {
            showFilePickerSheet.value = false
        },
        onSelectType = {
            when (it) {
                FileType.GALLERY -> launcherGallery.launch("image/*")
                FileType.CAMERA -> launcherCamera.launch(null)
                FileType.PDF -> Unit
            }
            showFilePickerSheet.value = false
        }
    )
}