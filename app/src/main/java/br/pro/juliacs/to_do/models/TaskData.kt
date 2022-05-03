package br.pro.juliacs.to_do.models

import android.os.Parcel
import android.os.Parcelable

class TaskData (
    val description: String,
    val isUrgent: Boolean,
    val isDone: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readByte() > 0,
        parcel.readByte() > 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeByte(if(isUrgent) 1 else 0)
        parcel.writeByte(if(isDone) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskData> {
        override fun createFromParcel(parcel: Parcel): TaskData {
            return TaskData(parcel)
        }

        override fun newArray(size: Int): Array<TaskData?> {
            return arrayOfNulls(size)
        }
    }
}