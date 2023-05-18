package com.example.innobuzzapp.services

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class MyAppService : AccessibilityService() {
    companion object {
        const val SERVICE_TAG = "MyAppService"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.packageName == "com.whatsapp" && event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Toast.makeText(this, "WhatsApp Launched", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInterrupt() {
        Log.e(SERVICE_TAG, "onInterrupt: ")
    }
}