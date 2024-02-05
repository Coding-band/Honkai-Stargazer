import { View, Text, TouchableOpacity } from 'react-native';
import React, { useEffect, useMemo, useState } from 'react';
import { Image } from 'expo-image';
import { useQuery } from 'react-query';
import db from '../../../../firebase/db';
import CharacterImage from '../../../../../assets/images/images_map/chacracterImage';
import officalCharId from '../../../../../map/character_offical_id_map';
import useMyFirebaseUid from '../../../../firebase/hooks/FirebaseUid/useMyFirebaseUid';
import { LinearGradient } from 'expo-linear-gradient';
import { RouteProp, useNavigation, useRoute } from '@react-navigation/native';
import { ParamList } from '../../../../types/navigation';
import useAppLanguage from '../../../../language/AppLanguage/useAppLanguage';
import { LOCALES } from '../../../../../locales';
import formatLocale from '../../../../utils/format/formatLocale';
import getRankColor from '../../../../utils/getRankColor';
import MOCPageNavigator from '../../../MemoryOfChaosLeaderboardScreen/MOCLbList/MOCLbItem/MOCPageNavigator/MOCPageNavigator';

export default React.memo(function PFLbItem({
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
  const route = useRoute<RouteProp<ParamList, 'PureFictionLeaderboard'>>();
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
    ['pf-leaderboard', floorNumber, versionNumber, showMoreFloorDetails],
    async () => {
      const result = (
        await db
          .UserPureFiction(versionNumber, floorNumber)
          .orderBy('score', 'desc')
          .orderBy('round_num')
          .orderBy('challenge_time')
          .limit(showMoreFloorDetails ? pageCount * countPerPage : 5)
          .get()
      ).docs.map((doc) => doc.data());
      return result;
    },
    { staleTime: 1000 * 30 }
  );

  // 我的排行榜資訊
  const { data: myFloorLbData } = useQuery(
    ['my-pf-leaderboard', floorNumber, versionNumber, firebaseUID],
    async () =>
      (
        await db
          .UserPureFiction(versionNumber, floorNumber)
          .doc(firebaseUID)
          .get()
      ).data()
  );

  const showMoreFloorDetailsJSX = useMemo(() => {
    // 計算起始索引，只計算一次
    const startIndex = currentPage * countPerPage;

    // 如果floorLbData不存在，則提前返回空陣列以避免後續錯誤
    if (!floorLbData) {
      return [];
    }

    return floorLbData
      .slice(startIndex, startIndex + countPerPage) // 直接截取當前頁面的數據範圍
      .map((user, index) => {
        const rank = startIndex + index + 1; // 計算排名
        return (
          <RecordItem
            key={index}
            rank={rank}
            {...user}
            showRank={showRank}
            onShowRank={setShowRank}
            isLayer2={isLayer2}
            onSetLayer2={setIsLayer2}
          />
        );
      });
  }, [floorLbData, currentPage, showRank, isLayer2]);

  return (
    <View>
      <LinearGradient
        colors={['#000000', '#00000000']}
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
            <Text className="text-text2 font-[HY65] text-[16px] leading-5">
              {floorName}{' '}
              {isLayer2
                ? LOCALES[language].MOCPart2
                : LOCALES[language].MOCPart1}
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            activeOpacity={0.65}
            onPress={() => {
              // @ts-ignore
              navigation.push('PureFictionLeaderboard', {
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
            ? showMoreFloorDetailsJSX
            : new Array(5)
                .fill(null)
                .map((val, i) => floorLbData?.[i])
                ?.map((user, i) => (
                  <RecordItem
                    key={i}
                    rank={currentPage * countPerPage + i + 1}
                    {...user}
                    showRank={showRank}
                    onShowRank={setShowRank}
                    isLayer2={isLayer2}
                    onSetLayer2={setIsLayer2}
                  />
                ))}
          <RecordItem
            rank={'-'}
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

const RecordItem = React.memo((props: any) => {
  const { language } = useAppLanguage();

  return (
    <View className="pl-3" style={{ flexDirection: 'row', gap: 8 }}>
      <View
        style={{
          flexDirection: 'row',
          justifyContent: 'space-between',
          alignItems: 'flex-end',
          flex: 1,
        }}
      >
        <View style={{ gap: 4 }}>
          <View style={{ flexDirection: 'row', alignItems: 'center' }}>
            <View className="w-10 absolute -left-7 items-end">
              <Text
                style={{ color: getRankColor(props.rank) }}
                className="font-[HY65] text-[16px]"
              >
                {props.rank}
              </Text>
            </View>
            <Text
              className="text-[14px] font-[HY65] leading-4 pl-6"
              style={{ color: props?.name ? 'white' : '#DDD' }}
            >
              {
                // props?.uuid?.substr(0, 3) +
                // props?.uuid?.substr(-3).padStart(props.uuid?.length - 3, "*")
                props.name || LOCALES[language].NoDataYet
              }
            </Text>
          </View>
          {props.challenge_time && (
            <Text className="text-text text-[10px] font-[HY65] translate-x-[-10px]">
              {new Date(props.challenge_time).toLocaleDateString()}{' '}
              {`0${new Date(props.challenge_time).getHours()}`.slice(-2)}:
              {`0${new Date(props.challenge_time).getMinutes()}`.slice(-2)}{' '}
              {formatLocale(LOCALES[language].MOCRounds, [props.round_num])}{' '}
              {formatLocale(LOCALES[language].PFScore, [props.score])}
            </Text>
          )}
        </View>
        <TouchableOpacity
          activeOpacity={0.35}
          onPress={() => {
            props.onShowRank(!props.showRank);
          }}
          onLongPress={() => {
            props.onSetLayer2(!props.isLayer2);
          }}
          style={{ flexDirection: 'row', gap: 6 }}
        >
          {props?.layer_1?.characters?.length !== 0 ? (
            props[props.isLayer2 ? 'layer_2' : 'layer_1']?.characters.map(
              (char: any) => (
                <View
                  key={char.id}
                  style={{ gap: 2, alignItems: 'center' }}
                  className="w-8"
                >
                  <Image
                    cachePolicy="none"
                    transition={200}
                    className="w-6 h-6 rounded-full"
                    // @ts-ignore
                    source={CharacterImage[officalCharId[char.id]].icon}
                  />
                  <Text className="text-text font-[HY65] text-[10px]">
                    {props.showRank ? (
                      <Text
                        style={{ color: char.rank === 6 ? '#DD8200' : '#FFF' }}
                      >
                        {formatLocale(LOCALES[language].CharSoulShort, [
                          char.rank,
                        ])}
                      </Text>
                    ) : (
                      `Lv ${char.level}`
                    )}
                  </Text>
                </View>
              )
            )
          ) : (
            <Text className="text-text2 font-[HY65] text-[12px]">
              {LOCALES[language].MOCSkipped}
            </Text>
          )}
        </TouchableOpacity>
      </View>
    </View>
  );
});
