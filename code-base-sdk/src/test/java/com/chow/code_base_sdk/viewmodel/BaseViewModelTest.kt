package com.chow.code_base_sdk.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chow.code_base_sdk.model.domain.DomainCharacter
import com.chow.code_base_sdk.usecases.BaseUseCase
import com.chow.code_base_sdk.utils.CharactersType
import com.chow.code_base_sdk.utils.UIState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var testObject: BaseViewModel

    private val mockUseCase = mockk<BaseUseCase>(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        testObject = BaseViewModel(mockUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get characters when the use case retrieves success it will return a success result state`() {
        // every -> everytime a mock object calls something it will return something else
        val characterType = CharactersType.SIMPSONS
        every { mockUseCase(characterType) } returns flowOf(
            UIState.Success(listOf(mockk(), mockk()))
        )

        val uiStates = mutableListOf<UIState<List<DomainCharacter>>>()

        // we have our input now we need to observe the output
        val job = testScope.launch {
            testObject.characters.collect {
                uiStates.add(it)
            }
        }

        // create the action
        testObject.getCharacters(characterType)

        assert(uiStates[0] is UIState.Loading)
        assert(uiStates[1] is UIState.Success)
        //checks to make sure that on Success it contains 2 items mockk(),mockk()
        val it = uiStates[1] as UIState.Success
        assertEquals(2, it.data.size)


        job.cancel()
    }

    @Test
    fun `getCharacters should emit Error UIState when use case returns error`() {
        // Arrange
        val error = Exception("Test Error")
        coEvery { mockUseCase(any()) } returns flow { emit(UIState.Error(error)) }
        val charactersType = CharactersType.SIMPSONS

        // Act
        testObject.getCharacters(charactersType)

        // Assert
        val expectedState = UIState.Error(error)
        val currentState = testObject.characters.value
        assertEquals(expectedState, currentState)
    }

    @Test
    fun `set the current item new selected item to returns the selected  item`() {
        var dChar: DomainCharacter? = null
        var mockChar = mockk<DomainCharacter>(relaxed = true) {
            every { description } returns "some"
        }

        // we have our input now we need to observe the output
        val job = testScope.launch {
            testObject.characterSelected.collect {
                dChar = it
            }
        }

        // create the action
        testObject.setCurrentItem(mockChar)

        assertNotNull(dChar)
        assertEquals("some", dChar?.description)



        job.cancel()
    }

    @Test
    fun `searchItem should filter data based on input string`() {
        // Arrange
        val testData = listOf(
            DomainCharacter("Alice", "A brave adventurer."),
            DomainCharacter("Bob", "A wise wizard."),
            DomainCharacter("Charlie", "A cunning rogue.")
        )

        testObject.prevData = testData

        // Action
        testObject.searchItem("brave")

        // Assert
        assertEquals(1, testObject.search.value?.size)
        assertEquals("Alice", testObject.search.value?.get(0)?.name)
    }
}
