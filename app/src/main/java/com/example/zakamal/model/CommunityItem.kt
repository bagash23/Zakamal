package com.example.zakamal.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CommunityItem (
    val communityTitle: String,
    val communityDescription: String,
    val communityId: String
): Parcelable