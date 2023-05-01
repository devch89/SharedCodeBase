package com.chow.code_base_sdk.rest

import com.chow.code_base_sdk.utils.CharactersType
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BaseRepositoryImplTest {

    private val mockServiceApi = mockk<BaseService>()

    private lateinit var testObject: BaseRepository


    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    @Before
    fun setUp() {
        testObject = BaseRepositoryImpl(mockServiceApi, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get character when server provides error response it will return error state`(){
        val endpoint = "simpsons characters"
        val characterType = CharactersType.SIMPSONS
        coEvery { mockServiceApi.getCharacters(endpoint) }

        testObject.getCharacters(characterType)
    }
}