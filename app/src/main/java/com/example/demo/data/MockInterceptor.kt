package com.example.demo.data

import android.content.Context
import androidx.test.internal.util.LogUtil
import com.example.demo.MyApplication
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import javax.inject.Inject

class MockInterceptor @Inject constructor(): Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri()

        Timber.d("CHAN >>> uri = $uri")

        if (uri.toString().contains(MOCK_PATH)) {
            val responseString = readAssetFile(MyApplication.applicationContext(), uri)
            return Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            return chain.proceed(chain.request())
        }
    }

    companion object {
        const val MOCK_PATH = "/mock_api"
    }
}

/**
 * asset의 파일을 읽어온다.
 */
fun readAssetFile(context: Context, uri: URI): String {
    val responseStringBuilder = StringBuilder()
    context.assets.open(uri.path.removePrefix("/")).use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            while (true) {
                val line = reader.readLine()
                line?.let {
                    responseStringBuilder.append(it).append("\n")
                } ?: break
            }
        }
    }
    return responseStringBuilder.toString()
}

