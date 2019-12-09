package com.ags.annada.postslist.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class Post(
    @PrimaryKey @ColumnInfo(name = "id") var id: Long?,
    @ColumnInfo(name = "userId") var userId: Int,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "body") var body: String?
) {
    constructor() : this(null, 0, "", "")
}