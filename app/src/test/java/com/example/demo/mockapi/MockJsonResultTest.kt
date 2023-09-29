package com.example.demo.mockapi

import org.junit.Test
import java.io.IOException
import java.net.URI

class MockJsonResultTest {
    @Test
    fun `Mock API 를 통한 Json 객체 리턴 테스트`() {

    }

    //    private String upCaseFirstLetter(String str) {
    //        return str.substring(0, 1).toUpperCase() + str.substring(1);
    //    }
    @Throws(IOException::class)
    private fun getFirstFileNameExist(inputFileNames: List<String>, uri: URI): String? {
        var mockDataPath = uri.host + uri.path
        mockDataPath = mockDataPath.substring(TEST_URL.length, mockDataPath.lastIndexOf('/'))
        println("Scan files in data path : $mockDataPath")
        println("Scan files in inputFileNames : $inputFileNames")

        //List all files in folder
        val files: Array<String> = context.getAssets().list(mockDataPath)
        for (fileName in inputFileNames) {
            if (fileName != null) {
                for (file in files) {
                    if (fileName == file) {
                        Logger.d("Scan files return fileName : $fileName")
                        return fileName
                    }
                }
            }
        }
        return null
    }
}