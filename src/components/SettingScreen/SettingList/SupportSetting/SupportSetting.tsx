import { View, Text, Dimensions } from "react-native";
import React, { useState } from "react";
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

export default function SupportSetting() {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  const [openDonate, setOpenDonate] = useState(false);
  return (
    <>
      {/* <SettingGroup title={LOCALES[language].SupportUs}>
        <SettingItem
          type="navigation"
          title={LOCALES[language].Donation}
          onNavigate={() => {
            setOpenDonate(true);
          }}
        />
        <SettingItem
          type="navigation"
          title={LOCALES[language].InviteOthers}
          onNavigate={() => {
            // @ts-ignore
            navigation.navigate(SCREENS.InvitePage.id);
          }}
        />
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
          title="捐赠"
          content={
            <View className="pt-2 py-4 px-4" style={{ gap: 10 }}>
              <HtmlText style={{ fontFamily: "HY65" }}>
                {`感谢您的捐赠，有您的支持我们才能更好地完善本App，所有捐赠都将用于Stargazer的<span style="color:#DD8200;">必要支出</span>和<span style="color:#DD8200;">其他提升</span>。`}
              </HtmlText>
              <HtmlText style={{ fontFamily: "HY65" }}>
                {`进行任意一项捐赠即可<span style="color:#DD8200;">免除所有廣告</span>。`}
              </HtmlText>
              <View style={{ gap: 12 }}>
                <TextButton hasShadow={false} width={310} height={46}>
                  捐赠$2
                </TextButton>
                <TextButton hasShadow={false} width={310} height={46}>
                  捐赠$5
                </TextButton>
                <TextButton hasShadow={false} width={310} height={46}>
                  捐赠$10
                </TextButton>
                <TextButton hasShadow={false} width={310} height={46}>
                  捐赠$20
                </TextButton>
                <TextButton hasShadow={false} width={310} height={46}>
                  捐赠$50
                </TextButton>
                <TextButton hasShadow={false} width={310} height={46}>
                  捐赠$99
                </TextButton>
                <TextButton hasShadow={false} width={310} height={46}>
                  恢复捐赠
                </TextButton>
                <TextButton hasShadow={false} width={310} height={46}>
                  所有捐赠名单
                </TextButton>
              </View>
            </View>
          }
        />
      </ReactNativeModal> */}
    </>
  );
}
