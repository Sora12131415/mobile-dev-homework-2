package com.harbourspace.unsplash.data

import com.harbourspace.unsplash.api.UnsplashProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class UnsplashRepository(
    private val provider: UnsplashProvider,
    private val dao: UnsplashDao
) {
    val allImages: Flow<List<UnsplashItem>> = dao.getAllImages()

    suspend fun fetchImages() {
        provider.fetchImages().collect { images ->
            dao.insertAll(images)
        }
    }

    fun search(query: String): Flow<List<UnsplashItem>> {
        return provider.search(query)
    }

    suspend fun fetchImageById(id: String): Flow<UnsplashItem> {
        return provider.fetchImageById(id).onEach { image ->
            dao.insertAll(listOf(image))
        }
    }
}
