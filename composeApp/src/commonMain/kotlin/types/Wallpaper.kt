package types

import androidx.compose.ui.graphics.ImageBitmap
import utils.Language
import utils.UtilTools
import utils.annotation.VersionUpdateCheck

open class Wallpaper(
    val id: String,
    val fileName: String,
    val localeName: String? = null,
    //val nameRes: StringResource? = null
){
    @VersionUpdateCheck
    companion object {
        val wallpaperList : ArrayList<Wallpaper> = arrayListOf(
            Wallpaper(id = "221000", fileName = "221000", localeName = WallPaperName_221000()),
            Wallpaper(id = "221001", fileName = "221001", localeName = WallPaperName_221001()),
            Wallpaper(id = "221002", fileName = "221002", localeName = WallPaperName_221002()),
            Wallpaper(id = "221003", fileName = "221003", localeName = WallPaperName_221003()),
            Wallpaper(id = "221004", fileName = "221004", localeName = WallPaperName_221004()),
            Wallpaper(id = "221005", fileName = "221005", localeName = WallPaperName_221005()),
            Wallpaper(id = "221006", fileName = "221006", localeName = WallPaperName_221006()),
            Wallpaper(id = "bg_light", fileName = "bg_light", localeName = "純色壁紙"),
            Wallpaper(id = "8001", fileName = "8001"),
            Wallpaper(id = "8002", fileName = "8002"),
            Wallpaper(id = "1001", fileName = "1001"),
            Wallpaper(id = "1002", fileName = "1002"),
            Wallpaper(id = "1003", fileName = "1003"),
            Wallpaper(id = "1004", fileName = "1004"),
            Wallpaper(id = "1005", fileName = "1005"),
            Wallpaper(id = "1006", fileName = "1006"),
            Wallpaper(id = "1008", fileName = "1008"),
            Wallpaper(id = "1009", fileName = "1009"),
            Wallpaper(id = "1013", fileName = "1013"),
            Wallpaper(id = "1101", fileName = "1101"),
            Wallpaper(id = "1102", fileName = "1102"),
            Wallpaper(id = "1104", fileName = "1104"),
            Wallpaper(id = "1106", fileName = "1106"),
            Wallpaper(id = "1107", fileName = "1107"),
            Wallpaper(id = "1108", fileName = "1108"),
            Wallpaper(id = "1109", fileName = "1109"),
            Wallpaper(id = "1110", fileName = "1110"),
            Wallpaper(id = "1111", fileName = "1111"),
            Wallpaper(id = "1112", fileName = "1112"),
            Wallpaper(id = "1201", fileName = "1201"),
            Wallpaper(id = "1203", fileName = "1203"),
            Wallpaper(id = "1204", fileName = "1204"),
            Wallpaper(id = "1205", fileName = "1205"),
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
            Wallpaper(id = "1310", fileName = "1310"),
            Wallpaper(id = "1312", fileName = "1312"),
            Wallpaper(id = "1315", fileName = "1315"),
        )

        fun getWallpaperByFileName(bgName : String): ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.BGS, bgName)
        }


        private fun WallPaperName_221000(language: Language.AppLanguage = Language.AppLanguageInstance): String {
            return when(language){
                Language.AppLanguage.ZH_CN -> "无名路途"
                Language.AppLanguage.ZH_HK -> "無名路途"
                Language.AppLanguage.JP -> "ナナシの道"
                Language.AppLanguage.KR -> "무명객의 여정"
                Language.AppLanguage.ES -> "Camino de los Anónimos"
                Language.AppLanguage.FR -> "Voyage des Sans Noms"
                Language.AppLanguage.RU -> "Путешествие Безымянных"
                Language.AppLanguage.TH -> "การเดินทางนิรนาม"
                Language.AppLanguage.VI -> "Hành Trình Không Tên"
                Language.AppLanguage.DE -> "Abenteuer der Namenlosen"
                Language.AppLanguage.PT -> "Jornada Inominada"
                else -> "Nameless Journey"
            }
        }

        private fun WallPaperName_221001(language: Language.AppLanguage = Language.AppLanguageInstance): String {
            return when(language){
                Language.AppLanguage.ZH_CN -> "机巧梦宵长"
                Language.AppLanguage.ZH_HK -> "機巧夢宵長"
                Language.AppLanguage.JP -> "宵長き機巧の夢"
                Language.AppLanguage.KR -> "기나긴 기교의 꿈"
                Language.AppLanguage.ES -> "Noche larga, sueño lúcido"
                Language.AppLanguage.FR -> "Rêves d'ingéniosité"
                Language.AppLanguage.RU -> "Долгий сон робоптахи"
                Language.AppLanguage.TH -> "ค่ำคืนอันยาวนานของนกสมองกล"
                Language.AppLanguage.VI -> "Giấc Mơ Công Nghệ Hóa"
                Language.AppLanguage.DE -> "Ingenium-Träume"
                Language.AppLanguage.PT -> "Sonhos de Engenho"
                else -> "Ingenium Dreams"
            }
        }

        private fun WallPaperName_221002(language: Language.AppLanguage = Language.AppLanguageInstance): String {
            return when(language){
                Language.AppLanguage.ZH_CN -> "列车长特供"
                Language.AppLanguage.ZH_HK -> "列車長特餐"
                Language.AppLanguage.JP -> "車掌のスペシャルプレート"
                Language.AppLanguage.KR -> "차장의 특식"
                Language.AppLanguage.ES -> "Especialidad del revisor"
                Language.AppLanguage.FR -> "La spécialité du conducteur"
                Language.AppLanguage.RU -> "Особое блюдо от проводника"
                Language.AppLanguage.TH -> "สูตรเฉพาะของกัปตันรถไฟ"
                Language.AppLanguage.VI -> "Đích Thân Tàu Trưởng Chế Biến"
                Language.AppLanguage.DE -> "Spezialität vom Schaffner"
                Language.AppLanguage.PT -> "A Especialidade do Condutor"
                else -> "Conductor's Treat"
            }
        }

        private fun WallPaperName_221003(language: Language.AppLanguage = Language.AppLanguageInstance): String {
            return when(language){
                Language.AppLanguage.ZH_CN -> "星火游园"
                Language.AppLanguage.ZH_HK -> "星火遊園"
                Language.AppLanguage.JP -> "星火游園"
                Language.AppLanguage.KR -> "불꽃 정원"
                Language.AppLanguage.ES -> "Parque de fuego estelar"
                Language.AppLanguage.FR -> "Foyer du feu d'étoile"
                Language.AppLanguage.RU -> "Сад искр"
                Language.AppLanguage.TH -> "สวนประกายเพลิงหฤหรรษ์"
                Language.AppLanguage.VI -> "Khu Vườn Tinh Hỏa"
                Language.AppLanguage.DE -> "Sternenfeuergarten"
                Language.AppLanguage.PT -> "Parque Chama Estelar"
                else -> "Starfire Parkland"
            }
        }

        private fun WallPaperName_221004(language: Language.AppLanguage = Language.AppLanguageInstance): String {
            return when(language){
                Language.AppLanguage.ZH_CN -> "盛会之星"
                Language.AppLanguage.ZH_HK -> "盛會之星"
                Language.AppLanguage.JP -> "宴の星"
                Language.AppLanguage.KR -> "축제의 별"
                Language.AppLanguage.ES -> "Planeta de las celebraciones"
                Language.AppLanguage.FR -> "La planète des festivités"
                Language.AppLanguage.RU -> "Планета празднеств"
                Language.AppLanguage.TH -> "ดาวแหงการเฉลิมฉลอง"
                Language.AppLanguage.VI -> "Hành Tinh Lễ Hội"
                Language.AppLanguage.DE -> "Der Planet der Festlichkeiten"
                Language.AppLanguage.PT -> "O Planeta das Festividades"
                else -> "The Planet of Festivities"
            }
        }

        private fun WallPaperName_221005(language: Language.AppLanguage = Language.AppLanguageInstance): String {
            return when(language){
                Language.AppLanguage.ZH_CN -> "星间旅行"
                Language.AppLanguage.ZH_HK -> "星間旅行"
                Language.AppLanguage.JP -> "開拓伝説～星間旅行スゴロク～"
                Language.AppLanguage.KR -> "성간 여행"
                Language.AppLanguage.ES -> "Cosmodisea"
                Language.AppLanguage.FR -> "Cosmopoly"
                Language.AppLanguage.RU -> "Звёздная одиссея"
                Language.AppLanguage.TH -> "ตะลุยจักรวาล"
                Language.AppLanguage.VI -> "Cờ Tỷ Phú Vũ Trụ"
                Language.AppLanguage.DE -> "Kosmonopoly"
                Language.AppLanguage.PT -> "Cosmodisseia"
                else -> "Cosmodyssey"
            }
        }

        private fun WallPaperName_221006(language: Language.AppLanguage = Language.AppLanguageInstance): String {
            return when(language){
                Language.AppLanguage.ZH_CN -> "课题进行时"
                Language.AppLanguage.ZH_HK -> "課題進行時"
                Language.AppLanguage.JP -> "研究進行中"
                Language.AppLanguage.KR -> "프로젝트 진행 중"
                Language.AppLanguage.ES -> "Tarea en progreso"
                Language.AppLanguage.FR -> "Projet en cours"
                Language.AppLanguage.RU -> "Рабочий проект"
                Language.AppLanguage.TH -> "หัวข้อที่กำลังดำเนินการ"
                Language.AppLanguage.VI -> "Đề Tài Đang Thực Hiện"
                Language.AppLanguage.DE -> "Thema in Bearbeitung"
                Language.AppLanguage.PT -> "Tarefa em Progresso"
                else -> "Task in Progress"
            }
        }
    }



}

