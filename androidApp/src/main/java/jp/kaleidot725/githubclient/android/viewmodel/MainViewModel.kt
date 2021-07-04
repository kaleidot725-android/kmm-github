package jp.kaleidot725.githubclient.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.kaleidot725.githubclient.api.dto.GistDto
import jp.kaleidot725.githubclient.repository.GistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val gistRepository: GistRepository) : ViewModel() {
    private val _gists = MutableStateFlow<UiState<List<GistDto>, Unit>>(UiState.Loading)
    val gists: StateFlow<UiState<List<GistDto>, Unit>> = _gists

    init {
        fetchGists()
    }

    fun fetchGists() {
        viewModelScope.launch {
            try {
                _gists.value = UiState.Success(gistRepository.getGists(TEST_USER))
            } catch (e: Exception) {
                _gists.value = UiState.Error(Unit)
            }
        }
    }

    companion object {
        val TEST_USER = "kaleidot725"
    }
}