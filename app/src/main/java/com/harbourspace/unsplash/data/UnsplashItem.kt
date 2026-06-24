package com.harbourspace.unsplash.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "images")
data class UnsplashItem(
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    val current_user_collections: List<CurrentUserCollection?>?,
    val description: String?,
    val height: Int?,
    @PrimaryKey val id: String,
    val likes: Int?,
    val downloads: Int?,
    val views: Int?,
    val exif: Exif?,
    val tags: List<Tag>?,
    val links: Links?,
    val updated_at: String?,
    val urls: Urls?,
    val user: User?,
    val width: Int?
): Parcelable
