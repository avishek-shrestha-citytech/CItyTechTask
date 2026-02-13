package com.example.basicapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class GithubUser(

    @PrimaryKey 
    val id: Int,

    val login: String,
    val avatarurl: String,
    val htmlurl: String,
    val type: String
): Parcelable {

    constructor(parcel: Parcel):this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun describeContents(): Int=0

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(id)
        p0.writeString(login)
        p0.writeString(avatarurl)
        p0.writeString(htmlurl)
        p0.writeString(type)
    }

    companion object CREATOR : Parcelable.Creator<GithubUser> {
        override fun createFromParcel(parcel: Parcel): GithubUser = GithubUser(parcel)
        override fun newArray(size: Int): Array<GithubUser?> = arrayOfNulls(size)
    }
}
