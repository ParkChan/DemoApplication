package com.example.demo.data.response

import com.example.demo.domain.entity.response.BookmarkEntity
import com.google.gson.annotations.SerializedName

data class BookmarkResponse(
    @SerializedName("bookmarkList")
    val bookmarkList: List<BookmarkItem>
)

data class BookmarkItem(
    @SerializedName("seq")
    val seq: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("price")
    val price: String = ""
) : MapToDomain<BookmarkEntity> {
    override fun mapToDomain(): BookmarkEntity {
        return BookmarkEntity(seq, thumbnail, title, price)
    }
}
