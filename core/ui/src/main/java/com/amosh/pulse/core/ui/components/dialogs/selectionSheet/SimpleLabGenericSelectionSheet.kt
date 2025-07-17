package com.amosh.pulse.core.ui.components.dialogs.selectionSheet

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.amosh.pulse.core.ui.R
import com.amosh.pulse.core.ui.components.action.SimpleLabCheckBoxCompose
import com.amosh.pulse.core.ui.components.action.SimpleLabLoadingComponent
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.extension.noRippleClickable
import com.amosh.pulse.core.ui.theme.SimpleLabBodySmall
import com.amosh.pulse.core.ui.theme.SimpleLabLabelMedium
import com.amosh.pulse.core.ui.theme.SimpleLabLabelSmall
import com.amosh.pulse.core.ui.theme.SimpleLabTitleMedium
import com.amosh.pulse.core.ui.theme.colorAccent
import com.amosh.pulse.core.ui.theme.platinum_600
import com.amosh.pulse.core.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SimpleLabGenericSelectionSheet(
    openSheetState: MutableState<Boolean>,
    title: String,
    fullSelectionList: List<String>,
    preSelected: MutableList<String> = mutableListOf(),
    singleSelection: Boolean = true,
    canBeCleared: Boolean = true,
    isSearchable: Boolean = true,
    showSeparator: Boolean = false,
    onClear: () -> Unit,
    onDone: (List<String>) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchState: SearchState = rememberSearchState(suggestions = fullSelectionList)
    val selectionLisState = remember { preSelected }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    if (openSheetState.value) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                openSheetState.value = false
                searchState.query = TextFieldValue("")
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = screenHeight.times(0.9f))
                    .wrapContentHeight()
                    .padding(
                        start = MaterialTheme.spacing.medium16,
                        end = MaterialTheme.spacing.medium16,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimpleLabTextViewCompose(
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.spacing.small8,
                            vertical = MaterialTheme.spacing.special12
                        ),
                        text = title,
                        textColor = MaterialTheme.colorScheme.primary,
                        textStyle = SimpleLabTitleMedium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (canBeCleared) {
                        SimpleLabTextViewCompose(
                            text = stringResource(R.string.clear),
                            textColor = MaterialTheme.colorScheme.onPrimary,
                            textStyle = SimpleLabBodySmall,
                            modifier = Modifier
                                .background(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                .clipToBounds()
                                .padding(
                                    vertical = MaterialTheme.spacing.special4,
                                    horizontal = MaterialTheme.spacing.small8
                                )
                                .noRippleClickable {
                                    scope.launch {
                                        sheetState.hide()
                                        delay(200)
                                        onClear.invoke()
                                    }
                                },
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (isSearchable) {
                        SearchBar(
                            query = searchState.query,
                            onQueryChange = { searchState.query = it },
                            onSearchFocusChange = { searchState.focused = it },
                            onClearQuery = { searchState.query = TextFieldValue("") },
                            searching = searchState.searching,
                        )
                    }

                    LaunchedEffect(searchState.query.text) {
                        searchState.searching = true
                        delay(100)
                        searchState.searchResults = fullSelectionList.filter {
                            it.contains(
                                searchState.query.text,
                                true
                            )
                        }
                        searchState.searching = false
                    }

                    when (searchState.searchDisplay) {
                        SearchDisplay.InitialResults, SearchDisplay.Suggestions -> {
                            SearchResultsList(
                                entries = searchState.suggestions,
                                selectionListState = selectionLisState,
                                singleSelection = singleSelection,
                                showSeparator = showSeparator,
                                scope = scope,
                                sheetState = sheetState,
                                keyboardController = keyboardController,
                                onDone = onDone
                            )
                        }

                        SearchDisplay.Results -> {
                            SearchResultsList(
                                entries = searchState.searchResults,
                                selectionListState = selectionLisState,
                                singleSelection = singleSelection,
                                showSeparator = showSeparator,
                                scope = scope,
                                sheetState = sheetState,
                                keyboardController = keyboardController,
                                onDone = onDone
                            )
                        }

                        SearchDisplay.NoResults -> {
                            Text(
                                text = stringResource(id = R.string.no_search_result),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(MaterialTheme.spacing.xLarge32),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                if (!singleSelection) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium16))

                    Button(
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                            }
                            keyboardController?.hide()
                            onDone.invoke(selectionLisState)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.medium16),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.done),
                            color = Color.White
                        )
                    }
                }

                Spacer(Modifier.size(MaterialTheme.spacing.xLarge32))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchResultsList(
    entries: List<String>,
    selectionListState: MutableList<String>,
    singleSelection: Boolean,
    showSeparator: Boolean,
    scope: CoroutineScope,
    sheetState: SheetState,
    keyboardController: SoftwareKeyboardController?,
    onDone: (List<String>) -> Unit, // Adjust type as needed
) {
    LazyColumn {
        items(entries.size) { index ->
            SelectionEntry(
                index = index,
                entries = entries,
                preSelected = selectionListState,
                singleSelection = singleSelection,
                showSeparator = showSeparator,
                onSelected = {
                    if (singleSelection) {
                        scope.launch {
                            sheetState.hide()
                            keyboardController?.hide()
                            delay(200)
                            onDone.invoke(selectionListState)
                        }
                    }
                }
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchTextField(
            query,
            onQueryChange,
            onSearchFocusChange,
            onClearQuery,
            searching,
            modifier.weight(1f)
        )
    }
}

@Composable
private fun SearchTextField(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier,
) {

    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = modifier
            .height(MaterialTheme.spacing.special56)
            .padding(
                vertical = MaterialTheme.spacing.small8,
                horizontal = MaterialTheme.spacing.small8
            ),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = CircleShape,
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (query.text.isEmpty()) {
                SearchHint(modifier.padding(horizontal = MaterialTheme.spacing.medium16))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        }
                        .focusRequester(focusRequester)
                        .padding(
                            horizontal = MaterialTheme.spacing.medium16,
                            vertical = MaterialTheme.spacing.small8
                        ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions.Default,
                    textStyle = SimpleLabLabelMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                innerTextField()
                            }
                        }
                    }
                )

                when {
                    searching -> {
                        Box(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium16)
                        ) {
                            SimpleLabLoadingComponent(
                                modifier = Modifier.size(MaterialTheme.spacing.medium16)
                            )
                        }
                    }

                    query.text.isNotEmpty() -> {
                        IconButton(onClick = onClearQuery) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchHint(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        SimpleLabTextViewCompose(
            text = stringResource(id = R.string.search),
            textColor = platinum_600,
            textStyle = SimpleLabLabelSmall
        )
    }
}

@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    suggestions: List<String> = emptyList(),
    searchResults: List<String> = emptyList(),
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            suggestions = suggestions,
            searchResults = searchResults
        )
    }
}

@Composable
private fun SelectionEntry(
    modifier: Modifier = Modifier,
    index: Int,
    entries: List<String>,
    preSelected: MutableList<String> = mutableListOf(),
    singleSelection: Boolean,
    showSeparator: Boolean,
    onSelected: () -> Unit,
) {
    val currentItem = entries[index]
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.small8,
                vertical = MaterialTheme.spacing.special4
            )
            .noRippleClickable {}
    ) {
        val checkedState = remember { mutableStateOf(preSelected.contains(currentItem)) }
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SimpleLabTextViewCompose(
                    modifier = modifier
                        .weight(1f)
                        .padding(
                            horizontal = MaterialTheme.spacing.small8,
                            vertical = MaterialTheme.spacing.special12
                        )
                        .then(
                            Modifier.noRippleClickable {
                                handleSelection(
                                    preSelected,
                                    currentItem,
                                    checkedState,
                                    onSelected,
                                    clearBeforeAdd = singleSelection
                                )
                            }
                        ),
                    text = currentItem,
                )

                if (!singleSelection) {
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.small8))

                    SimpleLabCheckBoxCompose(
                        isCheckedState = checkedState,
                        onCheckChange = {
                            handleSelection(
                                preSelected,
                                currentItem,
                                checkedState,
                                onSelected,
                                clearBeforeAdd = false
                            )
                            checkedState.value = it
                        },
                    )
                }
            }

            if (showSeparator) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    thickness = MaterialTheme.spacing.special1
                )
            }
        }
    }
}

private fun handleSelection(
    preSelected: MutableList<String>,
    currentItem: String,
    checkedState: MutableState<Boolean>,
    onSelected: () -> Unit,
    clearBeforeAdd: Boolean,
) {
    if (!preSelected.contains(currentItem)) {
        if (clearBeforeAdd) preSelected.clear()
        preSelected.add(currentItem)
    } else {
        preSelected.remove(currentItem)
    }

    checkedState.value = !checkedState.value
    if (preSelected.isNotEmpty()) onSelected.invoke()
}
