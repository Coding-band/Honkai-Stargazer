import { View, Text, Dimensions } from "react-native";
import React, { useState } from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import PopUpCard from "../../../global/PopUpCard/PopUpCard";
import ReactNativeModal from "react-native-modal";
import { HtmlText } from "@e-mine/react-native-html-text";
import Button from "../../../global/Button/Button";

export default function SupportSetting() {
  const [openDonate, setOpenDonate] = useState(false);

  return (
    <>
      <SettingGroup title="支持我们">
        <SettingItem
          type="navigation"
          title="捐赠"
          onNavigate={() => {
            setOpenDonate(true);
          }}
        />
        <SettingItem type="navigation" title="邀请他人" />
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
              <HtmlText>
                {`感谢您的捐赠，有您的支持我们才能更好地完善本App，所有捐赠都将用于Stargazer的<span style="color:#DD8200;">必要支出</span>和<span style="color:#DD8200;">其他提升</span>。`}
              </HtmlText>
              <HtmlText>
                {`进行任意一项捐赠即可<span style="color:#DD8200;">免除所有廣告</span>。`}
              </HtmlText>
              <View style={{ gap: 12 }}>
                <Button hasShadow={false} width={310} height={46}>
                  <Text>捐赠$2</Text>
                </Button>
                <Button hasShadow={false} width={310} height={46}>
                  <Text>捐赠$5</Text>
                </Button>
                <Button hasShadow={false} width={310} height={46}>
                  <Text>捐赠$10</Text>
                </Button>
                <Button hasShadow={false} width={310} height={46}>
                  <Text>捐赠$20</Text>
                </Button>
                <Button hasShadow={false} width={310} height={46}>
                  <Text>捐赠$50</Text>
                </Button>
                <Button hasShadow={false} width={310} height={46}>
                  <Text>捐赠$99</Text>
                </Button>
                <Button hasShadow={false} width={310} height={46}>
                  <Text>恢复捐赠</Text>
                </Button>
                <Button hasShadow={false} width={310} height={46}>
                  <Text>所有捐赠名单</Text>
                </Button>
              </View>
            </View>
          }
        />
      </ReactNativeModal>
    </>
  );
}
