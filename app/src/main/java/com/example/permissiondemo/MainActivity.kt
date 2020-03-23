package com.example.permissiondemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ComponentActivity() {

    lateinit var observer: PermissionLifeCycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observer =
            PermissionLifeCycleObserver(activityResultRegistry, this) { isGranted, permission ->
                when {
                    !isGranted -> {
                    } // Permission denied
                    isGranted && permission == android.Manifest.permission.CAMERA -> {
                    }
                }
            }
        lifecycle.addObserver(observer)

        button.setOnClickListener {
            observer.checkPermission(android.Manifest.permission.CAMERA)
        }
    }
}
