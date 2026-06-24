package com.harbourspace.unsplash.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.harbourspace.unsplash.api.UnsplashProvider
import com.harbourspace.unsplash.data.UnsplashDatabase
import com.harbourspace.unsplash.data.UnsplashItem
import com.harbourspace.unsplash.data.UnsplashRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UnsplashViewModel(application: Application): AndroidViewModel(application) {

  private val _images = MutableStateFlow<UiState<List<UnsplashItem>>>(UiState.Loading)
  val images: StateFlow<UiState<List<UnsplashItem>>> = _images.asStateFlow()

  private val repository: UnsplashRepository

  init {
    val dao = UnsplashDatabase.getDatabase(application).unsplashDao()
    repository = UnsplashRepository(UnsplashProvider(), dao)
    
    viewModelScope.launch {
        repository.allImages.collect { list ->
            if (list.isNotEmpty()) {
                _images.value = UiState.Success(list)
            }
        }
    }
    
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
      try {
        repository.fetchImages()
      } catch (e: Exception) {
        if (_images.value !is UiState.Success) {
          _images.value = UiState.Error(e.message ?: "Unknown error")
        }
      }
    }
  }

  fun search(query: String) {
    viewModelScope.launch {
      _images.value = UiState.Loading
      try {
        repository.search(query).collect { list ->
          _images.value = UiState.Success(list)
        }
      } catch (e: Exception) {
        _images.value = UiState.Error(e.message ?: "Unknown error")
      }
    }
  }
}
