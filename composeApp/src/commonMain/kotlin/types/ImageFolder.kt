package types

enum class ImageFolder(val folderName: String, val suffix: String) {
    AVATAR_ICON("avatar_icon", ".webp"),
    BGS("bgs", ".webp"),

    CHAR_EIDOLON("character_eidolon", ".webp"),
    CHAR_EIDOLON_BORDER("character_eidolon_border", ".svg"),
    CHAR_FADE("character_fade", "_fade.webp"),
    CHAR_FULL("character_full", "_full.webp"),
    CHAR_ICON("character_icon", "_icon.webp"),
    CHAR_SKILL("character_skill", ".webp"),
    CHAR_SKILL_TREE("character_skill_tree", ".webp"),
    CHAR_SOUL("character_soul", ".webp"),
    CHAR_SPLASH("character_splash", "_splash.webp"),

    LC_ARTWORK("lightcone_artwork", "_artwork.webp"),
    LC_ICON("lightcone_icon", ".webp"),
    MAOGOU("maogou", ".webp"),
    MATERIAL_ICON("material_icon", ".webp"),
    MONSTER_ICON("monster_icon", "_icon.webp"),
    ORMANENT_ICON("ornament_icon", ".webp"),
    ORMANENT_PC_ICON("ornament_pcicon", ".webp"),
    RELIC_ICON("relic_icon", ".webp"),
    RELIC_PC_ICON("relic_pcicon", ".webp"),
}