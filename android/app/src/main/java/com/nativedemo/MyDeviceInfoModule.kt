package com.nativedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.modules.core.DeviceEventManagerModule

class MyDeviceInfoModule(
    reactContext: ReactApplicationContext
) : NativeMyDeviceInfoSpec(reactContext) {

    companion object {
        const val NAME = "MyDeviceInfo"
        const val BATTERY_CHANGED = "batteryChanged"
    }

    private val batteryReceiver = object : BroadcastReceiver() {

        override fun onReceive(
            context: Context?,
            intent: Intent?
        ) {

            if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {

                val level = intent.getIntExtra(
                    BatteryManager.EXTRA_LEVEL,
                    -1
                )

                val scale = intent.getIntExtra(
                    BatteryManager.EXTRA_SCALE,
                    -1
                )

                if (level >= 0 && scale > 0) {

                    val batteryLevel =
                        (level * 100) / scale

                    sendBatteryEvent(batteryLevel)
                }
            }
        }
    }

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

        return batteryManager
            .getIntProperty(
                BatteryManager.BATTERY_PROPERTY_CAPACITY
            )
            .toDouble()
    }

    private fun sendBatteryEvent(
        batteryLevel: Int
    ) {

        reactApplicationContext
            .getJSModule(
                DeviceEventManagerModule.RCTDeviceEventEmitter::class.java
            )
            .emit(
                BATTERY_CHANGED,
                batteryLevel
            )
    }

    override fun initialize() {
        super.initialize()

        val filter = IntentFilter(
            Intent.ACTION_BATTERY_CHANGED
        )

        reactApplicationContext.registerReceiver(
            batteryReceiver,
            filter
        )
    }

    override fun invalidate() {
        reactApplicationContext.unregisterReceiver(
            batteryReceiver
        )

        super.invalidate()
    }
}