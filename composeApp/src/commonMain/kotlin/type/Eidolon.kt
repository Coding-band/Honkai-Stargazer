package types

import androidx.annotation.IntRange

data class Eidolon(
    @IntRange(1,6) var eidolonIndex: Int,
    var name: String,
    var desc: String,
    var params: ArrayList<Float>,
    var eidolonImgName: String,
    var soulIconName: String,
)