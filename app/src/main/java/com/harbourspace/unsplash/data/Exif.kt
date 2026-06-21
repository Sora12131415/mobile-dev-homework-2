package com.harbourspace.unsplash.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exif(
    val make: String?,
    val model: String?,
    val exposure_time: String?,
    val aperture: String?,
    val focal_length: String?,
    val iso: Int?
): Parcelable