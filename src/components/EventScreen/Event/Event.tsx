import { View, Text, ScrollView, Dimensions } from "react-native";
import React, { useState } from "react";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../../../types/navigation";
import { Image } from "expo-image";
import useHsrEvent from "../../../hooks/hoyolab/useHsrEvent";
import { RefreshControl } from "react-native";
import WebView from "react-native-webview";
import EventWebView from "./EventWebView/EventWebView";

export default function Event() {
  const route = useRoute<RouteProp<ParamList, "Event">>();
  const eventId = route.params.id;

  const { data: hsrEvents, refetch: hsrEventsRefetch } = useHsrEvent();
  const hsrEventsList = hsrEvents?.data?.list;
  const hsrEvent = hsrEventsList.find((event: any) => event.ann_id === eventId);

  const onRefresh = React.useCallback(() => {
    hsrEventsRefetch();
  }, []);

  return (
    <View style={{ width: "100%" }} className="z-30">
      <ScrollView
        className="w-full mt-[110px]"
        refreshControl={
          <RefreshControl refreshing={false} onRefresh={onRefresh} />
        }
      >
        <View style={{ gap: 16, alignItems: "center" }} className="mb-16">
          <Image
            className="w-screen h-[142px]"
            source={hsrEvent?.banner}
            contentFit="contain"
          />
          <EventWebView title={hsrEvent?.title} content={hsrEvent?.content} />
        </View>
      </ScrollView>
    </View>
  );
}
