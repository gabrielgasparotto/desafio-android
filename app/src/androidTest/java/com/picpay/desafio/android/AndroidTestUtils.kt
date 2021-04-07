package com.picpay.desafio.android

import com.picpay.desafio.android.service.WebService
import io.mockk.every
import io.mockk.mockkObject
import okhttp3.mockwebserver.MockWebServer

object AndroidTestUtils {

    fun setupServerUrl(server: MockWebServer) {
        mockkObject(WebService)
        every { WebService.retrofit(WebService.URL_PICPAY) } returns WebService.retrofit(
            server.url("/").toString()
        )
    }
}