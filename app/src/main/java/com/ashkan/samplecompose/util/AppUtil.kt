package com.ashkan.samplecompose.util

import android.content.Context

fun Context.getAppVersionName(): String {
    return try {
        packageManager.getPackageInfo(packageName, 0).versionName ?: "Unknown"
    } catch (_: Exception) {
        "Unknown"
    }
}