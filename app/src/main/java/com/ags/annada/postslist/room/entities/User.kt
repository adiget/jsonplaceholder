package com.ags.annada.postslist.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gabriel.jsonplaceholder.data.local.entity.user.Address
import com.example.gabriel.jsonplaceholder.data.local.entity.user.Company

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,

    val username: String,

    val email: String,

    @Embedded
    val address: Address?,

    val phone: String,

    val website: String,

    @Embedded
    val company: Company?
)