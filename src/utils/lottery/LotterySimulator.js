
const PULL_LIMIT_CHAR = "LIMIT_CHAR" //限定角色池
const PULL_LIMIT_LIGHTCONE = "LIMIT_LC" //限定光錐池
const PULL_STATIC = "STATIC" //常駐池

//三星常駐
const itemRare3 = [
    "鋒鏑","物穰","天傾","琥珀",
    "幽邃","齊頌","智庫","離弦",
    "嘉果","樂圮","戍禦","輪契",
    "靈鑰","相抗","俱歿","開疆",
    "匿影","淵環","蕃息","調和",
    "睿見"
]

//四星光錐常駐
const itemRare4LC = [
    "一場術後對話","晚安與睡顏","餘生的第一天","唯有沉默",
    "記憶中的模樣","鼴鼠黨歡迎你","「我」的誕生","同一種心情",
    "獵物的視線","朗道的選擇","論劍","與行星相會",
    "秘密誓心","別讓世界靜下來","此時恰好","決心如汗珠般閃耀",
    "宇宙市場趨勢","按個追蹤吧!","舞!舞!舞!","在藍天下",
    "天才們的休憩",
]

//四星角色常駐
const itemRare4Char = [
    "素裳","黑塔","娜塔莎","希露瓦",
    "佩拉","桑博","虎克","艾絲妲",
    "青雀","阿蘭","馭空","盧卡",
    "丹恆","寒鴉","雪衣","玲可",
    "三月七"
]

//五星角色常駐
const itemRare5Char = [
    "姬子","瓦爾特","布洛妮婭","傑帕德",
    "克拉拉","彥卿","白露"
]

//五星光錐常駐
const itemRare5LC = [
    "姬子","瓦爾特","布洛妮婭","傑帕德",
    "克拉拉","彥卿","白露"
]

//限定角色
const pullNormalRateChar = [0.943, 0.051, 0.006]
const pullPityRateChar = [0.854, 0.13, 0.016]
const pullPityOrNotChar = 0.5
const pullPityMaxChar = 90

//限定光錐
const pullNormalRateLC = [0.943, 0.051, 0.006]
const pullPityRateLC = [0.854, 0.13, 0.016]
const pullPityOrNotLC = 0.75
const pullPityMaxLC = 80

//常駐
const pullNormalRateStatic = [0.943, 0.051, 0.006]
const pullPityRateStatic = [0.854, 0.13, 0.016]

const headerRare3n = "\x1b[42m【3✦常駐】\x1b[0m"
const headerRare4n = "\x1b[45m【4✦常駐】\x1b[0m"
const headerRare4u = "\x1b[45m【4✦限定】\x1b[0m"
const headerRare5n = "\x1b[43m【5✦常駐】\x1b[0m"
const headerRare5u = "\x1b[43m【5✦限定】\x1b[0m"

//pullInfo : {"special_rare5": ["Ruan Mei"], "special_rare4": ["March 7th", "Tingyun", "Xueyi"]}
function makePulls(pullInfo, pullType, pullCount){
    
}

function testPulls(){
    let pullArray = []
    let pullNormalRate = pullNormalRateChar
    let pullPityRate = pullPityRateChar
    let pullRate = pullNormalRate
    let pullPityOrNot = pullPityOrNotChar
    let pullNormalSet = itemRare3
    let pullUpSet = itemRare3
    let pullPityMax = pullPityMaxChar
    let pullsAfterRare4 = 0
    let pullsAfterRare5 = 0
    let isMustGetRare5  = false
    let isMustGetRare4  = false
    let pulledItemType = "THREE"

    const itemUpRare4 = ["桂乃芬","米沙","停雲"]
    const itemUpRare5 = ["黑天鵝"]

    for(let x = 0 ; x < 180 ; x++){
        const randNum = Math.random(Date.now())
        //75 + Math.random(Date.now())* 15 不應該出現 但爲了避免過高機率是89
        if(randNum <= pullRate[0] && pullsAfterRare4 < 9 && pullsAfterRare5 < pullPityMax - 15 + Math.random(Date.now())* 15 ){
            pulledItemType = "THREE";
            pullNormalSet = itemRare3
            pullUpSet = itemRare3
        }else if(randNum <= pullRate[0] + pullRate[1] && pullsAfterRare5 < pullPityMax - 15 + Math.random(Date.now())* 15){
            pulledItemType = "FOUR";
            pullNormalSet = itemRare4Char.concat(itemRare4LC)
            pullUpSet = itemUpRare4
            isMustGetRare4 = !isMustGetRare4
        }else{
            pulledItemType = "FIVE";
            pullNormalSet = itemRare5Char
            pullUpSet = itemUpRare5
            isMustGetRare5 = !isMustGetRare5
            pullRate = (isMustGetRare5 ? pullPityRate : pullNormalRate)
        }

        (pulledItemType === "FIVE" ? console.log("距離上一次5星 "+pullsAfterRare5) : "")

        pullsAfterRare4 = (pulledItemType === "FOUR" ? 0 : pullsAfterRare4 + 1)
        pullsAfterRare5 = (pulledItemType === "FIVE" ? 0 : pullsAfterRare5 + 1)
        
        const randPityOrNot = Math.random(Date.now())
        const randIndex = Math.trunc(Math.random(Date.now()) * (
            (isMustGetRare4 && pulledItemType === "FOUR" || isMustGetRare5 && pulledItemType === "FIVE" ? pullUpSet.length : (randPityOrNot < pullPityOrNot ? pullNormalSet.length : pullUpSet.length))
        ));

        pullArray.push((
                pulledItemType === "THREE" && true ? headerRare3n
                : (pulledItemType === "FOUR") ? ((isMustGetRare4) ? headerRare4u : (randPityOrNot < pullPityOrNot ? headerRare4n : headerRare4u)) 
                : ((isMustGetRare5) ? headerRare5u : (randPityOrNot < pullPityOrNot ? headerRare5n : headerRare5u)) 
            ) + ((randPityOrNot < pullPityOrNot) ? ((isMustGetRare4 && pulledItemType === "FOUR" || isMustGetRare5 && pulledItemType === "FIVE") ? pullUpSet : pullNormalSet) : pullUpSet)[randIndex]
        )
    }
    
    for(let c = 0 ; c < pullArray.length ; c++){
        console.log((c < 100 ? "0" : "") + (c < 10 ? "0" : "") + c +"\t"+pullArray[c])
    }
}

testPulls()