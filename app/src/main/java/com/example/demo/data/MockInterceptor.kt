package com.example.demo.data

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class MockInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()

        // 특정 URL 경로에 대한 요청을 가로챕니다.
        if (uri.endsWith(MOCK_PATH)) {
            val responseString = // 로컬 JSON 파일에서 데이터를 읽어옵니다.
                return Response.Builder()
                    .code(200)
//                    .message(responseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
//                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
                    .addHeader("content-type", "application/json")
                    .build()
        } else {
            return chain.proceed(chain.request())
        }
    }
    companion object{
        const val MOCK_PATH = "/moack_api"
    }
}