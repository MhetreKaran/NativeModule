package com.nativedemo

import android.content.Context
import android.os.BatteryManager
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

    override fun getBatteryLevel(): Double {
        val batteryManager =
            reactApplicationContext.getSystemService(
                Context.BATTERY_SERVICE
            ) as BatteryManager

        return batteryManager.getIntProperty(
            BatteryManager.BATTERY_PROPERTY_CAPACITY
        ).toDouble()
    }

    companion object {
        const val NAME = "MyDeviceInfo"
    }
}