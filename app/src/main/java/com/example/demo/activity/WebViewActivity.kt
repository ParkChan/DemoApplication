package com.example.demo.activity

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityWebviewBinding

/**
 * chrome://inspect를 통한 웹뷰 디버깅 테스트
 */
class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wvTest.settings.apply {
            javaScriptEnabled = true
            allowContentAccess = true
            allowFileAccess = true
        }
        WebView.setWebContentsDebuggingEnabled(true)
        binding.wvTest.webChromeClient = WebChromeClient()
        binding.wvTest.loadUrl("https://m.daum.net/?nil_top=mobile")
    }
}