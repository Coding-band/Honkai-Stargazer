import { View, ScrollView, RefreshControl } from "react-native";
import React from "react";
import EpdtListItem from "./EpdtListItem/EpdtListItem";
import useHsrNote from "../../../hooks/hoyolab/useHsrNote";
import NotFound from "../../global/Loading/NotFound";

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

  return epdtList ? (
    <View style={{ width: "100%" }} className="z-30">
      <ScrollView
        className="h-screen p-4 pb-0 mt-[110px]"
        refreshControl={
          <RefreshControl refreshing={false} onRefresh={onRefresh} />
        }
        scrollEnabled={false}
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
  ) : (
    <NotFound />
  );
}
