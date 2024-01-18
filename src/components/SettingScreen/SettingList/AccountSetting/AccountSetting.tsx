import React, { useState, useEffect } from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import Toast from "../../../../utils/toast/Toast";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import formatLocale from "../../../../utils/format/formatLocale";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import useIsShowUserInfo from "../../../../firebase/hooks/User/useIsShowUserInfo";

export default function AccountSetting() {
  const { language } = useAppLanguage();
  const hsrUUID = useHsrUUID();

  const { isShowInfo: isShowInfoFB, setIsShowInfo: setIsShowInfoFB } =
    useIsShowUserInfo(hsrUUID);

  const [isShowInfo, setIsShowInfo] = useState<boolean>();
  useEffect(() => {
    setIsShowInfo(isShowInfoFB);
  }, [isShowInfoFB]);

  return (
    hsrUUID && (
      <SettingGroup
        title={formatLocale(LOCALES[language].AccountSetup, [hsrUUID])}
      >
        <SettingItem
          type="navigation"
          title={LOCALES[language].UseInviteCode}
          content={LOCALES[language].HaveNotUsed}
          onNavigate={() => {
            Toast.StillDevelopingToast();
          }}
        />
        {hsrUUID && (
          <SettingItem
            type="list"
            title={LOCALES[language].SettingPersonalPage}
            list={[
              { value: true, name: LOCALES[language].SettingPersonalPageShow },
              { value: false, name: LOCALES[language].SettingPersonalPageDisable },
            ]}
            value={isShowInfo}
            onChange={(v) => {
              setIsShowInfo(v);
              setIsShowInfoFB(v);
            }}
          />
        )}
      </SettingGroup>
    )
  );
}
