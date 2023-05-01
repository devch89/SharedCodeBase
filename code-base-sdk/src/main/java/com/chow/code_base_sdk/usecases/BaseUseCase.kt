package com.chow.code_base_sdk.usecases

import com.chow.code_base_sdk.model.domain.DomainCharacter
import com.chow.code_base_sdk.rest.BaseRepository
import com.chow.code_base_sdk.utils.CharactersType
import com.chow.code_base_sdk.utils.UIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BaseUseCase @Inject constructor(
    private val repository: BaseRepository
) {
    operator fun invoke(type: CharactersType): Flow<UIState<List<DomainCharacter>>> =
        repository.getCharacters(type)
}