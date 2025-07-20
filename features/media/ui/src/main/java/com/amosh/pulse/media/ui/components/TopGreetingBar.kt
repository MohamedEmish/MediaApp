package com.amosh.pulse.media.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.amosh.pulse.core.domain.constants.Constants.GOOD_AFTERNOON
import com.amosh.pulse.core.domain.constants.Constants.GOOD_EVENING
import com.amosh.pulse.core.domain.constants.Constants.GOOD_MORNING
import com.amosh.pulse.core.ui.components.action.SimpleLabCircularImageView
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.media.ui.R
import java.time.LocalTime

@Composable
fun TopGreetingBar(
    userName: String?,
    userImage: String?,
    modifier: Modifier = Modifier,
) {
    val greetingResId = rememberGreetingResId(userName)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        userImage?.let {
            SimpleLabCircularImageView(
                imageUrl = userImage,
                placeholder = Icons.Rounded.Person,
                size = MaterialTheme.spacing.large24
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.spacing.special4))

        Text(
            text = if (userName.isNullOrBlank()) {
                stringResource(id = greetingResId)
            } else {
                stringResource(id = greetingResId, userName)
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun rememberGreetingResId(userName: String?): Int {
    val greeting = remember { getGreeting() }

    return remember(greeting, userName) {
        when (greeting) {
            GOOD_MORNING -> if (userName.isNullOrBlank()) {
                R.string.greeting_morning
            } else {
                R.string.greeting_morning_with_name
            }

            GOOD_AFTERNOON -> if (userName.isNullOrBlank()) {
                R.string.greeting_afternoon
            } else {
                R.string.greeting_afternoon_with_name
            }

            else -> if (userName.isNullOrBlank()) {
                R.string.greeting_evening
            } else {
                R.string.greeting_evening_with_name
            }
        }
    }
}

private fun getGreeting(): String {
    val hourOfDay = LocalTime.now().hour

    return when (hourOfDay) {
        in 5..11 -> GOOD_MORNING
        in 12..17 -> GOOD_AFTERNOON
        else -> GOOD_EVENING
    }
}