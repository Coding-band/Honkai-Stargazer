
const PULL_LIMIT_CHAR = "LIMIT_CHAR" //限定角色池
const PULL_LIMIT_LIGHTCONE = "LIMIT_LC" //限定光錐池
const PULL_STATIC = "STATIC" //常駐池

const itemRare3 = [
    "鋒鏑","物穰","天傾","琥珀",
    "幽邃","齊頌","智庫","離弦",
    "嘉果","樂圮","戍禦","輪契",
    "靈鑰","相抗","俱歿","開疆",
    "匿影","淵環","蕃息","調和",
    "睿見"
]

const itemRare4LC = [
    "一場術後對話","晚安與睡顏","餘生的第一天","唯有沉默",
    "記憶中的模樣","鼴鼠黨歡迎你","「我」的誕生","同一種心情",
    "獵物的視線","朗道的選擇","論劍","與行星相會",
    "秘密誓心","別讓世界靜下來","此時恰好","決心如汗珠般閃耀",
    "宇宙市場趨勢","按個追蹤吧!","舞!舞!舞!","在藍天下",
    "天才們的休憩",
]

const itemRare4Char = [
    "素裳","黑塔","娜塔莎","希露瓦",
    "佩拉","桑博","虎克","艾絲妲",
    "青雀","阿蘭","馭空","盧卡",
    "丹恆","寒鴉","雪衣","玲可",
    "三月七"
]


const itemRare5 = [
    "姬子","瓦爾特","布洛妮婭","傑帕德",
    "克拉拉","彥卿","白露"
]

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
    const pullNormalRate = [0.943, 0.051, 0.006]
    const pullPityRate = [0.854, 0.13, 0.016]
    let pullRate = pullNormalRate
    let pullsAfterRare4 = 0
    let pullsAfterRare5 = 0
    let isMustGetRare5  = false
    let isMustGetRare4  = false

    const itemUpRare4 = ["桂乃芬","米沙","停雲"]
    const itemUpRare5 = ["黑天鵝"]

    for(let x = 0 ; x < 180 ; x++){
        const randNum = Math.random(Date.now())
        
        if(randNum <= pullRate[0] && pullsAfterRare4 < 9 && pullsAfterRare5 < 89){
            //3星
            const randNumRare3 = Math.trunc(Math.random(Date.now()) * itemRare3.length);
            pullArray.push(headerRare3n+itemRare3[randNumRare3])

            pullsAfterRare4 ++
            pullsAfterRare5 ++
        }else if (randNum <= pullRate[0] + pullRate[1] && pullsAfterRare5 < 89){
            //4星
            const randNum4s = Math.random(Date.now())
            if(randNum4s < 0.5 && !isMustGetRare4){
                const randNumRare4 = Math.trunc(Math.random(Date.now()) * (itemRare4Char.length + itemRare4LC.length));
                pullArray.push(headerRare4n+(randNumRare4 < itemRare4Char.length ? itemRare4Char[randNumRare4] : itemRare4LC[randNumRare4-itemRare4Char.length]))
                isMustGetRare4 = true
            }else{
                const randNumRare4 = Math.trunc(Math.random(Date.now()) * itemUpRare4.length);
                pullArray.push(headerRare4u+itemUpRare4[randNumRare4])
                isMustGetRare4 = false
            }

            pullsAfterRare4 = 0
            pullsAfterRare5 ++
        }else{
            //5星
            const randNum5s = Math.random(Date.now())
            if(randNum5s < 0.5 && !isMustGetRare5){
                //歪咗
                const randNumRare5 = Math.trunc(Math.random(Date.now()) * (itemRare5.length));
                pullArray.push(headerRare5n+itemRare5[randNumRare5])
                pullRate = pullPityRate
                isMustGetRare5 = true
            }else{
                //冇歪
                pullArray.push(headerRare5u+itemUpRare5[0])
                isMustGetRare5 = false
            }

            pullsAfterRare4 ++
            pullsAfterRare5 = 0
        }
    }
    for(let c = 0 ; c < pullArray.length ; c++){
        console.log((c < 100 ? "0" : "") + (c < 10 ? "0" : "") + c +"\t"+pullArray[c])
    }
}

testPulls()