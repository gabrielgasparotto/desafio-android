package com.picpay.desafio.android.users.repository.remote.response

import com.google.gson.annotations.SerializedName
import com.picpay.desafio.android.users.model.User
import com.picpay.desafio.android.users.repository.local.entity.UserEntity
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Parcelize
data class UserResponse(
    @SerializedName("img") override val img: String,
    @SerializedName("name") override val name: String,
    @SerializedName("id") override val id: Int,
    @SerializedName("username") override val username: String
) : User

suspend fun List<UserResponse>.mapResponseEntity(): List<UserEntity> {
    val list: List<User> = this
    return withContext(Dispatchers.Default) {
        list.map {
            UserEntity(
                it.id,
                it.img,
                it.name,
                it.username
            )
        }
    }
}