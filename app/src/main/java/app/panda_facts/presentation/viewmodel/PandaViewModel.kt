package app.panda_facts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.panda_facts.domain.model.Panda
import app.panda_facts.domain.usecase.GetPandaFactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PandaUiState(
    val panda: Panda? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PandaViewModel @Inject constructor(
    private val getPandaFactUseCase: GetPandaFactUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PandaUiState(isLoading = true))
    val state: StateFlow<PandaUiState> = _state


    init {
        loadPanda()
    }

    fun loadPanda() {
        _state.value = PandaUiState(isLoading = true)
        viewModelScope.launch {
            val response = getPandaFactUseCase()
            if (response.isSuccess) _state.value = PandaUiState(panda = response.getOrNull())
            else _state.value = PandaUiState(error = response.exceptionOrNull()?.localizedMessage)
        }
    }


}