/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package utils

/**
 * I know this way is very silly, but currently I can only do this to reduce unstable
 */
class LongStringXML() {

    fun PublicCharDesc(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "Para proteger a segurança e os direitos do usuário, atualizamos os dados da função hoyolab por meio do dispositivo do usuário (cliente) e não por meio do servidor. Isso significa que se um usuário não usar o Stargazer por um tempo, isso pode resultar em discrepâncias na contagem de personagens, dias ativos, conquistas alcançadas e dados do Salão Esquecido. Observe que esses dados são apenas para referência e não são precisos."
            Language.AppLanguage.ZH_CN -> "为了保障安全性和用户权益，我们通过用户的设备（客户端），而非透过服务器来更新 hoyolab 角色数据。这意味着如果用户一段时间不使用 Stargazer，可能会导致他们的角色数量、活跃天数、达成成就和忘却之庭数据存在差异。请注意，这些数据仅供参考，不具有绝对准确性。"
            Language.AppLanguage.ZH_HK -> "為了保障安全性和用戶權益，我們通過用戶的設備（客戶端），而非透過伺服器來更新 hoyolab 角色數據。這意味著如果用戶一段時間不使用 Stargazer，可能會導致他們的角色數量、活躍天數、達成成就和忘卻之庭數據存在差異。請注意，這些數據僅供參考，不具有絕對準確性。"
            else -> "To protect security and user rights, we update hoyolab role data through the user's device (client) rather than through the server. This means that if a user does not use Stargazer for a while, it may result in discrepancies in their character count, days active, achievements achieved, and the Forgotten Hall data. Please note that these data are for reference only and are not accurate."
        }
    }


    fun TrailblazerNotiTaggedU(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "O jogador ${1} em \"Discussão de ${2}\" mencionou você! Venha participar da discussão."
            Language.AppLanguage.ZH_CN -> "玩家 ${1} 在【${2}讨论串】提及了您！快来参与讨论吧。"
            Language.AppLanguage.ZH_HK -> "玩家 ${1} 在【${2}討論串】提及了您！快來參與討論吧。"
            else -> "Player ${1} in \"${2}\\'s Discussion\" has mentioned you! Come to join the discussion."
        }
    }

    fun UIDOnlySupportFullUID(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "Suporta apenas pesquisa por UID completo, não é permitido pesquisar por nome ou UID parcial."
            Language.AppLanguage.ZH_CN -> "仅支持查询完整UID，不支持查询昵称或部分UID"
            Language.AppLanguage.ZH_HK -> "僅支持查詢完整UID，不支持查詢暱稱或部分UID"
            else -> "Only support search by full UID, it's not allow to search by name or partly UID."
        }
    }

    fun LoginViaPCToGetCookies(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "Por favor, use um computador para fazer login no hoyolab. Certifique-se de sair e fazer login novamente e siga o vídeo de instruções: https://www.youtube.com/watch?v=CLkhV30kg_A (para copiar, selecione cURL (bash))"
            Language.AppLanguage.ZH_CN -> "请使用计算机登入 hoyolab / 米游社，并确认您已经先注销再登入，并按照以下教学视频获取Cookies : https://www.youtube.com/watch?v=CLkhV30kg_A (记得选取复制cURL (bash))"
            Language.AppLanguage.ZH_HK -> "請使用電腦登入 hoyolab / 米游社，並確認您已經先登出再登入，並按照以下教學影片獲取Cookies : https://www.youtube.com/watch?v=CLkhV30kg_A (記得選取複製cURL (bash))"
            else -> "Please use a computer to log in to hoyolab / 米游社. Make sure you have logout, and login again, then please follow the instructional video: https://www.youtube.com/watch?v=CLkhV30kg_A (for copying, please select cURL (bash))"
        }
    }

    fun SelectServerAndPasteCookies(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "Selecione o servidor e cole seus cookies."
            Language.AppLanguage.ZH_CN -> "请选择服务器并贴上 Cookies。"
            Language.AppLanguage.ZH_HK -> "請選擇伺服器並貼上 Cookies。"
            else -> "Please select the server and paste your Cookies."
        }
    }

    fun CommentOverLimit(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "O Comentário excede o limite máximo (200 caracteres), reduza o comentário antes de postar."
            Language.AppLanguage.ZH_CN -> "输入超过最大值上限（200字元），请减少输入再发布。"
            Language.AppLanguage.ZH_HK -> "輸入超過最大值上限（200字元），請減少輸入再發布。"
            else -> "The input exceeds the maximum limit (200 characters), please reduce the input before posting."
        }
    }

    fun DonationRemoveAds(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "Feito por Coding Band :D"
            Language.AppLanguage.ZH_CN -> "由 Coding Band 制作 :D"
            Language.AppLanguage.ZH_HK -> "由 Coding Band 製作 :D"
            else -> "Made by Coding Band :D"
        }
    }

    fun AboutTheAppContent(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "<h2>Sobre o Stargazer 2</h2><div>Stargazer 2 é um aplicativo assistente Star Rail de terceiros desenvolvido pela Coding Band. Ele fornece funções como consulta de proficiência, ilustração de personagem, consulta de desempenho, lista de classificação, etc... <br>Nosso objetivo é criar um aplicativo de Assistência Star Rail  com experiência móvel nativa para fornecer uma experiência de uso mais conveniente e tranquila.</div><br><h2>Sobre a equipe</h2><div>Coding Band é uma equipe de desenvolvimento que se concentra no desenvolvimento da praticidade, mergulhando na arte e na diversão do software, e atualmente consiste em três membros principais:</div> <div><li>2O48: UI/UX Designer, revisor de arte </li><li>Dalufish: Engenheiro Chefe, desenvolvimento completo </li><li>Voc-夜芷冰：Gerente de Projeto, desenvolvimento completo </li></div><h2>Agradecimentos especiais</h2><div>Agradecimentos especiais a 明治 e a todos os testadores do beta fechado por nos ajudar a melhorar durante o período do beta fechado.<br>jedudu<br>yi_9487<br >appledush#0218<br>shiroweaver<br>_7475<br>rxin.66<br>rover5205<br>rudmon<br>.yanyi<br>mo_yc<br>ryouendragon3369<br>jonahs0202<br>mashujiu<br> yangyangxd<br>sava_tw<br>日輪#1577<br>.yangqin<br>sakura_snow.0w0<br>professionalwindowsexpert<br>dreamawake<br>_d_evil_<br>whitykun<br><br>Stargazer 2 não teria sido lançado em dois meses sem a sua ajuda :D</div><h2>Lembrete caloroso</h2><div>Observe que este projeto e aplicação não pertencem à miHoYo Co., Ltd. Stargazer 2 é apenas um projeto que fornece dados desenvolvidos por fãs. As informações fornecidas neste aplicativo são apenas para referência. Stargazer 2 não garante a exatidão e integridade de qualquer informação contida em qualquer grau. <br><br>Os direitos autorais de cada material usado no projeto 'Stargazer 2' pertencem ao detentor dos direitos autorais.<br> Os direitos autorais do código do projeto 'Stargazer 2' pertencem à Coding Band. </div><br>Alguns dos materiais utilizados neste projeto vêm dos seguintes sites <br>https://starrailstation.com/<br>https://github.com/reko-beep/hsr-data< br>https://honkai-star-rail.fandom.com/wiki/Honkai:_Star_Rail_Wiki<br>https://wiki.biligame.com/sr/首页<br>https://www.prydwen.gg/ star-rail<br>https://api.mihomo.me/<br>https://github.com/Mar-7th"
            Language.AppLanguage.ZH_CN -> "<h2>关于应用</h2><div>Stargazer 2 是由 Coding Band 团队开发的一款星铁第三方助手应用，提供诸如练度查询，角色图鉴，战绩查询和排行榜等功能。<br>我们旨在打造一款原生、移动端体验的星铁助手 App，提供更方便和流畅的使用体验。</div><br><h2>关于团队</h2><div>Coding Band 是一个专注于开发具实用性、钻研软件内艺术和趣味性的开发团队，目前由兩位核心成员组成：</div><div><li>2O48：UI/UX 设计师，美术把关</li><li>Dalufish：首席工程师，全端开发</li><li>Voc-夜芷冰: 项目负责人，全端开发</li></div><h2>特别感谢</h2><div>特别感谢明治及所有封测测试员，在封测期协助我们完善 Stargazer 2：<br><br>jedudu<br>yi_9487<br>appledush#0218<br>shiroweaver<br>_7475<br>rxin.66<br>rover5205<br>rudmon<br>.yanyi<br>mo_yc<br>ryouendragon3369<br>jonahs0202<br>mashujiu<br>yangyangxd<br>sava_tw<br>日轮#1577<br>.yangqin<br>sakura_snow.0w0<br>professionalwindowsexpert<br>dreamawake<br>_d_evil_<br>whitykun<br>Dalufishe<br><br>没有你们 Stargazer 2 根本不可能在两个月内发布正式版 :D<br></div><h2>温馨提醒</h2><div>请注意：本项目及应用程序并不属于 miHoYo Co., Ltd. 且并未获得其认可。Stargazer 2（星穹观星者2）仅为一款由粉丝自行开发之数据提供 App，本应用程序所提供的数据只作参考用途。 不会对其所载任何数据的准确性及完整作任何程度保证。<br><br>「星穹观星者 2」项目内使用之各素材版权归该版权所有者拥有。<br>「星穹观星者 2」项目代码著作权归 Coding Band 所有。</div><br>部分在本项目使用的素材来自以下网站：<br>https://starrailstation.com/<br>https://github.com/reko-beep/hsr-data<br>https://honkai-star-rail.fandom.com/wiki/Honkai:_Star_Rail_Wiki<br>https://wiki.biligame.com/sr/首页<br>https://www.prydwen.gg/star-rail<br>https://api.mihomo.me/<br>https://github.com/Mar-7th<br>"
            Language.AppLanguage.ZH_HK -> "<h2>關於應用</h2><div>Stargazer 2 是由 Coding Band 團隊開發的一款星鐵第三方助手應用，提供諸如練度查詢，角色圖鑑，戰績查詢和排行榜等功能。<br>我們旨在打造一款原生、移動端體驗的星鐵助手 App，提供更方便和流暢的使用體驗。</div><br><h2>關於團隊</h2><div>Coding Band 是一個專注於開發具實用性、鑽研軟體内藝術和趣味性的開發團隊，目前由兩位核心成員組成：</div><div><li>2O48：UI/UX 設計師，美術把關</li><li>Voc-夜芷冰: 專案負責人，全端開發</li></div><h2>特別感謝</h2><div>特別感謝明治及所有封測測試員，在封測期協助我們完善 Stargazer 2：<br><br>jedudu<br>yi_9487<br>appledush#0218<br>shiroweaver<br>_7475<br>rxin.66<br>rover5205<br>rudmon<br>.yanyi<br>mo_yc<br>ryouendragon3369<br>jonahs0202<br>mashujiu<br>yangyangxd<br>sava_tw<br>日輪#1577<br>.yangqin<br>sakura_snow.0w0<br>professionalwindowsexpert<br>dreamawake<br>_d_evil_<br>whitykun<br>Dalufishe<br><br>沒有你們 Stargazer 2 根本不可能在兩個月內發布正式版 :D<br></div><h2>溫馨提醒</h2><div>請注意：本專案及應用程式並不屬於 miHoYo Co., Ltd. 且並未獲得其認可。Stargazer 2（星穹觀星者2）僅為一款由粉絲自行開發之數據提供 App，本應用程式所提供的資料只作參考用途。 不會對其所載任何資料的準確性及完整作任何程度保證。<br><br>「星穹觀星者 2」專案内使用之各素材版權歸該版權所有者擁有。<br>「星穹觀星者 2」專案代碼著作權歸 Coding Band 所有。</div><br>部分在本專案使用的素材來自以下網站：<br>https://starrailstation.com/<br>https://github.com/reko-beep/hsr-data<br>https://honkai-star-rail.fandom.com/wiki/Honkai:_Star_Rail_Wiki<br>https://wiki.biligame.com/sr/首页<br>https://www.prydwen.gg/star-rail<br>https://api.mihomo.me/<br>https://github.com/Mar-7th<br>"
            else -> "<h2>About Stargazer 2</h2><div>Stargazer 2 is a third-party Star Rail assistant application developed by Coding Band. It provides functions such as proficiency inquiry, character illustration, performance inquiry, ranking list, etc... <br> We aim to create a Star Rail Assistant App with native, mobile experience to provide a more convenient and smooth usage experience.</div><br><h2>About Team</h2><div>Coding Band is a development team that focuses on developing practicality, delving into the art and fun of software, and currently consists of two members:</div><div><li>2O48: UI/UX Designer, art reviewer </li><li>Voc-夜芷冰: Project Manager, full-end development </li></div><h2>Special Thanks</h2><div> Special thanks to 明治and all closed beta testers for helping us improve during the closed beta period.<br>jedudu<br>yi_9487<br>appledush#0218<br>shiroweaver<br>_7475<br>rxin.66<br>rover5205<br>rudmon<br>.yanyi<br>mo_yc<br>ryouendragon3369<br>jonahs0202<br>mashujiu<br>yangyangxd<br>sava_tw<br>日輪#1577<br>.yangqin<br>sakura_snow.0w0<br>professionalwindowsexpert<br>dreamawake<br>_d_evil_<br>whitykun<br>Dalufishe<br><br>Stargazer 2 would not have been released in two months without your help :D</div><h2>Warm Reminder</h2><div>Please note that, this project and application do not belong to miHoYo Co., Ltd. and have not been approved. Stargazer 2 is only a project that provides data developed by fans. The information provided in this app is for reference only. Stargazer 2 will not guarantee the accuracy and completeness of any information it contains to any degree. <br><br> The copyright of each material used in the 'Stargazer 2' project belongs to the copyright holder.<br> The copyright of the 'Stargazer 2' project code belongs to Coding Band. </div><br> Some of the materials used in this project come from the following websites <br>https://starrailstation.com/<br>https://github.com/reko-beep/hsr-data<br>https://honkai-star-rail.fandom.com/wiki/Honkai:_Star_Rail_Wiki<br>https://wiki.biligame.com/sr/首页<br>https://www.prydwen.gg/star-rail<br>https://api.mihomo.me/<br>https://github.com/Mar-7th"
        }
    }

    fun LoginHint(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "Observação: atualmente, o login em plataformas de terceiros não é compatível. Recomendamos que você vá para Configurações do jogo -> Configurações da conta -> Central do usuário -> Bind Pass para obter a melhor experiência."
            Language.AppLanguage.ZH_CN -> "请注意：目前暂不支援第三方平台登入，我们建议您到游戏内设定->帐户设定->使用者中心->绑定通行证，以获得最佳的使用体验。"
            Language.AppLanguage.ZH_HK -> "請注意：目前暫不支援第三方平台登入，我們建議您到遊戲内設定->帳戶設定->使用者中心->綁定通行證，以獲得最佳的使用體驗。"
            else -> "Please note: Currently, third-party platform login is not supported. We recommend that you go to in-game Settings -> Account Settings -> User Center -> Bind Pass to get the best experience."
        }
    }

    fun LoginPolicy(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "<p>Prezado usuário, o login na função da conta Hoyoverse requer que seus cookies sejam armazenados no dispositivo, portanto, devemos obter sua autorização antes de prosseguir. Garantimos que seus cookies serão salvos apenas localmente e usados apenas entre você e a Hoyoverse. Além disso, também armazenaremos dados de jogos (mas não cookies) no servidor para fornecer serviços (como classificação MDC).</p><br/><p>Observação: primeiro, não podemos obter as informações de conta e senha. A inserção da senha da conta é utilizada apenas para fornecer uma forma mais conveniente de obter cookies, substituindo a inconveniência de pesquisar e inserir cookies manualmente. Em segundo lugar, garantimos que os cookies são armazenados apenas no dispositivo do utilizador (cliente) (seja obtido manualmente ou através da página de login). Você pode visualizar o código-fonte relevante no repositório GitHub da Stargazer para verificar esta mensagem. Portanto, não seremos responsáveis pelo roubo da senha da conta de qualquer usuário."
            Language.AppLanguage.ZH_CN -> "<p>亲爱的用户您好，登录米游社 / Hoyoverse 账号功能需要于设备端存储您的 cookies，因此我们必须获得您的授权才可以进行。我们保证您的 cookies 仅会保存在本地并仅于您和米游社 / Hoyoverse 间使用。此外，我们还会将游戏数据（但不包括cookies）存储到服务器以提供服务（如混沌回忆排行）。</p><br/><p>注意：第一点，我们无法获取您输入的账号密码信息，输入账密仅用于提供一种更便利的方式在取得 cookies，替代手动查找，输入 cookies 的不便利性。第二，我们保证 cookies 仅存储在用户设备端（客户端）（不管是手动还是透过登入页面获取），您可以在 Stargazer 的 Github 仓库查看相关源代码验证此讯息。因此，我们不会为任何本 App 用户的帐密被盗用等行为负责。"
            Language.AppLanguage.ZH_HK -> "<p>親愛的用戶您好，登錄米游社 / Hoyoverse 帳號功能需要於設備端存儲您的 cookies，因此我們必須獲得您的授權才可以進行。我們保證您的 cookies 僅會保存在本地並僅於您和米游社 / Hoyoverse 間使用。此外，我們還會將遊戲數據（但不包括cookies）存儲到伺服器以提供服務（如混沌回憶排行）。</p><br/><p>注意：第一點，我們無法獲取您输入的帳號密碼信息，輸入帳密僅用於提供一種更便利的方式在取得 cookies，替代手動查找，輸入 cookies 的不便利性。第二，我們保證 cookies 僅存儲在用戶設備端（客戶端）（不管是手動還是透過登入頁面獲取），您可以在 Stargazer 的 Github 倉庫查看相關源代碼驗證此訊息。因此，我們不會為任何本 App 用户的帳密被盜用等行為負責。"
            else -> "<p>Dear user, logging in to the Miyoushe/Hoyoverse account function requires your cookies to be stored on the device, so we must obtain your authorization before we can proceed. We guarantee that your cookies will only be saved locally and used only between you and 米遊社/Hoyoverse. In addition, we will also store game data (but not cookies) on the server to provide services (such as MOC ranking).</p><br/><p>Note: First, we cannot obtain the account and password information you enter. Entering the account password is only used to provide a more convenient way to obtain cookies, replacing the inconvenience of manually searching and entering cookies. Second, we ensure that cookies are only stored on the user device (client) (whether obtained manually or through the login page). You can view the relevant source code in Stargazer's GitHub repository to verify this message. Therefore, we will not be responsible for the theft of any user's account password."
        }
    }

    fun GithubStarPlease(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "Se você acha que este projeto é útil, dê-nos uma estrela no GitHub, obrigado!"
            Language.AppLanguage.ZH_CN -> "如果这个项目有帮助到您，请在GitHub给我们一颗星星，谢谢！"
            Language.AppLanguage.ZH_HK -> "如果這個專案有幫助到您，請在GitHub給我們一顆星星，謝謝！"
            else -> "If you think this project is helpful, please give us a star in GitHub, thx!"
        }
    }

    @Deprecated("This is a demo function for copying, do not use it.")
    fun demo(appLanguage: Language.AppLanguage = Language.AppLanguageInstance): String {
        return when (appLanguage) {
            Language.AppLanguage.PT -> "demotext"
            Language.AppLanguage.ZH_CN -> "demotext"
            Language.AppLanguage.ZH_HK -> "demotext"
            else -> "demotext"
        }
    }

}