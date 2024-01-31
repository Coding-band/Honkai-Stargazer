import { View, Text, TouchableOpacity } from "react-native";
import React, { useEffect, useState } from "react";
import { Image } from "expo-image";
import { useQuery } from "react-query";
import db from "../../../../firebase/db";
import CharacterImage from "../../../../../assets/images/images_map/chacracterImage";
import officalCharId from "../../../../../map/character_offical_id_map";
import useMyFirebaseUid from "../../../../firebase/hooks/FirebaseUid/useMyFirebaseUid";
import { LinearGradient } from "expo-linear-gradient";
import { RouteProp, useNavigation, useRoute } from "@react-navigation/native";
import { ParamList } from "../../../../types/navigation";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import formatLocale from "../../../../utils/format/formatLocale";
import getRankColor from "../../../../utils/getRankColor";
import MOCRecordItem from "./MOCRecordItem/MOCRecordItem";
import MOCPageNavigator from "./MOCPageNavigator/MOCPageNavigator";

export default React.memo(function MOCLbItem({
  versionNumber,
  floorNumber,
  floorName,
}: {
  versionNumber: number;
  floorNumber: number;
  floorName: string;
}) {
  const { language } = useAppLanguage();

  const navigation = useNavigation();
  const route = useRoute<RouteProp<ParamList, "MemoryOfChaosLeaderboard">>();
  const showMoreFloorDetails = !!route.params?.floorNumber;
  const firebaseUID = useMyFirebaseUid();

  // 是否顯示命作
  const [showRank, setShowRank] = useState(false);
  // 是否顯示第二層
  const [isLayer2, setIsLayer2] = useState(false);

  // 分頁導航
  const pageCount = 6;
  const countPerPage = 50;
  const [currentPage, setCurrentPage] = useState(0);

  // 排行榜資訊
  const { data: floorLbData } = useQuery(
    [
      "moc-leaderboard",
      floorNumber,
      versionNumber,
      showMoreFloorDetails,
      currentPage,
    ],
    async () => {
      const result = (
        await db
          .UserMemoryOfChaos(versionNumber, floorNumber)
          .orderBy("star_num", "desc")
          .orderBy("round_num")
          .orderBy("challenge_time")
          .limit(showMoreFloorDetails ? pageCount * countPerPage : 5)
          .get()
      ).docs.map((doc) => {
        return doc.data();
      });
      return result;
    },
    { staleTime: 1000 * 30 }
  );

  // 我的排行榜資訊
  const { data: myFloorLbData } = useQuery(
    ["my-moc-leaderboard", floorNumber, versionNumber, firebaseUID],
    async () =>
      (
        await db
          .UserMemoryOfChaos(versionNumber, floorNumber)
          .doc(firebaseUID)
          .get()
      ).data()
  );

  return (
    <View>
      <LinearGradient
        colors={["#000000", "#00000000"]}
        className="border border-[#DDDDDD20] rounded-[4px] py-4 px-3 w-[360px]"
        style={{ gap: 8 }}
      >
        <View className="flex-row justify-between items-center">
          <TouchableOpacity
            activeOpacity={0.35}
            onPress={() => {
              setIsLayer2(!isLayer2);
            }}
          >
            <Text className="text-text2 font-[HY65] text-[16px]">
              {floorName}{" "}
              {isLayer2
                ? LOCALES[language].MOCPart2
                : LOCALES[language].MOCPart1}
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            activeOpacity={0.65}
            onPress={() => {
              // @ts-ignore
              navigation.push("MemoryOfChaosLeaderboard", {
                scheduleId: versionNumber,
                floorNumber: floorNumber,
              });
            }}
          >
            <Text className="text-text2 font-[HY65] text-[12px]">
              {LOCALES[language].MOCShowMore}
            </Text>
          </TouchableOpacity>
        </View>
        <View style={{ gap: 12 }}>
          {showMoreFloorDetails
            ? new Array(countPerPage)
                .fill(null)
                .map((val, i) => floorLbData?.[currentPage * countPerPage + i])
                ?.map((user, i) => (
                  <MOCRecordItem
                    key={i}
                    rank={currentPage * countPerPage + i + 1}
                    {...user}
                    showRank={showRank}
                    onShowRank={setShowRank}
                    isLayer2={isLayer2}
                    onSetLayer2={setIsLayer2}
                  />
                ))
            : new Array(5)
                .fill(null)
                .map((val, i) => floorLbData?.[i])
                ?.map((user, i) => (
                  <MOCRecordItem
                    key={i}
                    rank={currentPage * countPerPage + i + 1}
                    {...user}
                    showRank={showRank}
                    onShowRank={setShowRank}
                    isLayer2={isLayer2}
                    onSetLayer2={setIsLayer2}
                  />
                ))}
          <MOCRecordItem
            rank={"-"}
            {...myFloorLbData}
            showRank={showRank}
            onShowRank={setShowRank}
            isLayer2={isLayer2}
            onSetLayer2={setIsLayer2}
          />
        </View>
      </LinearGradient>
      {showMoreFloorDetails && (
        <MOCPageNavigator
          pageCount={pageCount}
          page={currentPage}
          onPageChange={setCurrentPage}
        />
      )}
    </View>
  );
});
