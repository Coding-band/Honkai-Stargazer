package types

import androidx.compose.ui.graphics.ImageBitmap
import utils.UtilTools

open class Wallpaper(
    val id: String,
    val fileName: String,
    val cnName: String? = null,
    //val nameRes: StringResource? = null
){
    companion object {
        val wallpaperList : ArrayList<Wallpaper> = arrayListOf(
            Wallpaper(id = "221000", fileName = "221000", cnName = "無名路途"),
            Wallpaper(id = "221001", fileName = "221001", cnName = "機巧夢消長"),
            Wallpaper(id = "221002", fileName = "221002", cnName = "列車長請客！"),
            Wallpaper(id = "221003", fileName = "221003", cnName = "星火遊園"),
            Wallpaper(id = "221004", fileName = "221004", cnName = "盛會之星"),
            Wallpaper(id = "221005", fileName = "221005", cnName = "星間旅行"),
            Wallpaper(id = "bg_light", fileName = "bg_light", cnName = "純色壁紙"),
            Wallpaper(id = "1001", fileName = "1001"),
            Wallpaper(id = "1002", fileName = "1002"),
            Wallpaper(id = "1003", fileName = "1003"),
            Wallpaper(id = "1004", fileName = "1004"),
            Wallpaper(id = "1005", fileName = "1005"),
            Wallpaper(id = "1006", fileName = "1006"),
            Wallpaper(id = "1008", fileName = "1008"),
            Wallpaper(id = "1009", fileName = "1009"),
            /*
            Wallpaper(id = "1013", fileName = "1013"),
            Wallpaper(id = "1101", fileName = "1101"),
            Wallpaper(id = "1102", fileName = "1102"),
            Wallpaper(id = "1104", fileName = "1104"),
            Wallpaper(id = "1106", fileName = "1106"),
            Wallpaper(id = "1107", fileName = "1107"),
            Wallpaper(id = "1107-2", fileName = "1107-2"),
            Wallpaper(id = "1107-3", fileName = "1107-3"),
            Wallpaper(id = "1108", fileName = "1108"),
            Wallpaper(id = "1109", fileName = "1109"),
            Wallpaper(id = "1110", fileName = "1110"),
            Wallpaper(id = "1111", fileName = "1111"),
            Wallpaper(id = "1112", fileName = "1112"),
            Wallpaper(id = "1201", fileName = "1201"),
            Wallpaper(id = "1203", fileName = "1203"),
            Wallpaper(id = "1204", fileName = "1204"),
            Wallpaper(id = "1205", fileName = "1205"),
            Wallpaper(id = "1205-2", fileName = "1205-2"),
            Wallpaper(id = "1206", fileName = "1206"),
            Wallpaper(id = "1207", fileName = "1207"),
            Wallpaper(id = "1208", fileName = "1208"),
            Wallpaper(id = "1209", fileName = "1209"),
            Wallpaper(id = "1210", fileName = "1210"),
            Wallpaper(id = "1211", fileName = "1211"),
            Wallpaper(id = "1212", fileName = "1212"),
            Wallpaper(id = "1213", fileName = "1213"),
            Wallpaper(id = "1214", fileName = "1214"),
            Wallpaper(id = "1215", fileName = "1215"),
            Wallpaper(id = "1217", fileName = "1217"),
            Wallpaper(id = "1302", fileName = "1302"),
            Wallpaper(id = "1303", fileName = "1303"),
            Wallpaper(id = "1305", fileName = "1305"),
            Wallpaper(id = "1306", fileName = "1306"),
            Wallpaper(id = "1307", fileName = "1307"),
            Wallpaper(id = "1312", fileName = "1312"),
             */
        )

        fun getWallpaperByFileName(bgName : String): ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.BGS, bgName)
        }
    }


}

