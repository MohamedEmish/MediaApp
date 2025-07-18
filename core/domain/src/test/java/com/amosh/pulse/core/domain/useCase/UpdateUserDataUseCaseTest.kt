package com.amosh.pulse.core.domain.useCase

import com.amosh.pulse.core.domain.BaseTest
import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.useCases.UpdateUserDataUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalTime
class UpdateUserDataUseCaseTest : BaseTest() {
    private val repository = mockk<Repository>()
    private val useCase = UpdateUserDataUseCase(repository)

    @Test
    fun `updateName should call repository`() = runTest {
        // Given
        val name = "New Name"
        coEvery { repository.updateName(name) } returns Unit

        // When
        useCase.updateName(name)

        // Then
        coVerify { repository.updateName(name) }
    }

    @Test
    fun `updateProfilePic should call repository`() = runTest {
        // Given
        val pic = "profile_pic_url"
        coEvery { repository.updateProfilePic(pic) } returns Unit

        // When
        useCase.updateProfilePic(pic)

        // Then
        coVerify { repository.updateProfilePic(pic) }
    }
}