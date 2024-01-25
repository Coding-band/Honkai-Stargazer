import { View } from "react-native";
import React from "react";
import PFFloor from "./PFFloor/PFFloor";
import { capitalize } from "lodash";
import NotFound from "../../global/Loading/NotFound";
import NoDataYet from "../../global/Loading/NoDataYet";
import { globalStyles } from "../../../../styles/global";
import PFHeader from "../MocHeader/PFHeader";
import Animated, {
  useAnimatedRef,
  useScrollViewOffset,
} from "react-native-reanimated";
import usePureFiction from "../../../hooks/hoyolab/usePureFiction";

export default function PFList() {
  // data
  const { data: moc } = usePureFiction();
  const floors = moc?.all_floor_detail?.map((floor: any) => ({
    title: floor?.name,
    stars: floor?.star_num,
    round: floor?.round_num,
    roundAverage: floor?.round_num,
    roundRemaining: 30 - floor?.round_num,
    isFast: floor?.is_fast,
    teams: [
      {
        date: `${floor?.node_1?.challenge_time.year}.${(
          "0" + floor?.node_1?.challenge_time.month
        ).slice(-2)}.${("0" + floor?.node_1?.challenge_time.day).slice(-2)} ${(
          "0" + floor?.node_1?.challenge_time.hour
        ).slice(-2)}:${("0" + floor?.node_1?.challenge_time.minute).slice(-2)}`,
        characters: floor?.node_1?.avatars?.map((char: any) => ({
          officalId: char.id,
          level: char?.level,
          image: char?.icon,
          rare: char?.rarity,
          rank: char?.rank,
          combatType: capitalize(char?.element),
        })),
        score: Number(floor?.node_1?.score),
      },
      {
        date: `${floor?.node_2?.challenge_time.year}.${(
          "0" + floor?.node_2?.challenge_time.month
        ).slice(-2)}.${("0" + floor?.node_2?.challenge_time.day).slice(-2)} ${(
          "0" + floor?.node_2?.challenge_time.hour
        ).slice(-2)}:${("0" + floor?.node_2?.challenge_time.minute).slice(-2)}`,
        characters: floor?.node_2?.avatars?.map((char: any) => ({
          officalId: char.id,
          level: char?.level,
          image: char?.icon,
          rare: char?.rarity,
          rank: char?.rank,
          combatType: capitalize(char?.element),
        })),
        score: Number(floor?.node_2?.score),
      },
    ],
  }));

  // state
  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  return (
    <View>
      <PFHeader scrollHandler={scrollHandler} />
      {!floors?.length && <NoDataYet />}
      {!moc && <NotFound />}
      <Animated.ScrollView ref={aref} className="z-30 pt-[127px] pb-0">
        <View
          style={{ ...globalStyles.rJCenterFWrap, gap: 12 }}
          className="pb-48"
        >
          {floors?.map((floor: any, index: number) => (
            <PFFloor key={floor?.title} {...floor} />
          ))}
        </View>
      </Animated.ScrollView>
    </View>
  );
}
