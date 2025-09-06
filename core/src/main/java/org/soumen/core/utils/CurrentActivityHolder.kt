package org.soumen.core.utils

import androidx.activity.ComponentActivity
import java.lang.ref.WeakReference

object CurrentActivityHolder {
    private var activityRef: WeakReference<ComponentActivity>? = null

    var activity: ComponentActivity?
        get() = activityRef?.get()
        set(value) {
            activityRef = if (value != null) WeakReference(value) else null
        }
}