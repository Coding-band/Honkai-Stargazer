import AsyncStorage from "@react-native-async-storage/async-storage";
import DeviceInfo from "react-native-device-info";

let getDynamicIsland = DeviceInfo.hasDynamicIsland();
let getNotch = DeviceInfo.hasNotch();

export function initUIData(getDynamicIslandx : boolean , getNotchx : boolean){
    if(getDynamicIslandx !== undefined){
        getDynamicIsland = getDynamicIslandx;
    }
    if(getNotchx !== undefined){
        getNotch = getNotchx;
    }
}

//對於ScrollView高度的動態設定
export const dynamicHeightScrollView = (
    getDynamicIsland ? "z-30 pt-[147px] pb-0  px-[8px] " 
    : getNotch ? "z-30 pt-[127px] pb-0  px-[8px] "
    : "z-30 pt-[112px] pb-0  px-[8px] "
);

//對於ScrollView + 左右Padding高度的動態設定
export const dynamicHeightScrollViewLRPadding = (
    getDynamicIsland ? "z-30 py-[147px] px-[17px] pb-0" 
    : getNotch ? "z-30 py-[127px] px-[17px] pb-0"
    : "z-30 py-[112px] px-[17px] pb-0"
);
//對於設定頁ScrollView高度的動態設定
export const dynamicHeightSettingScrollView = (
    getDynamicIsland ? "z-30 h-screen py-[130px] px-4 pb-0" 
    : getNotch ? "z-30 h-screen py-[110px] px-4 pb-0"
    : "z-30 h-screen py-[95px] px-4 pb-0"
);

//對於排行榜ScrollView高度的動態設定
export const dynamicHeightLeaderScrollView = (
    getDynamicIsland ? "p-4 pb-0 pt-[147px]"
    : getNotch ? "p-4 pb-0 pt-[127px]"
    : "p-4 pb-0 pt-[112px]"
);
//對於怪物列表高度的動態設定
export const dynamicHeightMonsterScrollView = (
    getDynamicIsland ? "z-30 pt-[130px] pb-0"
    : getNotch ? "z-30 pt-[110px] pb-0"
    : "z-30 pt-[95px] pb-0"
);

//對於派遣探索ScrollView高度的動態設定
export const dynamicHeightEpditScrollView = (
    getDynamicIsland ? "h-screen p-4 pb-0 mt-[130px]"
    : getNotch ? "h-screen p-4 pb-0 mt-[110px]"
    : "h-screen p-4 pb-0 mt-[95px]"
);

//對於Header高度的動態設定 (數值)
export const dynamicHeightHeaderValue = (
    getDynamicIsland ? 130
    : getNotch ? 110 
    : 95
);

//對於Header高度的動態設定
export const dynamicHeightHeader = (
    getDynamicIsland ? "w-full h-[130px]" 
    : getNotch ? "w-full h-[110px]" 
    : "w-full h-[95px]"
);

//對於ListAction高度的動態設定
export const dynamicHeightListAction = (
    getDynamicIsland ? "w-full h-[46px] absolute bottom-12 z-50"
    : getNotch ? "w-full h-[46px] absolute bottom-12 z-50"
    : "w-full h-[46px] absolute bottom-8 z-50"
);

//對於UIDSearchView高度的動態設定
export const dynamicHeightUIDSearchView = (
    getDynamicIsland ? "h-screen p-4 pb-0 mt-[130px]"
    : getNotch ? "h-screen p-4 pb-0 mt-[110px]"
    : "h-screen p-4 pb-0 mt-[95px]"
);

//對於EventList高度的動態設定 (活動列表頁)
export const dynamicHeightEventList = (
    getDynamicIsland ? "z-30 py-[147px] px-[17px] pb-0"
    : getNotch ? "z-30 py-[127px] px-[17px] pb-0"
    : "z-30 py-[112px] px-[17px] pb-0"
);

//對於EventWebview高度的動態設定 (活動內容頁)
export const dynamicHeightEventWebview = (
    getDynamicIsland ? "z-30 py-[130px] pb-0"
    : getNotch ? "z-30 py-[110px] pb-0"
    : "z-30 py-[95px] pb-0"
);

//對於UserInfo AnimatedView的paddingTop的動態設定 (活動內容頁)
export const dynamicHeightUserInfoAnimView = (
    getDynamicIsland ? "mt-32"
    : getNotch ? "mt-28"
    : "mt-24"
);


//對於WallpaperChanger的View動態設定 (活動內容頁)
export const dynamicHeightWallpaperChangerView = (
    getDynamicIsland ? "w-full h-full z-30 mt-[130px]"
    : getNotch ? "w-full h-full z-30 mt-[110px]"
    : "w-full h-full z-30 mt-[95px]"
);

//對於無語狀態下的400動態設定 (桌布更換)
export const dynamicHeightWallpaperSwiper = (
    getDynamicIsland ? 400
    : getNotch ? 370
    : 340
);

//對於登入頁面Webview高度動態設定
export const dynamicHeightLoginWebview = (
    getDynamicIsland ? "mt-[130px]"
    : getNotch ? "mt-[110px]"
    : "mt-[95px]"
);