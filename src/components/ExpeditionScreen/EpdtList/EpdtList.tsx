import { View, ScrollView, RefreshControl } from "react-native";
import React from "react";
import EpdtListItem from "./EpdtListItem/EpdtListItem";
import useHsrNote from "../../../hooks/hoyolab/useHsrNote";

export default function EpdtList() {
  const { data: hsrNote, refetch: refetchHsrNote } = useHsrNote();
  const epdtList = hsrNote?.expeditions?.map((epdt: any) => ({
    avatars: epdt?.avatars,
    icon: epdt?.item_url,
    name: epdt?.name,
    remainingTime: epdt?.remaining_time,
    ongoing: epdt?.status === "Ongoing",
  }));

  const onRefresh = React.useCallback(() => {
    refetchHsrNote();
  }, []);

  return (
    <View style={{ width: "100%" }} className="z-30">
      <ScrollView
        className="h-screen"
        style={{
          padding: 17,
          paddingBottom: 0,
          marginTop: 110,
        }}
        refreshControl={
          <RefreshControl refreshing={false} onRefresh={onRefresh} />
        }
      >
        <View
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            gap: 11,
            justifyContent: "center",
          }}
        >
          {epdtList?.map((edpt: any, i: number) => (
            <EpdtListItem key={i} {...edpt} />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
