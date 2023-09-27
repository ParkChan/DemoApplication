package com.example.demo.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF
import androidx.webkit.WebSettingsCompat.FORCE_DARK_ON
import com.example.demo.BuildConfig
import com.example.demo.databinding.ActivityWebviewBinding
import com.example.demo.databinding.FragmentWebviewBinding
import com.example.demo.ui.fragment.base.BaseFragment
import com.example.demo.ui.util.ThemeType
import com.example.demo.ui.util.ThemeUtil
import timber.log.Timber


/**
 * chrome://inspect를 통한 웹뷰 디버깅 테스트
 * [웹뷰 다크테마 테스트](https://developer.android.com/guide/webapps/dark-theme)
 * 웹뷰 입력상자 > 키보드 올라왔을시 화면 리사이즈 테스트
 *
 */
class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding
    private val pagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this)
    }

    @SuppressLint("RequiresFeature")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpSample.adapter = pagerAdapter
        //layoutNoLimits()
    }

    /**
     * 상단에 상태 표시줄이 있는 전체 화면 활동 만들기
     * 해당 코드 사용시 adjustResize 동작이 안되는것으로 확인됨
     */
    private fun layoutNoLimits() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}

internal class ViewPagerAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return WebViewFragment.newInstance()
    }

    companion object {
        private const val FRAGMENT_COUNT = 1
    }

}

internal class WebViewFragment :
    BaseFragment<FragmentWebviewBinding>(FragmentWebviewBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        // 웹뷰 디버깅을 위한 설정
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)

        binding.wvTest.webViewClient = WebViewClient()
        binding.wvTest.webChromeClient = WebChromeClient()
        binding.wvTest.addJavascriptInterface(WebAppInterface(), JavaScriptInterfaceName)


        binding.wvTest.loadUrl(WebUrlLocal)

        binding.btnLight.setOnClickListener {
            ThemeUtil.applyTheme(ThemeType.LIGHT_MODE)
        }

        binding.btnDark.setOnClickListener {
            ThemeUtil.applyTheme(ThemeType.DARK_MODE)
        }

        // 직접 Script Call 수행히는 예제
        binding.btnScriptCall1.setOnClickListener {
            binding.wvTest.evaluateJavascript(
                "window.DemoApplication.showToast('ParkChan')", null
            )
        }

        // WebView -> localStorage를 삭제하는 예제
        binding.btnScriptCall2.setOnClickListener {

            val keyName = "examKey"

            // 스크립트 함수를 정의하고 실행시켜서 삭제하는 예시
            binding.wvTest.evaluateJavascript(
                "onClearLocalStorage = function() { " +
                        "const data = window.localStorage; " +
                        "const localStorageKeys = Object.keys({ ...data }); " +
                        "const examKeys = localStorageKeys.filter((key) => key.includes('$keyName') ); " +
                        "examKeys.forEach((key) => { window.localStorage.removeItem(key); }); }; " +
                        "onClearLocalStorage()",
                null
            )

            // localStorage의 모든 키를 포함하는 JSON 문자열 가져오기
            binding.wvTest.evaluateJavascript("JSON.stringify(Object.keys(window.localStorage));") { value ->
                // todo
            }

            // value는 특정 키 값에 대한 localStorage의 키값 가져오기
            binding.wvTest.evaluateJavascript("window.localStorage.getItem('$keyName');") {
                    value ->
                println(value)
                // 삭제 처리
                removeItemFromLocalStorage(binding.wvTest, value)
            }

        }
    }

    // 한번에 하나의 키값에 포함되는 로컬스토리지 항목을 삭제
    private fun removeItemFromLocalStorage(webView: WebView, key: String) {
        webView.evaluateJavascript("window.localStorage.removeItem('$key');", null)
    }


    inner class WebAppInterface {

        //Script 전달 받기 테스트
        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val JavaScriptInterfaceName: String = "DemoApplication"
        private const val WebUrlLocal = "file:///android_asset/index.html"
        fun newInstance(): WebViewFragment = WebViewFragment()
    }
}