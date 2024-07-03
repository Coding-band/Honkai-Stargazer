# Stargazer 3

## 目錄 Menu
- [Stargazer 3](#stargazer-3)
  - [目錄 Menu](#目錄-menu)
  - [重寫的目標 Purpose in Rewrite](#重寫的目標-purpose-in-rewrite)
  - [分工安排 Division of Work Arrangement](#分工安排-division-of-work-arrangement)
    - [2O48 (30%)](#2o48-30)
    - [Somebody (10%)](#somebody-10)
    - [Voc (60%)](#voc-60)
  - [規範 Repo Rules](#規範-repo-rules)
    - [Commit Summary](#commit-summary)
  - [功能備忘 Functions Remarks](#功能備忘-functions-remarks)
    - [混沌回憶/虛構敘事排行榜 MOC/PF Leaderboards](#混沌回憶虛構敘事排行榜-mocpf-leaderboards)
    - [廣告功能 Ads](#廣告功能-ads)
    - [通知功能 Notifications](#通知功能-notifications)
    - [Close BETA](#close-beta)
    - [不定期補充](#不定期補充)
  - [專案結構 Project Structure](#專案結構-project-structure)
  - [Todo-List](#todo-list)

## 重寫的目標 Purpose in Rewrite
這是我們重寫 Stargazer 的主要目的
- 簡化並刪除無用功能
- 重新規劃好Stargazer 3中的代碼結構
- 優化App和資料庫之間的互動控制
- 允許App可以離線使用
- 改善用戶體驗（避免卡頓）
- 減低Bug識別難度

Here's the major purpose we rewrite Stargazer
- Simplify and remove useless functions
- Make good planning of code structure in Stargazer 3
- Good managing in interation between app and database
- Allowing app run in offline
- Inproving user experience (No more Lag)
- Reduce bug-identify difficulties

## 分工安排 Division of Work Arrangement
### 2O48 (30%)
- UI/UX 設計
- App品質監管

### Somebody (10%)
- 功能編寫及維護 Function Develop and Maintain
- 資料庫管理 Database Manage
- 建議指導 Suggestion Advisor

### Voc (60%)
- 功能編寫及維護 Function Develop and Maintain
- 資料庫管理 Database Manage
- 宣傳、社群管理 Advertise & Community Manage
- 支援人員 Custom Service
- 翻譯協調 Translation Coordination
- 程式編譯 App Compilation
- 上架及營運 Listing and Operation
- 資源更新 Resource Update

## 規範 Repo Rules
- 每次Commit請盡量只專注於一個功能 (Please try to focus ONE function in each commits)
### Commit Summary
- 僅允許以下類型 (Only allow commit type below)
    - feat : 有關非Bug的所有功能/頁面更新 (For non-bug-fix's function/UI update)
    - fix : Bug、算法邏輯錯誤相關修正 (fixing Bug and algorthm logic mistakes)
    - docs : 文檔相關的更新 (Updates that about documents)
    - style : 格式化代碼 (Reformatting Codes) 
    - revert : 撤銷先前Commit (Revert previous commit)
    - struct : 更改代碼檔案結構 (Modify Code File Structure, E.g. Change ListHeader.kt to PageHeader.kt)
    - rss : 數據/圖片更新 (Data/Image file update)
- 應使用以下格式 (Must use the format below) :
  - `<type_of_commit>: <commit_describe_zh> (<commit_describe_en>)`
  - E.g. `feat: 角色介紹頁面添加角色全身圖 (add Character Full Image in Character Info Page)`
  - E.g. `fix: Home Page TextSize`


## 功能備忘 Functions Remarks
### 混沌回憶/虛構敘事排行榜 MOC/PF Leaderboards
- 用戶開啟排行時，先檢查是否最近一小時有讀取過資料庫數據
  - 若有，將會讀取存放在本地的臨時數據
  - 否則，會向伺服器申請讀取數據，並在本地存放
- 用戶在啟動app時，若果混沌回憶/虛構敘事數據和上次啟動app一樣
  - 則不會向伺服器更新這部分的數據

EN:
- When the user opens the leaderboards, first check whether the database data has been read in the last hour
  - If there is, temporary data stored locally will be read.
  - Otherwise, it will apply to the server to read the data and store it locally.
- When the user launches the app, if the chaotic memory/fictional narrative data is the same as the last time he launched the app
  - this part of the data will not be updated to the server

### 廣告功能 Ads
- 準備好廣告框架需要的所有設定
- 視乎BETA反應載決定是否開啟廣告
- 應避免影響使用者使用體驗
- 廣告類型會有:
  - 5秒可跳過的全屏廣告
  - 橫幅廣告
- 透過App内購/Buy Me A Coffee支持我們的用戶，可永久免廣告（需要商榷）

EN:
- Prepare all the settings required for the ads framework
- Determine whether to enable ads based on Close BETA response loading
- Avoid affecting user experience
- Ad types will include:
 - 5 seconds skippable full-screen ads
 - Banner ads
- Support our users through in-app purchase/Buy Me A Coffee, will be free from ads forever (need to discuss)

### 通知功能 Notifications

### Close BETA
- 預計200位測試員
- 稍後更新

### 不定期補充

## 專案結構 Project Structure
- composeApp/src
  - androidMain/kotlin : Android Native Codes
    - `SpecificPlatformCode.android.kt` All `actual` functions are writen in there
  - desktopMain/kotlin : PC/Mac Native Codes
    - `SpecificPlatformCode.desktop.kt` All `actual` functions are writen in there
  - nativeMain/kotlin : iOS/iPad OS Native Codes
    - `SpecificPlatformCode.native.kt` All `actual` functions are writen in there
  - commonMain : The shared folder of UI, Functions, Assets
    - composeResource : storing translations, images, json files
      - `drawable` : Images that is use to display as UI Pages (E.g. App & Button Icon)
      - `font` : Font Files
      - `values` : Translation XML files
      - `files/data` : All json files
      - `files/images` : All Images that is dynamic read 
    - kotlin : Source Code Root
      - `components` : All UI Components (Pack as able use selfly, so functions is allowed to include)
      - `screens` : All Screens / Pages / Dialogs of the App
      - `core` : All functions that support Screens and algorthm (E.g. List Sorting, Button onClick action)
      - `types` : Enums, Interfaces, Object Types, Constants
      - `utils` : All useful functions (Expect to use as high frequency)
      - `App.kt` : The Main Frame of the App, will handle Screen Display and Init function
      - `SpecificPlatformCode.kt` : The only file to handle `expect` functions

## Todo-List
- [x] Finish This md's writting

July 1 - July 7
- [ ] Error Log Handler (1 day)
- [ ] Character Info Page [Not include Comments] (5 days)
- [ ] Lightcone Info Page (0.5 days)
- [ ] Relic Info Page (0.5 day)

July 8 - July 14
- [ ] miHoYo API [DeviceFP will do at late August]
- [ ] Database Structure Design (2 days)
- [ ] Event List, Interaction Map Redirect (1 day)
- [ ] User Login & Home Page Info (2 days)
- [ ] User Info Page & UID Search (2 days)

July 15 - July 21
- [ ] MOC & PF Leaderboard (7 days)
  - Team Usage Ranking

July 22 - July 28
- [ ] Proficient Calculation (2 days)
- [ ] Relic Score Algorthm (2 days)
- [ ] Proficient Leaderboard (3 days)

July 29 - August 4
- [ ] Wrap Simulator (3 days)
- [ ] Wrap Analysis (4 days)

August 5 - August 11
- [ ] Customize Home Page Function (2 days)
- [ ] Ads Function (2 days)
- [ ] In-app Purchase Function (2 days)
- [ ] Prepare for Close BETA Testing (1 day)

August 12 - August 18
- [ ] Action Order Function (5 days)
- [ ] Notification Initize (2 days)

August 19 - August 31
- [ ] Close Beta Testing