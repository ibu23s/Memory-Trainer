package de.fra_uas.fb2.mobiledevices.memorytrainer
import android.os.Parcel
import android.os.Parcelable

data class IntPair(val first: Int, val second: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(first)
        parcel.writeInt(second)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IntPair> {
        override fun createFromParcel(parcel: Parcel): IntPair {
            return IntPair(parcel)
        }

        override fun newArray(size: Int): Array<IntPair?> {
            return arrayOfNulls(size)
        }
    }
}
