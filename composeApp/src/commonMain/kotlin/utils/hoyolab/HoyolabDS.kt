package utils.hoyolab

import kotlinx.datetime.Clock
import okio.Buffer
import okio.ByteString.Companion.encodeUtf8
import okio.HashingSink
import okio.blackholeSink
import kotlin.math.floor
import kotlin.random.Random

fun genDSv1(): String {
    val salt = "6s25p5ox5y14umn1p61aqyyvbvvl3lrt"
    val time = Clock.System.now().toEpochMilliseconds()/1000
    var random = ""
    val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    for (x in 0..5) {
        val randomIndex = floor(Random.nextDouble() * characters.length).toInt()
        val randomChar = characters[randomIndex]
        random += randomChar
    }
    val hash: String = md5HexTotal("salt=$salt&t=$time&r=$random")
    return "$time,$random,$hash"
}

/**
 *
 * E.g. body = "{\"role\": \"108289390\"}";
 * E.g. queryFromURL = "uid=16299869";
 * @return
 */
fun genDSv2(body: String, queryFromURL: String): String {
    val salt = "xV8v4Qu54lUKrEYFZkJhB8cuOh9Asafs"
    val time = Clock.System.now().toEpochMilliseconds()/1000
    val preQuery = queryFromURL.split("&".toRegex()).sorted() as ArrayList<String>
    var query = ""
    for (index in preQuery.indices) {
        preQuery[index] += if (index + 1 < preQuery.size) "&" else ""
        query += preQuery[index]
    }
    var random = floor(Random.nextDouble() * 100001 + 100000).toInt()
    if (random == 100000) {
        random = 642367
    }

    val main : String = md5HexTotal("salt=$salt&t=$time&r=$random&b=$body&q=$query")
    return "$time,$random,$main"
}

fun md5HexTotal(data: String): String {
    // Create a buffer from the input string
    val buffer = Buffer().write(data.encodeUtf8())

    // Create an MD5 hashing sink
    val hashingSink = HashingSink.md5(blackholeSink())

    // Write the buffer to the hashing sink
    buffer.readAll(hashingSink)

    // Get the MD5 hash as a ByteString
    val md5Hash = hashingSink.hash

    // Return the MD5 hash as a hex string
    return md5Hash.hex()
}
