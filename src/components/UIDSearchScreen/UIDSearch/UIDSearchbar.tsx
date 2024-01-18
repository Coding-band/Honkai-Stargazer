import {
  View,
  Text,
  ScrollView,
  Pressable,
  Keyboard,
  TouchableOpacity,
  Dimensions,
} from "react-native";
import React, { useEffect, useState } from "react";
import UIDSearchbar from "./UIDSearchbar/UIDSearchbar";
import UIDSearchItem from "./UIDSearchItem/UIDSearchItem";
import Toast from "../../../utils/toast/Toast";
import getServerFromUUID from "../../../utils/hoyolab/servers/getServerFromUUID";
import useLocalState from "../../../hooks/useLocalState";
import useHsrInGameInfo from "../../../hooks/mihomo/useHsrInGameInfo";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../constant/screens";
import DraggableFlatList, {
  RenderItemParams,
} from "react-native-draggable-flatlist";
import { uniq } from "lodash";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";

export default function UIDSearch() {
  const navigation = useNavigation();
  const { language } = useAppLanguage()

  const [input, setInput] = useState("");
  const [uidHistory, setUidHistory] = useLocalState<string[]>(
    "uid-history",
    []
  );

  const { refetch, error, data } = useHsrInGameInfo(input, {
    enabled: false,
    retry: 2,
  });

  const handleSubmit = () => {
    if (input.length !== 9 || !getServerFromUUID(input)) {
      Toast(LOCALES[language].UIDFormatError);
    } else {
      Toast(LOCALES[language].Searching, 8);
      refetch();
    }
  };
  useEffect(() => {
    if (error) {
      Toast(LOCALES[language].UIDNoData);
    }

    if (data) {
      // @ts-ignore
      navigation.navigate(SCREENS.UserInfoPage.id, { uuid: input });
      setUidHistory(uniq([input, ...uidHistory]));
      setInput("");
    }
  }, [error, data]);

  const handleClearHistory = () => {
    setUidHistory([]);
  };

  return (
    <Pressable
      onPress={() => {
        Keyboard.dismiss();
      }}
      style={{ width: "100%" }}
      className="z-30"
    >
      <View
        className="h-screen p-4 pb-0 mt-[110px]"
        style={{ alignItems: "center", gap: 16 }}
        // keyboardShouldPersistTaps="always"
      >
        <UIDSearchbar
          value={input}
          onChangeText={setInput}
          onSubmit={handleSubmit}
          placeholder={LOCALES[language].UIDEnter}
        />
        <Text className="text-text text-[12px] font-[HY65] leading-4">
          {LOCALES[language].UIDOnlySupportFullUID}
        </Text>
        {!!uidHistory?.length && (
          <View
            className="w-full"
            style={{ justifyContent: "space-between", flexDirection: "row" }}
          >
            <Text className="text-text text-[14px] font-[HY65] leading-5">
              {LOCALES[language].UIDSearchRecord}
            </Text>
            <TouchableOpacity activeOpacity={0.35} onPress={handleClearHistory}>
              <Text className="text-text text-[14px] font-[HY65] leading-5">
                {LOCALES[language].UIDSearchRecordClear}
              </Text>
            </TouchableOpacity>
          </View>
        )}

        <DraggableFlatList
          containerStyle={{ height: Dimensions.get("screen").height - 260 }}
          data={uidHistory}
          onDragEnd={({ data }) => {
            setUidHistory(data);
          }}
          keyExtractor={(item) => item}
          renderItem={({ item, drag, isActive }: RenderItemParams<any>) => {
            return (
              <TouchableOpacity
                activeOpacity={0.65}
                onPress={() => {
                  // @ts-ignore
                  navigation.navigate(SCREENS.UserInfoPage.id, { uuid: item });
                }}
                delayLongPress={200}
                className="mb-4"
                onLongPress={drag}
                disabled={isActive}
              >
                <UIDSearchItem uuid={item} />
              </TouchableOpacity>
            );
          }}
        />
      </View>
    </Pressable>
  );
}
