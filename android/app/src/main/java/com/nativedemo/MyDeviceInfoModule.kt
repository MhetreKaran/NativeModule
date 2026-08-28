package com.nativedemo

import android.os.Build
import com.facebook.react.bridge.ReactApplicationContext

class MyDeviceInfoModule(
    reactContext: ReactApplicationContext
) : NativeMyDeviceInfoSpec(reactContext) {

    override fun getName(): String {
        return NAME
    }

    override fun getDeviceModel(): String {
        return Build.MODEL
    }

    companion object {
        const val NAME = "MyDeviceInfo"
    }
}