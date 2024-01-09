import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
} from "react-native";
import React from "react";
import { RefreshControl } from "react-native";
import useHsrEvent from "../../hooks/hoyolab/useHsrEvent";
import { Image } from "expo-image";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../constant/screens";
import useHsrEventList from "../../hooks/hoyolab/useHsrEventList";
import { formatTimeDuration } from "../../utils/date/formatTime";

export default function EventList() {
  const navigation = useNavigation();

  const { data: hsrEvents, refetch: hsrEventsRefetch } = useHsrEvent();
  const { data: hsrEventList, refetch: hsrEventListRefetch } =
    useHsrEventList();

  const onRefresh = React.useCallback(() => {
    hsrEventsRefetch();
  }, []);

  const handleNavigateEvent = (id: string) => {
    // @ts-ignore
    navigation.push(SCREENS.EventPage.id, { id });
  };

  return (
    <ScrollView className="z-30 py-[127px] px-[17px] pb-0">
      <View
        style={{
          flexDirection: "row",
          flexWrap: "wrap",
          gap: 11,
          justifyContent: "center",
        }}
        className="pb-12"
      >
        <View style={styles.listInner} className="mb-24">
          {hsrEvents?.data?.pic_list?.map(
            (event: any, i: number) =>
              event?.title && (
                <TouchableOpacity
                  key={event?.ann_id}
                  className="w-full"
                  activeOpacity={0.65}
                  onPress={() => {
                    handleNavigateEvent(event?.ann_id);
                  }}
                >
                  <Image
                    transition={200}
                    className="w-full aspect-[360/108]"
                    source={event?.img}
                  />
                  {/* <View
                    className="w-10 h-5 bg-[#F3F9FF80] absolute top-[9px] left-[9px]"
                    style={styles.leftTopWidget}
                  ></View> */}
                </TouchableOpacity>
              )
          )}
          {hsrEvents?.data?.list?.map(
            (event: any, i: number) =>
              event?.title && (
                <TouchableOpacity
                  key={event?.ann_id}
                  className="w-full"
                  activeOpacity={0.65}
                  onPress={() => {
                    handleNavigateEvent(event?.ann_id);
                  }}
                >
                  <Image
                    transition={200}
                    className="w-full aspect-[360/130]"
                    source={event?.banner}
                  />
                  {/* <View
                    className="h-5 bg-[#F3F9FF80] absolute top-[9px] left-[9px]"
                    style={styles.leftTopWidget}
                  ></View> */}
                </TouchableOpacity>
              )
          )}
        </View>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  listInner: {
    flexDirection: "row",
    flexWrap: "wrap",
    gap: 11,
    justifyContent: "center",
  },
  leftTopWidget: {
    justifyContent: "center",
    alignItems: "center",
  },
});
