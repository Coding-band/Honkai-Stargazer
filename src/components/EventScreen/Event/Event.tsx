import { View, Text, ScrollView } from "react-native";
import React, { useState } from "react";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../../../types/navigation";
import { Image } from "expo-image";
import useHsrEvent from "../../../hooks/hoyolab/useHsrEvent";
import { RefreshControl } from "react-native";
import EventWebView from "./EventWebView/EventWebView";
import Animated from "react-native-reanimated";
import { cn } from "../../../utils/css/cn";

export default function Event() {
  const route = useRoute<RouteProp<ParamList, "Event">>();
  const eventId = route.params.id;
  const eventType = eventId.toString().startsWith("3") ? "pic_list" : "list";

  const { data: hsrEvents, refetch: hsrEventsRefetch } = useHsrEvent();
  /*
  Origin version 
  const hsrEventsList =
    hsrEvents?.data?.[eventType === "pic_list" ? "pic_list" : "list"];
  const hsrEvent = hsrEventsList.find((event: any) => event.ann_id === eventId);
  */

  const hsrEventTMP1 = hsrEvents?.data?.["pic_list"].find(
    (event: any) => event.ann_id === eventId
  );
  const hsrEventTMP2 = hsrEvents?.data?.["list"].find(
    (event: any) => event.ann_id === eventId
  );
  const hsrEvent = hsrEventTMP1 === undefined ? hsrEventTMP2 : hsrEventTMP1;

  const onRefresh = React.useCallback(() => {
    hsrEventsRefetch();
  }, []);

  return (
    <ScrollView className="z-30 py-[110px] pb-0">
      <View
        style={{
          flexDirection: "row",
          flexWrap: "wrap",
          gap: 11,
          justifyContent: "center",
        }}
        className="pb-12"
      >
        <View style={{ gap: 16, alignItems: "center" }} className="mb-16">
          <AnimatedImage
            className={cn(
              "w-screen",
              eventType === "pic_list" ? "aspect-[360/110]" : "aspect-[360/130]"
            )}
            source={hsrEvent?.[eventType === "pic_list" ? "img" : "banner"]}
            contentFit="contain"
          />
          <EventWebView title={hsrEvent?.title} content={hsrEvent?.content} />
        </View>
      </View>
    </ScrollView>
  );
}

const AnimatedImage = Animated.createAnimatedComponent(Image);
