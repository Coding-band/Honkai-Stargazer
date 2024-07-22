package types

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.errorLogExport
import utils.hoyolab.HoyolabAPI

@Serializable
data class EventItem(
    var ann_id: Int = 0,
    var title: String = "",
    var banner: String = "",
    var content: String = "",
    var start_time: String = "2024-07-22 16:46:00",
    var end_time: String = "2024-07-22 16:47:00",
    var end_unix: Long = 0L,
){
    companion object {
        val EventListInstance = EventItem().initEventList().sortedBy { it.end_unix }
    }
    fun initEventList() : ArrayList<EventItem>{
        val eventList = ArrayList<EventItem>()
        try {
            val eventListResponse = HoyolabAPI(cookies = "").getHsrEventList()
            val eventListContentResponse = HoyolabAPI(cookies = "").getHsrEventContent()

            val eventListJson = eventListResponse.data.jsonObject["list"]!!.jsonArray[0].jsonObject["list"]
            val eventPicListJson = eventListResponse.data.jsonObject["pic_list"]!!.jsonArray[0].jsonObject["type_list"]!!.jsonArray[0].jsonObject["list"]
            val eventContentJson = eventListContentResponse.data.jsonObject["list"]
            val eventContentPicJson = eventListContentResponse.data.jsonObject["pic_list"]

            if (eventListJson != null && !eventListJson.jsonArray.isEmpty()) {
                for (event in eventListJson.jsonArray.filter { it.jsonObject["banner"]!!.jsonPrimitive.contentOrNull != null }) {
                    val eventItem = EventItem(
                        ann_id = event.jsonObject["ann_id"]!!.jsonPrimitive.int,
                        title = event.jsonObject["title"]!!.jsonPrimitive.content,
                        banner = event.jsonObject["banner"]!!.jsonPrimitive.content,
                        start_time = event.jsonObject["start_time"]!!.jsonPrimitive.content,
                        end_time = event.jsonObject["end_time"]!!.jsonPrimitive.content,
                        content = eventContentJson!!.jsonArray.firstOrNull { it.jsonObject["ann_id"]!!.jsonPrimitive.int == event.jsonObject["ann_id"]!!.jsonPrimitive.int }?.jsonObject?.get(
                            "content"
                        )?.jsonPrimitive?.contentOrNull ?: "",
                        end_unix = LocalDateTime.parse(event.jsonObject["end_time"]!!.jsonPrimitive.content, LocalDateTime.Format { date(LocalDate.Formats.ISO); char(' '); time(LocalTime.Formats.ISO) }).toInstant(TimeZone.of("UTC+8")).toEpochMilliseconds()
                    )
                    eventList.add(eventItem)
                }
            }

            if (eventPicListJson != null && !eventPicListJson.jsonArray.isEmpty()) {
                for (event in eventPicListJson.jsonArray.filter { it.jsonObject["banner"]!!.jsonPrimitive.contentOrNull != null }) {
                    val eventItem = EventItem(
                        ann_id = event.jsonObject["ann_id"]!!.jsonPrimitive.int,
                        title = event.jsonObject["title"]!!.jsonPrimitive.content,
                        banner = if(event.jsonObject["img"]!!.jsonPrimitive.content == "") event.jsonObject["banner"]!!.jsonPrimitive.content else event.jsonObject["img"]!!.jsonPrimitive.content,
                        start_time = event.jsonObject["start_time"]!!.jsonPrimitive.content,
                        end_time = event.jsonObject["end_time"]!!.jsonPrimitive.content,
                        content = eventContentPicJson!!.jsonArray.firstOrNull { it.jsonObject["ann_id"]!!.jsonPrimitive.int == event.jsonObject["ann_id"]!!.jsonPrimitive.int }?.jsonObject?.get(
                            "content"
                        )?.jsonPrimitive?.contentOrNull ?: "",
                        end_unix = LocalDateTime.parse(event.jsonObject["end_time"]!!.jsonPrimitive.content, LocalDateTime.Format { date(LocalDate.Formats.ISO); char(' '); time(LocalTime.Formats.ISO) }).toInstant(TimeZone.of("UTC+8")).toEpochMilliseconds()
                    )
                    eventList.add(eventItem)
                }
            }

        }catch (e : Exception){
            errorLogExport("EventListPageScreen", "EventListPageScreen() -> Loading Event List", e)
        }
        return eventList
    }
}