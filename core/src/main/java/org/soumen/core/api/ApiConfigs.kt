package org.soumen.core.api

import kotlin.coroutines.EmptyCoroutineContext.get
import org.soumen.core.BuildConfig

object ApiConfigs {
    val apiKey : String
        get() = BuildConfig.API_KEY

    val baseURL : String
        get() = BuildConfig.API_URL
}