package net.vizja.currencystock.http

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.client.RestTemplate

@Validated
@Configuration
@EnableConfigurationProperties(OkHttpSettings::class)
class RestTemplateAutoConfiguration(private val okHttpSettings: OkHttpSettings) {

    @Bean
    @Primary
    fun createRestTemplate(builder: RestTemplateBuilder, mapper: ObjectMapper): RestTemplate {
        return builder
                .additionalCustomizers(RestTemplateCustomizer { restTemplate ->
                    restTemplate.requestFactory = OkHttp3ClientHttpRequestFactory(createOkHttpClient())
                })
                .build()
    }

    @Bean
    fun createOkHttpClient(): OkHttpClient {
        logger.info("Setting OkHttp log level: {}", okHttpSettings.logLevel)
        val logging: Interceptor = HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message: String? -> logger.info("http: {}", message) }
        )
        return OkHttpClient().newBuilder()
                .followRedirects(okHttpSettings.followRedirects)
                .followSslRedirects(okHttpSettings.followRedirects)
                .addInterceptor(logging)
                .build()
    }

    private companion object : KLogging()
}