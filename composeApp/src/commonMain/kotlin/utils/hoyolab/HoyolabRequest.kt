package utils.hoyolab

import getLocalHttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.BrowserUserAgent
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import utils.annotation.DoItLater
import utils.errorLogExport

/**
 * Refer from Stargazer 2 (rn-branch)
 * https://medium.com/jastzeonic/ktor-client-%E9%82%A3%E4%B8%80%E5%85%A9%E4%BB%B6%E4%BA%8B%E6%83%85-af9a06e6f070
 */

class HoyolabRequest(
    val platform: PLATFORM = PLATFORM.HOYOLAB,
    val cookieStr: String?
) {
    enum class Method { GET, POST }
    enum class DsType { NONE, V1, V2 }
    enum class PLATFORM { HOYOLAB, MIYOUSHE }

    @DoItLater("getDeviceFP, check Miyoushe's status")
    fun send(
        url: String,
        dsType: DsType = DsType.V1,
        method: Method = Method.GET,
        body: String? = null
    ) : HoyolabResponse {
        val client = getLocalHttpClient {
            install(HttpTimeout){
                requestTimeoutMillis = 3000
            }
            install(ContentNegotiation){
                json()
            }


            expectSuccess = true

            //BrowserUserAgent()
            defaultRequest {
                url(url)

                headers {
                    append(HttpHeaders.Origin, if(platform == PLATFORM.MIYOUSHE) "https://webstatic.mihoyo.com" else "https://act.hoyolab.com")
                    append(HttpHeaders.Referrer, if(platform == PLATFORM.MIYOUSHE) "https://webstatic.mihoyo.com" else "https://webstatic-sea.hoyolab.com")
                    append(HttpHeaders.Host, if(platform == PLATFORM.MIYOUSHE){"api-takumi-record.mihoyo.com"} else "bbs-api-os.hoyolab.com")
                    append("ds", if((platform == PLATFORM.MIYOUSHE || dsType == DsType.V2) && body != null){genDSv2(body, url.split("?")[1])} else genDSv1())
                    if(cookieStr != null){append("cookie",cookieStr)}
                    append("x-rpc-client_type", "5")
                    append("x-rpc-app_version", if(platform == PLATFORM.MIYOUSHE) "2.65.2" else "1.5.0")
                    append("X-Requested-With", if(platform == PLATFORM.MIYOUSHE) "com.mihoyo.hyperion" else "com.mihoyo.hoyolab")
                }
            }
        }

        try {
            return runBlocking {
                val response: HttpResponse = when(method){
                    Method.GET -> client.get(url)
                    Method.POST -> client.post(url){
                        if(body != null) setBody(Json.parseToJsonElement(body))
                    }
                }

                //Check whether it is having any errors
                if (!arrayListOf(200,201).contains(response.status.value)){
                    errorLogExport("HoyolabRequest", "send(url = ${url}, body = ${body})",Exception("HTTP Error Code ${response.status.value} : ${response.status.description}"))
                    return@runBlocking HoyolabResponse(
                        -9800,
                        "HTTP Error Code ${response.status.value} : ${response.status.description}",
                        Json.parseToJsonElement("{}")
                    )

                }else{
                    return@runBlocking response.body<HoyolabResponse>()
                }
            }

        }catch (e : UnresolvedAddressException){
            //Cannot find the Address, maybe bcz of u are offline
            //errorLogExport("HoyolabRequest", "send(url = ${url}, body = ${body})",e)
            e.printStackTrace()
        }catch (e : Exception){
            // All response
            errorLogExport("HoyolabRequest", "send(url = ${url}, body = ${body})",e)
        }

        return HoyolabResponse(-9999,"NOPE", Json.parseToJsonElement("{}"))
    }

    fun getPlainTxt(
        url: String
    ) : HoyolabResponse {
        val client = getLocalHttpClient {
            install(HttpTimeout){
                requestTimeoutMillis = 4000
            }
            install(ContentNegotiation){
                json()
            }

            expectSuccess = true
            BrowserUserAgent()
        }

        try {
            return runBlocking {
                return@runBlocking withTimeout(4000) {
                    val response: HttpResponse = client.get(url)

                    //Check whether it is having any errors
                    if (!arrayListOf(200, 201).contains(response.status.value)) {
                        errorLogExport(
                            "HoyolabRequest",
                            "send(url = ${url}",
                            Exception("HTTP Error Code ${response.status.value} : ${response.status.description}")
                        )
                        return@withTimeout HoyolabResponse(
                            -9800,
                            "HTTP Error Code ${response.status.value} : ${response.status.description}",
                            Json.parseToJsonElement("{}")
                        )

                    } else {
                        return@withTimeout response.body<HoyolabResponse>()
                    }
                }
            }

        }catch (e : UnresolvedAddressException){
            //Cannot find the Address, maybe bcz of u are offline
            //errorLogExport("HoyolabRequest", "send(url = ${url}, body = ${body})",e)
            e.printStackTrace()
        }catch (e : Exception){
            // All response
            errorLogExport("HoyolabRequest", "getPlainTxt(url = ${url})",e)
        }
        return HoyolabResponse(-9999,"NOPE", Json.parseToJsonElement("{}"))

    }
}


