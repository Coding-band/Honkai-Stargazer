package types

data class TraceTreeItem(
    val id : Int,
    val anchor : Int = -1,
    val type : Int = -1,
    val name : String,
    //val levelReq: Int,
    //val promotionReq: Int,
    val iconPath: String,
    val desc: String,
    val energy: Int = -1,
    val ultimateCost : Int = 0,
    val levelData: ArrayList<TracecTreeLevelData>? = null,
    val statusList: ArrayList<TracecTreeKeyStatus>? = null,
    val trigCost: ArrayList<TracecTreeCost>? = null,
)

data class TracecTreeKeyStatus(
    val key : String,
    val value : Float,
)
data class TracecTreeLevelData(
    val params : ArrayList<Float>,
    val level : Int,
    //val levelReq : Int,
    //val promotionReq : Int,
    val cost : ArrayList<TracecTreeCost>,
)
data class TracecTreeCost(
    val id : Int,
    val count : Int,
)