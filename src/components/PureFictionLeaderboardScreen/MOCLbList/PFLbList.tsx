import { View, ScrollView, Dimensions, Text } from "react-native";
import React, { useState } from "react";
import PFLbItem from "./MOCLbItem/PFLbItem";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import Button from "../../global/Button/Button";
import Listbox from "../../global/Listbox/Listbox";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../../../types/navigation";
import { PFVersion } from "../../../constant/pf";
import PFDataMap from "../../../../map/pure_fiction_data_map";
import { LOCALES } from "../../../../locales";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";

export default function PFLbList() {
  const { language: textLanguage } = useTextLanguage();
  const { language: appLanguage } = useAppLanguage();

  const route = useRoute<RouteProp<ParamList, "PureFictionLeaderboard">>();
  // 當頁面跳轉時有指定版本&樓層數 (查看完整排行榜時)
  const scheduleId = route.params?.scheduleId;
  const floorNumber = route.params?.floorNumber;

  const pfVersion = PFVersion(textLanguage);

  const [selectedVersion, setSelectedVersion] = useState(
    scheduleId || pfVersion[0].id
  );

  // @ts-ignore
  const pfData = PFDataMap[selectedVersion];
  const floorNames = pfData.info
    .map((floor: any) => floor.name?.[textLanguage])
    .reverse();

  return (
    <View style={{ width: "100%" }} className="z-30">
      <ScrollView
        className="p-4 pb-0 pt-[127px]"
        style={{ height: Dimensions.get("screen").height }}
      >
        <View className="w-full mb-4 z-40" style={{ alignItems: "center" }}>
          {floorNumber ? (
            <></>
          ) : (
            <Listbox
              top={8}
              button={
                <Button
                  width={Dimensions.get("screen").width - 32}
                  height={46}
                  withArrow
                >
                  <Text className="text-[16px] font-[HY65] text-[#222] leading-5">
                    {pfVersion.filter((v) => v.id === selectedVersion)[0].name}
                  </Text>
                </Button>
              }
              value={selectedVersion}
              onChange={(version) => {
                setSelectedVersion(version);
              }}
            >
              {pfVersion?.map((version) => (
                <Listbox.Item key={version.id} value={version.id}>
                  {/* @ts-ignore */}
                  {version.name}
                </Listbox.Item>
              )) || []}
            </Listbox>
          )}
        </View>
        <View style={{ gap: 16, alignItems: "center" }} className="mb-44">
          {/* 排行榜列表 */}
          <>
            {floorNumber
              ? [floorNames?.[floorNames.length - floorNumber]]?.map(
                  (name: string, i: number) => (
                    <PFLbItem
                      key={i}
                      versionNumber={selectedVersion}
                      floorNumber={floorNumber}
                      floorName={name}
                    />
                  )
                )
              : floorNames?.map((name: string, i: number) => (
                  <PFLbItem
                    key={i}
                    versionNumber={selectedVersion}
                    floorNumber={floorNames.length - i}
                    floorName={name}
                  />
                ))}
          </>
          {/* 底下描述 */}
          <View>
            <Text className="text-text2 font-[HY65] text-[16px] text-center">
              {LOCALES[appLanguage].EventDuration}：
              {new Date(pfData.time.begin).toLocaleDateString()} -{" "}
              {new Date(pfData.time.end).toLocaleDateString()}
            </Text>
          </View>
        </View>
      </ScrollView>
    </View>
  );
}
