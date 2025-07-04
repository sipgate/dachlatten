package de.sipgate.dachlatten.retrofit

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.junit5.StartStop
import okhttp3.MediaType.Companion.toMediaType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import kotlin.test.assertEquals

@OptIn(ExperimentalSerializationApi::class)
class KotlinSerializationConverterFactoryStringTest {

    @StartStop
    private val server = MockWebServer()

    private lateinit var service: Service

    interface Service {
        @GET("/")
        fun deserialize(): Call<User>
        @POST("/")
        fun serialize(@Body user: User): Call<Void?>
    }

    @Serializable
    data class User(val name: String)

    @BeforeEach
    fun setUp() {
        val contentType = "application/json; charset=utf-8".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        service = retrofit.create(Service::class.java)
    }

    @Test
    fun deserialize() {
        server.enqueue(MockResponse().newBuilder().body("""{"name":"Bob"}""").build())
        val user = service.deserialize().execute().body()!!
        assertEquals(User("Bob"), user)
    }

    @Test
    fun serialize() {
        server.enqueue(MockResponse())
        service.serialize(User("Bob")).execute()
        val request = server.takeRequest()
        assertEquals("""{"name":"Bob"}""", request.body?.string(Charsets.UTF_8))
        assertEquals("application/json; charset=utf-8", request.headers["Content-Type"])
    }
}
