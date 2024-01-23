import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  TouchableWithoutFeedback,
} from "react-native";
import React, { useState } from "react";
import useHsrEvent from "../../hooks/hoyolab/useHsrEvent";
import { Image } from "expo-image";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../constant/screens";
import EventListItemType2 from "./EventListItem/EventListItemType2";
import EventListItemType1 from "./EventListItem/EventListItemType1";
import useHsrEventList from "../../hooks/hoyolab/useHsrEventList";
import useLocalState from "../../hooks/useLocalState";

export default function EventList() {
  const { data: hsrEvents, refetch: hsrEventsRefetch } = useHsrEvent();
  const { data: hsrEventsList } = useHsrEventList();

  const [displayType, setDisplayType] = useLocalState<1 | 2>(
    "hoyolab-event-display-type",
    1
  );

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
                <EventListItemType1
                  key={i}
                  event={event}
                  eventListData={
                    [
                      ...hsrEventsList?.data?.pic_list?.[0]?.type_list?.[0]
                        ?.list,
                      ...hsrEventsList?.data?.pic_list?.[0]?.type_list?.[1]
                        ?.list,
                    ].filter((e: any) => e.ann_id === event.ann_id)[0]
                  }
                  displayType={displayType}
                  onLongPress={() => {
                    if (displayType === 1) setDisplayType(2);
                    else setDisplayType(1);
                  }}
                />
              )
          )}
          {hsrEvents?.data?.list?.map(
            (event: any, i: number) =>
              event?.title && (
                <EventListItemType2
                  key={i}
                  event={event}
                  eventListData={
                    hsrEventsList?.data?.list?.[0]?.list?.filter(
                      (e: any) => e.ann_id === event.ann_id
                    )[0]
                  }
                  displayType={displayType}
                  onLongPress={() => {
                    if (displayType === 1) setDisplayType(2);
                    else setDisplayType(1);
                  }}
                />
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
