package com.amosh.pulse.core.domain

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
abstract class BaseTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    open fun setup() {
        MockKAnnotations.init(this)
    }
}