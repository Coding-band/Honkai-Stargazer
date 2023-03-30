package com.voc.honkai_stargazer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "character_db")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("Name")
    val name: String,
    @SerializedName("Rarity")
    val rarity: Int,
    @SerializedName("Role")
    val role: String,
    @SerializedName("Path")
    val path: String,
    @SerializedName("Sex")
    val sex: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Element")
    val element: String,
    @SerializedName("Affiliation")
    val affiliation: String,
    @SerializedName("Short description")
    val description: String,
    @SerializedName("Name of Basic Attack")
    val basicAttackName: String,
    @SerializedName("BA description")
    val basicAttackDescription: String,
    @SerializedName("Name of Skill")
    val skillName: String,
    @SerializedName("Skill description")
    val skillDescription: String,
    @SerializedName("Ultimate")
    val ultimate: String,
    @SerializedName("Ult description")
    val ultimateDescription: String,
    @SerializedName("Talent")
    val talent: String,
    @SerializedName("Telent description")
    val talentDescription: String,
    @SerializedName("Technique")
    val technique: String,
    @SerializedName("Technique descript")
    val techniqueDescription: String,
    @SerializedName("Base ATK Traces")
    val basicAttackTraceName: String,
    @SerializedName("Base ATK Traces description")
    val basicAttackTraceDescription: String,
    @SerializedName("Skill Traces")
    val skillTraceName: String,
    @SerializedName("Skill traces descrip")
    val skillTraceDescription: String,
    @SerializedName("Ultimate Traces")
    val ultimateTraceName: String,
    @SerializedName("Ultimate Traces desc")
    val ultimateTraceDescription: String,
    @SerializedName("Other Traces")
    val otherTraces: String,
    @SerializedName("Other Traces desc")
    val otherTracesDescription: String,
    @SerializedName("Name of E1")
    val eOneName: String,
    @SerializedName("Name of E2")
    val eTwoName: String,
    @SerializedName("Name of E3")
    val eThreeName: String,
    @SerializedName("Name of E4")
    val eFourName: String,
    @SerializedName("Name of E5")
    val eFiveName: String,
    @SerializedName("Name of E6")
    val eSixName: String,
    @SerializedName("E1 description")
    val eOneDescription: String,
    @SerializedName("E2 description")
    val eTwoDescription: String,
    @SerializedName("E3 description")
    val eThreeDescription: String,
    @SerializedName("E4 description")
    val eFourDescription: String,
    @SerializedName("E5 description")
    val eFiveDescription: String,
    @SerializedName("E6 description")
    val eSixDescription: String,
)
