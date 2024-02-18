import { View, Text, TouchableOpacity, Linking, Platform } from "react-native";
import React, { useEffect, useState } from "react";
import { Image, ImageBackground } from "expo-image";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import Toast from "../../../../utils/toast/Toast";
import Purchases, { LOG_LEVEL, PRODUCT_CATEGORY, PurchasesStoreProduct } from "react-native-purchases";
import { ENV } from "../../../../../app.config";
import { PURCHASE_APPLE_KEY, PURCHASE_APPLE_KEY_BETA, PURCHASE_GOOGLE_KEY, purchaseItemID_AppStore, purchaseItemID_AppStoreBETA, purchaseItemID_GooglePlay } from "../../../../constant/billing";
import { GestureResponderEvent } from "react-native-modal";

export default function DonateTab() {
  const { language } = useAppLanguage();

  //Donation
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
      if(Platform.OS !== 'ios'){
        Linking.openURL("https://www.buymeacoffee.com/codingband");
      }else{
        if(ENV === "beta"){ Toast(LOCALES[language].ErrorDonationInBeta); return; }
        try {
          await Purchases.purchaseStoreProduct(productList[itemId]);
        } catch (e : any) {
          if(!e.userCancelled){
            Toast(
              e.message === "TypeError: Cannot read property 'identifier' of undefined" 
              ? LOCALES[language].ErrorIdentifier
              
              : e.message === "Error: The operation is already in progress for this product." 
              ? LOCALES[language].ErrorIdentifier
    
              : e.message
            );
          }
        }
      }
    }

  return (
    <ImageBackground
      source={require("../../../../../assets/ads/donate_ad_bg.png")}
      className="w-full h-full"
    >
      <View className="absolute w-full h-full opacity-80 bg-[#FFF]" />
      <View className="pt-[6px]" style={{ alignItems: "center", gap: 8 }}>
        <Text className="text-black font-[HY65]">
          {LOCALES[language].PlsDonateUs}
        </Text>
        <View style={{ flexDirection: "row", gap: 14 }}>
          <DonateBtn pressEvent={() => doPurchasing(0)}>$2</DonateBtn>
          <DonateBtn pressEvent={() => doPurchasing(1)}>$5</DonateBtn>
          <DonateBtn pressEvent={() => doPurchasing(2)}>$10</DonateBtn>
          <DonateBtn pressEvent={() => doPurchasing(3)}>$20</DonateBtn>
          {/* <DonateBtn>{LOCALES[language].DonateInRandomCount}</DonateBtn> */}
        </View>
        {/* <Text className="text-black font-[HY65]"></Text> */}
      </View>
    </ImageBackground>
  );
}

const DonateBtn = (props: { children: string , pressEvent : any}) => {
  return (
    <TouchableOpacity
      onPress={props.pressEvent}
      activeOpacity={0.65}
      className="w-[77px] h-9 bg-[#404165] rounded-[49px] border-2 border-[#FFFFFF30]"
      style={{ alignItems: "center", justifyContent: "center" }}
    >
      <Text className="font-[HY65] text-text">{props.children}</Text>
    </TouchableOpacity>
  );
};
