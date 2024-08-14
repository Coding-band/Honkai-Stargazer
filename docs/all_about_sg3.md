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
  - [資料庫結構 Database Structure](#資料庫結構-database-structure)
    - [API](#api)
    - [帳戶基本資料 (模組1)](#帳戶基本資料-模組1)
    - [邀請碼 (模組1)](#邀請碼-模組1)
    - [使用量統計 (模組1)](#使用量統計-模組1)
    - [混沌回憶 (模組2)](#混沌回憶-模組2)
    - [練度排行 (模組2)](#練度排行-模組2)
    - [虛構敘事 (模組2)](#虛構敘事-模組2)
    - [用戶角色數據 (模組3)](#用戶角色數據-模組3)
    - [躍遷 (模組4)](#躍遷-模組4)
  - [專案結構 Project Structure](#專案結構-project-structure)
  - [Known-Issue 已知問題](#known-issue-已知問題)
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
    - `feat` : 有關非Bug的所有功能/頁面更新 (For non-bug-fix's function/UI update)
    - `fix` : Bug、算法邏輯錯誤相關修正 (fixing Bug and algorthm logic mistakes)
    - `docs` : 文檔相關的更新 (Updates that about documents)
    - `style` : 格式化代碼 (Reformatting Codes) 
    - `revert` : 撤銷先前Commit (Revert previous commit)
    - `struct` : 更改代碼檔案結構 (Modify Code File Structure, E.g. Change ListHeader.kt to PageHeader.kt)
    - `rss` : 數據/圖片更新 (Data/Image file update)
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
- 只展示前100就够
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

## 資料庫結構 Database Structure
### API
- 獲取伺服器時間
### 帳戶基本資料 (模組1)
- 基礎資料
  - UID
  - 用戶名稱
  - 是否展示所有角色
  - 上次登入時間
  - 身份組 (`user` / `mod` / `mascot`)
  - 廣告計劃  (`normal` / `gift_20xxxxxx` / `donor` / `beta` / `mascot`)
  
- 用戶裝置相關
  - App 版本 (`BETA 2.4.0` / `Release 2.3.2`)
  - App 版本號 (`3840`)
  - 裝置平台 (`Android` / `iOS` ...)
  - 裝置版本 (`Android 34` / `iOS 17.1.2` / `iPad OS 16.0.0`)

- 遊戲相關
  - 遊玩的伺服器
  - 頭像URL
  - 開啟箱子數目
  - 成就完成數目
  - 活躍日數
  - 混沌回憶層數

### 邀請碼 (模組1)
  - 該用戶邀請代碼
  - 該使用了的邀請代碼
  - 
### 使用量統計 (模組1)
  - 該日使用者數量
  - 該日功能使用量
  - 本月不重複用戶量

### 混沌回憶 (模組2)
- 按混沌回憶的版本+層數作資料表
  - 該用戶使用的4個角色(名字&光錐ID)
  - 上半還是下半
  - 紀錄時間
  - 回合數

### 練度排行 (模組2)
- 角色ID作資料表
  - 該角色的角色分數
  - 該角色的星魂數
  - 該角色穿戴的遺器，各自的分數
  - 該角色穿戴的遺器，合共的分數

### 虛構敘事 (模組2)
- 按虛構敘事的版本+層數作資料表
  - 該用戶使用的4個角色(名字&光錐ID)
  - 紀錄時間
  - 回合數

### 用戶角色數據 (模組3)
> 用戶UID作資料表
  - 該角色4個天賦的等級
  - 該角色穿戴的光錐 (等級、疊形)
  - 該角色穿戴的遺器 (各自的等級、詞條名和值`[JSON]`)
  - 該角色屬性值 (名和值`[JSON]`)

### 躍遷 (模組4)
- 躍遷紀錄 按用戶UID作資料表
  - 稍後添加
- 躍遷總覽
  - 稍後添加

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
      - `components` : All UI Components & UI functions (Which won't be reused in other pages)
      - `screens` : All Screens / Pages / Dialogs of the App
      ~~- `core` : All functions that support Screens and algorthm (E.g. List Sorting, Button onClick action)~~
        - Reason : Since Calculation function are putting in utils. UI functions can really no need to put in there
      - `types` : Enums, Interfaces, Object Types, Constants
      - `utils` : All useful functions (Expect to use as high frequency)
      - `App.kt` : The Main Frame of the App, will handle Screen Display and Init function
      - `SpecificPlatformCode.kt` : The only file to handle `expect` functions

## Known-Issue 已知問題
- **做不及呀 真做不及呀！ (Can't finish all)**
- CharacterInfoPage -> InfoAdviceTeam 造成卡頓 (Laggy)
- CharacterInfoPage -> 從其他角色頁面跳回會顯示角色全身圖 (Char Full Img will display when back from other char info page)
- RelicInfoPage & LightconeInfoPage -> 細節未完善 (Not finished details improvements)
- BackgroundSettingScreen -> 細節未完善 (Not finished details improvements)
- BackgroundSettingScreen -> 沒法做到無縫輪播、按鈕功能未完善 (Can't do seamless loop, Button function not finished)
- Language -> App語言因為框架未支援而沒法更改 (Can't change apps' language natively since the framework not supported)
- SettingPage -> 設定頁面通知部分未完成 ( Setting Page Notification part not finished)
- UserAccount -> 用戶登入對接資料庫伺服器未完成 (User Login's function still not done adapt Database Server API)
- UserAccount -> 詳細角色未紀錄 (User Account's Character Info not recorded)
- HoyolabLoginPage -> Popup細節未完善 (Not finished details improvements of Popups)
- Character/Lc/RelicListPage -> 排序選單未完成 (Sort Menu not finished)
- UserAccount -> 加載用戶數據大卡頓 (VERY Laggy when loading user data)
- Comment Function -> 同意添回純留言功能，須確保有擴展性 (Only agree to add pure comment function, need to ensure extensibility)

## Close BETA Testing
### 目前(2024/07/17)暫定如下：
- 階段一（8月7號開始）：基礎角色資料、排行榜、評分、用戶登入
- 階段二（8月21號開始）：躍遷分析、廣告功能
- 階段三（8月28號開始）：排軸功能，最終封測
- 將於9月9號完結，9月15號推出正式版

## Todo-List
- [x] Finish This md's writting

July 1 - July 14
- [x] Error Log Handler (1 day)
- [x] Basic Util, Constants, types (2 day)
- [x] Character Info Page [Not include Comments] (8 days)
- [x] Lightcone Info Page (1 day do together)
- [x] Relic Info Page (1 day do together)
- [x] Database Structure Design (2 days)

July 15 - July 21
- [x] miHoYo API [DeviceFP will do after Release]
- [x] Setting Page (2 days) [Including Language Setting]
- [x] Background Setting Page (1 day)
- [x] Break in July 18 - 19
- [x] User Login & Home Page Info (2 days) [Without Database API]

July 22 - August 7
- [x] Event List, Interaction Map Redirect (2 day) [Do together with lower choice],
- [x] User Info Page (2 days)
- [x] User Character Page (2 days)
- [x] UID Search (0.5 days)
- [x] Proficient Calculation (Transfer) (1 days)
- [x] Backend API design (1 days)
- [x] Prepare for SG2 2.4 RSS + Offline Update (July 29 - 30)
- [x] Prepare for Close BETA Testing (1 day)

August 8 - August 11
- [x] Bug Fixing (1 + n days)

August 12 - August 21 (before Part 2 of Close BETA)
- [ ] MOC & PF Leaderboard (3 days)
  - Team Usage Ranking
- [ ] Proficient Leaderboard (2 days)
- [ ] Action Order Function (5 days)

August 22 - August 27
- [ ] Notification Initize (2 days)
- [ ] Customize Home Page Function (2 days)
- [ ] Ads Function, In-app Purchase Function (2 days)

August 28 - September 7
- [ ] Relic Score Algorthm (? days) [With Reset]
- [ ] Warp Simulator (3 days)
- [ ] Warp Analysis (4 days)

September 10 - September 15
- [ ] Final checking
- [ ] Submit to Google Play Store & App Store (September 11)
