package com.tuna.cinergy.business.domain.model.bookModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookInfo(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: com.tuna.cinergy.business.domain.model.bookModel.VolumeInfoData,
): Parcelable

@Parcelize
data class VolumeInfoData(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int,
    val printType: String,
    val categories: List<String>?,
    val averageRating: Int,
    val imageLinks: com.tuna.cinergy.business.domain.model.bookModel.ImageLinksData?,
    val previewLink:String?,
    val infoLink:String?,
    val canonicalVolumeLink:String?
): Parcelable

@Parcelize
data class ImageLinksData(
    val smallThumbnail: String?,
    val thumbnail: String?
): Parcelable
