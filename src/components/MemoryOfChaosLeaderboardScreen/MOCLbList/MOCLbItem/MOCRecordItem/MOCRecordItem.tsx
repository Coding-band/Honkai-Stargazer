import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import getRankColor from "../../../../../utils/getRankColor";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../locales";
import formatLocale from "../../../../../utils/format/formatLocale";
import { Image } from "expo-image";
import CharacterImage from "../../../../../../assets/images/images_map/chacracterImage";
import officalCharId from "../../../../../../map/character_offical_id_map";

export default React.memo(function MOCRecordItem(props: {
  rank: any;
  name: string;
  challenge_time: string;
  round_num: number;
  star_num: number;
  characters: any[];
  showRank: boolean;
  onShowRank: (v: boolean) => void;
  isLayer2: boolean;
  onSetLayer2: (v: boolean) => void;
  layer_1: any;
  layer_2: any;
}) {
  const { language } = useAppLanguage();

  return (
    <View className="pl-3" style={{ flexDirection: "row", gap: 8 }}>
      <View className="flex-row justify-between items-end flex-1">
        <View style={{ gap: 4 }}>
          <View className="flex-row items-center">
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
              style={{ color: props?.name ? "white" : "#DDD" }}
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
              {new Date(props.challenge_time).toLocaleDateString()}{" "}
              {`0${new Date(props.challenge_time).getHours()}`.slice(-2)}:
              {`0${new Date(props.challenge_time).getMinutes()}`.slice(-2)}{" "}
              {formatLocale(LOCALES[language].MOCRounds, [props.round_num])}{" "}
              {formatLocale(LOCALES[language].MOCStars, [props.star_num])}
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
          style={{ flexDirection: "row", gap: 6 }}
        >
          {props?.layer_1?.characters?.length !== 0 ? (
            props[props.isLayer2 ? "layer_2" : "layer_1"]?.characters.map(
              (char: any) => (
                <LbTeamItem char={char} showRank={props.showRank} />
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

const LbTeamItem = React.memo(
  ({ char, showRank }: { char: any; showRank: boolean }) => {
    const { language } = useAppLanguage();

    return (
      <View
        key={char.id}
        style={{ gap: 2, alignItems: "center" }}
        className="w-8"
      >
        <Image cachePolicy="none"
          transition={200}
          className="w-6 h-6 rounded-full"
          // @ts-ignore
          source={CharacterImage[officalCharId[char.id]].icon}
        />
        <Text className="text-text font-[HY65] text-[10px]">
          {showRank ? (
            <Text
              style={{
                color: char.rank === 6 ? "#DD8200" : "#FFF",
              }}
            >
              {formatLocale(LOCALES[language].CharSoulShort, [char.rank])}
            </Text>
          ) : (
            `Lv ${char.level}`
          )}
        </Text>
      </View>
    );
  }
);
