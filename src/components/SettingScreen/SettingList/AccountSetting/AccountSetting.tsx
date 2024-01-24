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

export default function AccountSetting() {
  const { language } = useAppLanguage();
  const hsrUUID = useHsrUUID();

  // 邀請碼
  const [openInvite, setOpenInvite] = useState(false);

  // 展示個人介面
  const { isShowInfo: isShowInfoFB, setIsShowInfo: setIsShowInfoFB } =
    useIsShowUserInfo(hsrUUID);
  const [isShowInfo, setIsShowInfo] = useState<boolean>();
  useEffect(() => {
    setIsShowInfo(isShowInfoFB);
  }, [isShowInfoFB]);

  return (
    hsrUUID && (
      <>
        <SettingGroup
          title={formatLocale(LOCALES[language].AccountSetup, [hsrUUID])}
        >
          <SettingItem
            type="navigation"
            title={LOCALES[language].UseInviteCode}
            content={LOCALES[language].HaveNotUsed}
            onNavigate={() => {
              setOpenInvite(true);
            }}
          />
          {hsrUUID && (
            <SettingItem
              type="list"
              title={LOCALES[language].SettingPersonalPage}
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
              value={isShowInfo}
              onChange={(v) => {
                setIsShowInfo(v);
                setIsShowInfoFB(v);
              }}
            />
          )}
        </SettingGroup>
        <ReactNativeModal
          useNativeDriverForBackdrop
          statusBarTranslucent
          deviceHeight={Dimensions.get("screen").height}
          isVisible={openInvite}
        >
          <PopUpCard
            onClose={() => {
              setOpenInvite(false);
            }}
            title="使用邀請碼"
            content={
              <View className="p-4" style={{ gap: 12 }}>
                <TextInput
                  textAlignVertical="top"
                  multiline={true}
               
                  placeholderTextColor="gray"
                  className="w-full bg-[#ffffff50] rounded-[8px] p-3 font-[HY55] leading-5"
                />
                <TextButton hasShadow={false} width={"100%"} height={46}>
                  確認
                </TextButton>
              </View>
            }
          />
        </ReactNativeModal>
      </>
    )
  );
}
