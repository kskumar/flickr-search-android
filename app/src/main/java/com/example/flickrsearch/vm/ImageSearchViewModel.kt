package com.example.flickrsearch.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrsearch.data.ImageSearchRepository
import com.example.flickrsearch.model.ImageSearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val repository: ImageSearchRepository
): ViewModel() {

    val actions: MutableSharedFlow<ViewAction> = MutableSharedFlow()
    val state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val events: MutableSharedFlow<ViewEvent> = MutableSharedFlow()

    init {
        collectActions()
    }

    private fun collectActions() {
        viewModelScope.launch{
            actions.collect {
                handleAction(it)
            }
        }
    }

    fun search(userQuery: String) {
        viewModelScope.launch {
            val response: Response<ImageSearchResponse> = repository.search(userQuery = userQuery)
            response.body()?.let {
                state.value = state.value.copy(
                    results = it.resultList,
                    isLoading = false,
                )
            }
        }
    }

    fun updateState(newState: ViewState) {
        viewModelScope.launch {
            state.emit(value = newState)
        }
    }

    fun sendAction(action: ViewAction) {
        viewModelScope.launch {
            actions.emit(value = action)
        }
    }

    fun sendEvent(event: ViewEvent) {
        viewModelScope.launch {
            events.emit(value = event)
        }
    }

    fun handleAction(action: ViewAction) {
        when (action) {
            is ViewAction.ImageClick -> sendEvent(event = ViewEvent.NavigateToImageDetails(
                imageUrl = action.imageUrl,
                title = action.title,
                description = action.description,
                author = action.authorName,
                date = action.publishedDate
            ))
        }
    }

}