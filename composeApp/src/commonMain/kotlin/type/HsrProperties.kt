package type

import kotlinx.serialization.Serializable
import utils.hoyolab.AttributeExchange


@Serializable
data class HsrProperties(
    var attributeExchange: AttributeExchange = AttributeExchange.ATTREX_UNKNOWN,
    var valueFinal: Float = 0f,
    var valueBase: Float = 0f,
    var valueAdd: Float = 0f,
    var times: Int = 0,
){
    companion object{
        fun turnStrToValue(str: String) : Float {
            return if(str.contains("%")) {
                str.replace("%","").toFloat() / 100
            } else {
                str.toFloat()
            }
        }
    }

}