import { View, ScrollView } from "react-native";
import React, { MutableRefObject, useState } from "react";
import SettingGroup from "../SettingGroup/SettingGroup";
import SettingItem from "../SettingGroup/SettingItem/SettingItem";
import UISetting from "./UISetting/UISetting";
import AccountSetting from "./AccountSetting/AccountSetting";
import LanguageSetting from "./LanguageSetting/LanguageSetting";
import PersonalSetting from "./PersonalSetting/PersonalSetting";
import NotificationSetting from "./NotificationSetting/NotificationSetting";
import SupportSetting from "./SupportSetting/SupportSetting";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";
import SpecialThanksSetting from "./SpecialThanksSetting/SpecialThanksSetting";
import DevelopmentSetting from "./DevelopmentSetting/DevelopmentSetting";
import { dynamicHeightSettingScrollView } from "../../../constant/ui";
import Animated from "react-native-reanimated";

type Props = {
  scrollViewRef : MutableRefObject<ScrollView | Animated.ScrollView | undefined | null>;
}

export default function SettingList(props : Props) {

  return (
    //@ts-ignore
    <ScrollView className={dynamicHeightSettingScrollView} ref={props.scrollViewRef}>
      <View style={{ gap: 20 }} className="pb-48">
        <AccountSetting />
        {/*<UISetting/>*/}
        <LanguageSetting />
        <PersonalSetting />
        <NotificationSetting />
        <SupportSetting />
        <SpecialThanksSetting />
        <DevelopmentSetting />
      </View>
    </ScrollView>
  );
}
