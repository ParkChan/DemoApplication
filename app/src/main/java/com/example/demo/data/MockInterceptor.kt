package com.example.demo.data

import android.content.Context
import com.example.demo.MyApplication
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import javax.inject.Inject

class MockInterceptor @Inject constructor(): Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri()

        return if (uri.isMockApi()) {
            val responseString = readAssetFile(MyApplication.applicationContext(), uri)
            mockResponse(chain, responseString)
        } else {
            chain.proceed(chain.request())
        }
    }

    companion object {
        private const val MOCK_PATH = "/mock_api"
        private fun URI.isMockApi() = this.toString().contains(MOCK_PATH)
        /**
         * asset의 파일을 읽어온다.
         */
        fun readAssetFile(context: Context, uri: URI): String {
            val responseStringBuilder = StringBuilder()
            context.assets.open(uri.path.removePrefix("/")).bufferedReader().use {
                responseStringBuilder.append(it.readText())
            }
            return responseStringBuilder.toString()
        }
        private fun mockResponse(chain: Interceptor.Chain, response:String) = Response.Builder()
            .code(200)
            .message("success")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    response.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}



