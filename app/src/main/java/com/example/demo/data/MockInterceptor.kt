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

class MockInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri()

        if (uri.toString().endsWith(MOCK_PATH)) {
            if (isFileInAssets(
                    MyApplication.applicationContext(),
                    uri
                )
            ) {
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
        } else {
            return chain.proceed(chain.request())
        }
    }

    companion object {
        const val MOCK_PATH = "/mock_api"
    }
}

/**
 * 경로에 파일이 있는지 확인
 */
fun isFileInAssets(context: Context, uri: URI): Boolean {
    return try {
        val path = uri.path.substringBeforeLast("/", "")
        val fileName = uri.path.substringAfterLast("/")
        val assets = context.assets.list(path)
        if (assets != null) {
            fileName in assets
        } else {
            false
        }
    } catch (e: IOException) {
        false
    }
}

/**
 * asset의 파일을 읽어온다.
 */
fun readAssetFile(context: Context, uri: URI): String {
    val responseStringBuilder = StringBuilder()
    context.assets.open(uri.path).use { inputStream ->
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

