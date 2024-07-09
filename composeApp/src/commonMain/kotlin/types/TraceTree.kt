package types

data class TraceTreeItem(
    val id : Int,
    val anchor : Int = -1,
    val type : Int = -1,
    val name : String,
    val tagHash : String? = null, //Name of that atk tag
    val typeDescHash : String? = null, //Describe the way of the atk type
    //val levelReq: Int,
    //val promotionReq: Int,
    val iconPath: String,
    val desc: String,
    val energy: Int = -1,
    val ultimateCost : Int = 0,
    val levelData: ArrayList<TracecTreeLevelData>? = null,
    val statusList: ArrayList<TracecTreeKeyStatus>? = null,
    val trigCost: ArrayList<Material>? = null,
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
    val cost : ArrayList<Material>,
)