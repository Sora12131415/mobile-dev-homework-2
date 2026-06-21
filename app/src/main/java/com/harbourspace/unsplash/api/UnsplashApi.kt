package com.harbourspace.unsplash.api

import com.harbourspace.unsplash.data.SearchResult
import com.harbourspace.unsplash.data.UnsplashItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val AUTHORIZATION_CLIENT_ID = "Client-ID"
private const val ACCESS_KEY = "kO6m_gqEjnmJCdrg7uCUHirlO-u9soB6TPJ66IYXhGk"

interface UnsplashApi {

  @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
  @GET("photos")
  suspend fun fetchImages(): List<UnsplashItem>

  @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
  @GET("search/photos")
  suspend fun search(@Query("query") query: String): SearchResult
}