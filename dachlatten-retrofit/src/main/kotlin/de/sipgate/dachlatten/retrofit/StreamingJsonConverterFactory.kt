package de.sipgate.dachlatten.retrofit

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.serializer
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.lang.reflect.Type

@JvmName("convert")
@ExperimentalSerializationApi
public fun Json.asConverterFactory(contentType: MediaType): Converter.Factory =
    object : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit,
        ) = Converter<ResponseBody, Any> { value ->
            this@asConverterFactory.decodeFromStream(
                this@asConverterFactory.serializersModule.serializer(type),
                value.byteStream(),
            )
        }

        override fun requestBodyConverter(
            type: Type,
            parameterAnnotations: Array<out Annotation>,
            methodAnnotations: Array<out Annotation>,
            retrofit: Retrofit,
        ) = Converter<Any, RequestBody> { value ->
            val stream = ByteArrayOutputStream()
            this@asConverterFactory.encodeToStream(
                this@asConverterFactory.serializersModule.serializer(type),
                value,
                stream,
            )
            stream.toByteArray().toRequestBody(
                contentType,
                0,
                stream.size(),
            )
        }
    }
