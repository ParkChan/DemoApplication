package com.example.demo.ui.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.demo.databinding.FragmentNotificationSettingsBinding
import com.google.android.material.snackbar.Snackbar

/**
 * https://developer.android.com/about/versions/13/changes/notification-permission?hl=ko
 */
class NotificationSettingsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationSettingsBinding
    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) {
        when(it) {
            true -> {
                Snackbar.make(binding.root, "알림권한을 허용함", Snackbar.LENGTH_LONG).show()
            }
            false -> {
                Snackbar.make(binding.root, "알림권한을 거절함", Snackbar.LENGTH_LONG).show()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationSettingsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNotificationSettings.setOnClickListener {
            openNotificationSettings()
        }

        requestNotificationPermission()
    }

    private fun openNotificationSettings() {

        context?.run {
            val intent = Intent().apply {
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    }

                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                        action = "android.settings.APP_NOTIFICATION_SETTINGS"
                        putExtra("app_package", packageName)
                        putExtra("app_uid", applicationInfo.uid)
                    }

                    else -> {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        addCategory(Intent.CATEGORY_DEFAULT)
                        data = Uri.parse("package:$packageName")
                    }
                }
            }
            startActivity(intent)
        }

    }

    override fun onResume() {
        checkNotificationStatus()
        super.onResume()
    }

    private fun checkNotificationStatus() {
        val notificationManager: NotificationManagerCompat =
            NotificationManagerCompat.from(requireContext())
        val isOpend = notificationManager.areNotificationsEnabled()
        if (isOpend) {
            binding.tvNotificationSettings.text = "알림권한 On"
        } else {
            binding.tvNotificationSettings.text = "알림권한 Off"
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is already granted
                // Do your work here
            } else {
                // Permission is not granted
                requestPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // Do your work here
        }
    }

}
