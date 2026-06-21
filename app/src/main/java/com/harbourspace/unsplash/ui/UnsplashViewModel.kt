package com.harbourspace.unsplash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harbourspace.unsplash.api.UnsplashProvider
import com.harbourspace.unsplash.data.UnsplashItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UnsplashViewModel: ViewModel() {

  private val _images = MutableStateFlow<UiState<List<UnsplashItem>>>(UiState.Loading)
  val images: StateFlow<UiState<List<UnsplashItem>>> = _images.asStateFlow()

  private val provider = UnsplashProvider()

  init {
    fetchImage()
  }

  fun refreshingImages() {
    viewModelScope.launch {
      _images.value = UiState.Refreshing
    }

    fetchImage()
  }

  fun fetchImage() {
    viewModelScope.launch {
      provider.fetchImages()
        .collect { images ->
          _images.value = UiState.Success(images)
        }
    }
  }

  fun search(query: String) {
    viewModelScope.launch {
      provider.search(query)
        .collect { images ->
          _images.value = UiState.Success(images)
        }
    }
  }
}