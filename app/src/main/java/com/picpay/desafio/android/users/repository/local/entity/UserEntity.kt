package com.picpay.desafio.android.users.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.users.model.User
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "User")
@Parcelize
data class UserEntity(
    @PrimaryKey
    override val id: Int,
    override val img: String,
    override val name: String,
    override val username: String
) : User