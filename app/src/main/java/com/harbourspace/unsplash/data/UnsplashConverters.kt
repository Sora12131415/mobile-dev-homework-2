package com.harbourspace.unsplash.data

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class UnsplashConverters {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromCurrentUserCollectionList(value: List<CurrentUserCollection?>?): String? {
        val type = Types.newParameterizedType(List::class.java, CurrentUserCollection::class.java)
        return moshi.adapter<List<CurrentUserCollection?>>(type).toJson(value)
    }

    @TypeConverter
    fun toCurrentUserCollectionList(value: String?): List<CurrentUserCollection?>? {
        val type = Types.newParameterizedType(List::class.java, CurrentUserCollection::class.java)
        return value?.let { moshi.adapter<List<CurrentUserCollection?>>(type).fromJson(it) }
    }

    @TypeConverter
    fun fromExif(value: Exif?): String? {
        return moshi.adapter(Exif::class.java).toJson(value)
    }

    @TypeConverter
    fun toExif(value: String?): Exif? {
        return value?.let { moshi.adapter(Exif::class.java).fromJson(it) }
    }

    @TypeConverter
    fun fromTagList(value: List<Tag>?): String? {
        val type = Types.newParameterizedType(List::class.java, Tag::class.java)
        return moshi.adapter<List<Tag>>(type).toJson(value)
    }

    @TypeConverter
    fun toTagList(value: String?): List<Tag>? {
        val type = Types.newParameterizedType(List::class.java, Tag::class.java)
        return value?.let { moshi.adapter<List<Tag>>(type).fromJson(it) }
    }

    @TypeConverter
    fun fromLinks(value: Links?): String? {
        return moshi.adapter(Links::class.java).toJson(value)
    }

    @TypeConverter
    fun toLinks(value: String?): Links? {
        return value?.let { moshi.adapter(Links::class.java).fromJson(it) }
    }

    @TypeConverter
    fun fromUrls(value: Urls?): String? {
        return moshi.adapter(Urls::class.java).toJson(value)
    }

    @TypeConverter
    fun toUrls(value: String?): Urls? {
        return value?.let { moshi.adapter(Urls::class.java).fromJson(it) }
    }

    @TypeConverter
    fun fromUser(value: User?): String? {
        return moshi.adapter(User::class.java).toJson(value)
    }

    @TypeConverter
    fun toUser(value: String?): User? {
        return value?.let { moshi.adapter(User::class.java).fromJson(it) }
    }
}
