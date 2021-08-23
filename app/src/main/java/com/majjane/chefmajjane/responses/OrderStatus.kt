package com.majjane.chefmajjane.responses

import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderStatus(
    val id: Int,
    val name: String
): Parcelable