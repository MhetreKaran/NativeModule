package com.nativedemo

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class BatteryModule(
    private val reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "BatteryModule"
    }

    @ReactMethod
    fun getBatteryLevel(promise: Promise) {

        try {
            val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

            val batteryStatus =
                reactContext.registerReceiver(null, intentFilter)

            val level = batteryStatus?.getIntExtra(
                BatteryManager.EXTRA_LEVEL,
                -1
            ) ?: -1

            val scale = batteryStatus?.getIntExtra(
                BatteryManager.EXTRA_SCALE,
                -1
            ) ?: -1

            if (level == -1 || scale == -1) {
                promise.reject(
                    "BATTERY_ERROR",
                    "Unable to get battery level"
                )
                return
            }

            val batteryPercentage =
                (level * 100) / scale

            promise.resolve(batteryPercentage)

        } catch (e: Exception) {

            promise.reject(
                "BATTERY_ERROR",
                e.message,
                e
            )
        }
    }
}