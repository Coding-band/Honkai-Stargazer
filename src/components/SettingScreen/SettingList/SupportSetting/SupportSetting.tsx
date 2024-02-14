import { View, Text, Dimensions, Platform, Share } from "react-native";
import React, { useEffect, useState } from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import PopUpCard from "../../../global/PopUpCard/PopUpCard";
import ReactNativeModal from "react-native-modal";
import { HtmlText } from "@e-mine/react-native-html-text";
import Button from "../../../global/Button/Button";
import TextButton from "../../../global/TextButton/TextButton";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import Toast from "../../../../utils/toast/Toast";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import Purchases, { LOG_LEVEL, PRODUCT_CATEGORY, PURCHASE_TYPE, PurchasesStoreProduct } from "react-native-purchases";
import { PurchasesPackage } from "react-native-purchases";
import { PURCHASE_APPLE_KEY, PURCHASE_APPLE_KEY_BETA, PURCHASE_GOOGLE_KEY, purchaseItemID_AppStore, purchaseItemID_AppStoreBETA, purchaseItemID_GooglePlay } from "../../../../constant/billing";
import { ENV } from "../../../../../app.config";
import * as Clipboard from "expo-clipboard";

export default function SupportSetting() {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  const [openDonate, setOpenDonate] = useState(false);
  
  // - State for displaying an overlay view
  const [productList, setProductList] = useState<PurchasesStoreProduct[]>([]);
  useEffect(() => {
    const products = async () => {
      // Purchases
      Purchases.setLogLevel(LOG_LEVEL.VERBOSE);
      if (Platform.OS === 'ios') {
        if(ENV === "beta"){
          Purchases.configure({apiKey: PURCHASE_APPLE_KEY_BETA});
          setProductList((await Purchases.getProducts(purchaseItemID_AppStoreBETA, PRODUCT_CATEGORY.NON_SUBSCRIPTION)).sort((a,b) => (a.price - b.price)))
        }else{
          Purchases.configure({apiKey: PURCHASE_APPLE_KEY});
          setProductList((await Purchases.getProducts(purchaseItemID_AppStore, PRODUCT_CATEGORY.NON_SUBSCRIPTION)).sort((a,b) => (a.price - b.price)))
        }
      } else if (Platform.OS === 'android') {
        Purchases.configure({apiKey: PURCHASE_GOOGLE_KEY});
        setProductList((await Purchases.getProducts(purchaseItemID_GooglePlay, PRODUCT_CATEGORY.NON_SUBSCRIPTION)).sort((a,b) => (a.price - b.price)))
      }
    }

    products()
  }, [])

  const doPurchasing = async(itemId : number) => {
    try {
      const { customerInfo, productIdentifier } = await Purchases.purchaseStoreProduct(productList[itemId]);
      console.log(customerInfo)
      console.log(productIdentifier)
    } catch (e : any) {
      if(!e.userCancelled){
        Toast("Error : "+e);
      }
    }
  }

  return (
    <>
      <SettingGroup title={LOCALES[language].SupportUs}>
        <SettingItem
          type="navigation"
          title={LOCALES[language].Donation}
          onNavigate={() => {
            setOpenDonate(true);
          }}
        />
        { 
        <SettingItem
          type="navigation"
          title={LOCALES[language].InviteOthers}
          onNavigate={async() => {
            // @ts-ignore
            try{
              await Share.share({
                message : LOCALES[language].ShareToOthers
                  .replace("${AppStoreLink}","https://apps.apple.com/us/app/stargazer-2-unofficial/id6474837377")
                  .replace("${PlayStoreLink}","https://play.google.com/store/apps/details?id=com.voc.honkai_stargazer_gp")
              })
            }catch(error){
              Toast.FailToCopy(language)
            }
              /*
              try {
              await Clipboard.setStringAsync(
                (Platform.OS === "ios" 
                ? "https://apps.apple.com/us/app/stargazer-2-unofficial/id6474837377" 
                : "https://play.google.com/store/apps/details?id=com.voc.honkai_stargazer_gp"
                )
              );
              Toast.CopyToClipboard(language);
            } catch (e) {
              Toast.FailToCopy(language);
            }
              */
          }}
        />
        
        }
      </SettingGroup>
      <ReactNativeModal
        useNativeDriverForBackdrop
        statusBarTranslucent
        deviceHeight={Dimensions.get("screen").height}
        isVisible={openDonate}
      >
        <PopUpCard
          onClose={() => {
            setOpenDonate(false);
          }}
          title={LOCALES[language].Donation}
          content={
            <View className="pt-2 py-4 px-4" style={{ gap: 10 }}>
              <HtmlText style={{ fontFamily: "HY65" }}>
                {LOCALES[language].DonationDesc}
              </HtmlText>
              { /* 暫時沒有廣告功能
                <HtmlText style={{ fontFamily: "HY65" }}>
                {`进行任意一项捐赠即可<span style="color:#DD8200;">免除所有廣告</span>。`}
              </HtmlText>
              */
              }
              <View style={{ gap: 12 }}>
                <TextButton hasShadow={false} width={"100%"} height={46} onPress={() => doPurchasing(0)}>
                  {LOCALES[language].DonateUs + " $2"}
                </TextButton>
                <TextButton hasShadow={false} width={"100%"} height={46} onPress={() => doPurchasing(1)}>
                  {LOCALES[language].DonateUs + " $5"}
                </TextButton>
                <TextButton hasShadow={false} width={"100%"} height={46} onPress={() => doPurchasing(2)}>
                  {LOCALES[language].DonateUs + " $10"}
                </TextButton>
                <TextButton hasShadow={false} width={"100%"} height={46} onPress={() => doPurchasing(3)}>
                  {LOCALES[language].DonateUs + " $20"}
                </TextButton>
                <TextButton hasShadow={false} width={"100%"} height={46} onPress={() => doPurchasing(4)}>
                  {LOCALES[language].DonateUs + " $50"}
                </TextButton>
                <TextButton hasShadow={false} width={"100%"} height={46} onPress={() => doPurchasing(5)}>
                  {LOCALES[language].DonateUs + " $99"}
                </TextButton>
                {/** 退款請求相關請到我們的Discord伺服器 : https://discord.gg/uXatcbWKv2，向@vocaloid2048 / @yukina4096 提出 */}
                {/* 恢復捐贈是指，如果之前捐贈過就直接獲得捐贈權益 (暫未開放，如有需要，請到我們的Discord伺服器 : https://discord.gg/uXatcbWKv2，向@vocaloid2048 / @yukina4096 提出)
                  <TextButton hasShadow={false} width={310} height={46}>
                    恢复捐赠
                  </TextButton>
                */
                }
                {/* 暫未開放名單功能
                  <TextButton hasShadow={false} width={310} height={46}>
                    所有捐赠名单
                  </TextButton>
            */}
              </View>
            </View>
          }
        />
      </ReactNativeModal>
    </>
  );
}
