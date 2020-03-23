package com.example.permissiondemo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class PermissionLifeCycleObserver(
    private val registry: ActivityResultRegistry,
    private val activity: Activity,
    private val callOnPermissionGranted: (Boolean, String) -> Unit
) : LifecycleObserver {
    private lateinit var permission: ActivityResultLauncher<String>
    private lateinit var key: String

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
        permission = registry.registerActivityResultCallback(
            key,
            owner,
            object : ActivityResultContracts.RequestPermission() {
                override fun createIntent(input: String): Intent {
                    if (activity.shouldShowRequestPermissionRationale(input)) {
                        // case 2
                    } else {
                        // case 1 and 3
                    }
                    return super.createIntent(input)
                }
            },
            ActivityResultCallback<Boolean> { callOnPermissionGranted(it, key) }
        )
    }

    fun checkPermission(per: String) {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, per)) {
            callOnPermissionGranted(true, key)
            return
        }
        key = per
        permission.launch(per)
    }
}