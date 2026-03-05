/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2026 Coding Band 版權所有
 */

package utils.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import types.UserAbyssRecord.Companion.refreshAAData
import types.UserAbyssRecord.Companion.refreshASData
import types.UserAbyssRecord.Companion.refreshMOCData
import types.UserAbyssRecord.Companion.refreshPFData
import types.UserAccount.Companion.getUID
import types.UserAccount.Companion.refreshUserAccount
import utils.starbase.StarbaseAPI

/**
 * SplashDataLoader - 負責管理 Splash 頁面的數據預加載
 *
 * 優化策略：
 * 1. 將關鍵數據（HomePage 必須）和非關鍵數據（可延遲）分開處理
 * 2. 關鍵數據使用並行加載來減少等待時間
 * 3. 上傳操作完全在背景執行，不阻塞導航
 * 4. 提供加載狀態供 UI 監聽
 */
object SplashDataLoader {

    // 加載狀態
    val isEssentialDataLoaded: MutableState<Boolean> = mutableStateOf(false)
    val isAllDataLoaded: MutableState<Boolean> = mutableStateOf(false)

    // 防止重複加載
    private var isLoading = false

    /**
     * 開始數據加載流程
     * @param onEssentialComplete 當關鍵數據加載完成時的回調（可以導航到 HomePage）
     * @param onAllComplete 當所有數據加載完成時的回調
     */
    fun startLoading(
        onEssentialComplete: (() -> Unit)? = null,
        onAllComplete: (() -> Unit)? = null
    ) {
        if (isLoading) return
        if (getUID() == "000000000") {
            // 未登入用戶，直接標記完成
            isEssentialDataLoaded.value = true
            isAllDataLoaded.value = true
            onEssentialComplete?.invoke()
            onAllComplete?.invoke()
            return
        }

        isLoading = true
        isEssentialDataLoaded.value = false
        isAllDataLoaded.value = false

        CoroutineScope(Dispatchers.Default).launch {
            // ===== 階段 1: 關鍵數據（並行加載）=====
            // 這些是 HomePage 必須的數據
            val essentialJobs = listOf(
                // Job 1: 用戶帳號資料 + Note (體力、委託等)
                async {
                    try {
                        refreshUserAccount()
                    } catch (e: Exception) {
                        errorLog("SplashDataLoader", "refreshUserAccount()", e)
                    }
                },
                // Job 2: CharWeightList (角色權重，用於評分)
                async {
                    try {
                        // 強制預加載 CharWeightList
                        CharWeightList.INSTANCE
                    } catch (e: Exception) {
                        errorLog("SplashDataLoader", "CharWeightList.INSTANCE", e)
                    }
                }
            )

            // 等待關鍵數據加載完成
            essentialJobs.awaitAll()

            // 標記關鍵數據已加載，通知可以導航
            withContext(Dispatchers.Main) {
                isEssentialDataLoaded.value = true
                onEssentialComplete?.invoke()
            }

            // ===== 階段 2: 次要數據（並行加載，不阻塞導航）=====
            val secondaryJobs = listOf(
                // 深淵相關數據
                async {
                    try {
                        refreshMOCData()
                    } catch (e: Exception) {
                        errorLog("SplashDataLoader", "refreshMOCData()", e)
                    }
                },
                async {
                    try {
                        refreshPFData()
                    } catch (e: Exception) {
                        errorLog("SplashDataLoader", "refreshPFData()", e)
                    }
                },
                async {
                    try {
                        refreshASData()
                    } catch (e: Exception) {
                        errorLog("SplashDataLoader", "refreshASData()", e)
                    }
                },
                async {
                    try {
                        refreshAAData()
                    } catch (e: Exception) {
                        errorLog("SplashDataLoader", "refreshAAData()", e)
                    }
                }
            )

            // 等待次要數據加載完成
            secondaryJobs.awaitAll()
            Preferences().Leaderboard.updatedLeaderboard()

            // ===== 階段 3: 上傳操作（完全背景，不等待）=====
            // 這些是上傳到 Starbase 的操作，不應該阻塞任何 UI
            launch {
                try {
                    // 依序上傳，避免同時太多請求
                    StarbaseAPI().updateUserAccountInfo()
                    StarbaseAPI().updateCharData()
                } catch (e: Exception) {
                    errorLog("SplashDataLoader", "updateUserAccountInfo/CharData", e)
                }
            }

            launch {
                try {
                    // 深淵數據上傳可以並行
                    listOf(
                        async { StarbaseAPI().updateMOCData() },
                        async { StarbaseAPI().updatePFData() },
                        async { StarbaseAPI().updateASData() },
                        async { StarbaseAPI().updateAAData() }
                    ).awaitAll()
                } catch (e: Exception) {
                    errorLog("SplashDataLoader", "updateAbyssData", e)
                }
            }

            // 標記所有下載完成（上傳仍在背景進行）
            withContext(Dispatchers.Main) {
                isAllDataLoaded.value = true
                onAllComplete?.invoke()
            }

            isLoading = false
        }
    }

    /**
     * 重置加載狀態（用於登出或切換帳號）
     */
    fun reset() {
        isLoading = false
        isEssentialDataLoaded.value = false
        isAllDataLoaded.value = false
    }
}

