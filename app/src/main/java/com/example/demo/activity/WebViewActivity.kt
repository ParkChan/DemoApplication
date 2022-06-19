package com.example.demo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF
import androidx.webkit.WebSettingsCompat.FORCE_DARK_ON
import com.example.demo.BuildConfig
import com.example.demo.databinding.ActivityWebviewBinding
import com.example.demo.util.ThemeType
import com.example.demo.util.ThemeUtil
import timber.log.Timber

/**
 * chrome://inspect를 통한 웹뷰 디버깅 테스트
 *
 * [웹뷰 다크테마 테스트](https://developer.android.com/guide/webapps/dark-theme)
 */
class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    @SuppressLint("RequiresFeature")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.wvTest.settings.apply {
            javaScriptEnabled = true
            allowContentAccess = true
            allowFileAccess = true

            if (ThemeUtil.isNightMode()) {
                Timber.d("WebViewActivity webView 다크테마")
                WebSettingsCompat.setForceDark(this, FORCE_DARK_ON)
            } else {
                Timber.d("WebViewActivity webView 라이트테마")
                WebSettingsCompat.setForceDark(this, FORCE_DARK_OFF)
            }
        }

        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)

        binding.wvTest.webViewClient = WebViewClient()
        binding.wvTest.webChromeClient = WebChromeClient()
        binding.wvTest.loadUrl("https://m.twitter.com/bitcoin")

        binding.btnLight.setOnClickListener {
            ThemeUtil.applyTheme(ThemeType.LIGHT_MODE)
        }
        binding.btnDark.setOnClickListener {
            ThemeUtil.applyTheme(ThemeType.DARK_MODE)
        }
    }
}