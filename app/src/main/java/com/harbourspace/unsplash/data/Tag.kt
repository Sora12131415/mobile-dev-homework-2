package com.harbourspace.unsplash.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    val title: String?,
    val type: String?
): Parcelable