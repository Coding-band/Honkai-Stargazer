import React, { useState, useEffect } from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import Toast from "../../../../utils/toast/Toast";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import formatLocale from "../../../../utils/format/formatLocale";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import useIsShowUserInfo from "../../../../firebase/hooks/User/useIsShowUserInfo";
import ReactNativeModal from "react-native-modal";
import { Dimensions, Text, View } from "react-native";
import PopUpCard from "../../../global/PopUpCard/PopUpCard";
import TextButton from "../../../global/TextButton/TextButton";
import { TextInput } from "react-native";
import useLocalState from "../../../../hooks/useLocalState";
import DeviceInfo from "react-native-device-info";
import { initUIData } from "../../../../constant/ui";

export default function UISetting() {
  const { language } = useAppLanguage();

  //確認是否已經初始化了
  const [isInited, setInited] = useLocalState<any>(
    "user-ui-custom-inited",
    false
  );

  //讓用戶自己選擇是否開啓瀏海屏適配（默認依照DeviceInfo的列表開啓）
  const [isCustomNotch, setCustomNotch] = useLocalState<any>(
    "user-ui-custom-notch",
    false
  );

  //讓用戶自己選擇是否開啓動態島適配（默認依照DeviceInfo的列表開啓）
  const [isCustomDynamicIsland, setCustomDynamicIsland] = useLocalState<any>(
    "user-ui-custom-dynamic-island",
    false
  );

  useEffect(() => {
    if(!isInited){
      setInited(true)
      setCustomNotch(DeviceInfo.hasNotch())
      setCustomDynamicIsland(DeviceInfo.hasDynamicIsland())
    }
  })

  return (
      <>
        <SettingGroup
          title={LOCALES[language].AccountSetup}
        >
          <SettingItem
              type="list"
              title={LOCALES[language].UserCustomHasNotch}
              list={[
                {
                  value: true,
                  name: LOCALES[language].SettingPersonalPageShow,
                },
                {
                  value: false,
                  name: LOCALES[language].SettingPersonalPageDisable,
                },
              ]}
              value={isCustomNotch}
              onChange={(v) => {
                setCustomNotch(v)
                setTimeout(() => {
                  initUIData(isCustomDynamicIsland,v)
                },500)
              }}
            />

            <SettingItem
              type="list"
              title={LOCALES[language].UserCustomHasDynamicIsland}
              list={[
                {
                  value: true,
                  name: LOCALES[language].SettingPersonalPageShow,
                },
                {
                  value: false,
                  name: LOCALES[language].SettingPersonalPageDisable,
                },
              ]}
              value={isCustomDynamicIsland}
              onChange={(v) => {
                setCustomDynamicIsland(v)
                setTimeout(() => {
                  initUIData(v,isCustomNotch)
                },500)
              }}
            />
        
        </SettingGroup>
      </>
  );
}
