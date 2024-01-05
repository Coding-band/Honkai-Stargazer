import { View, Text, ScrollView, TouchableOpacity } from "react-native";
import React from "react";
import { RefreshControl } from "react-native";
import useHsrEvent from "../../hooks/hoyolab/useHsrEvent";
import { Image } from "expo-image";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../constant/screens";
import Animated, {
  SharedTransition,
  withSpring,
} from "react-native-reanimated";

export default function EventList() {
  const navigation = useNavigation();

  const { data: hsrEvents, refetch: hsrEventsRefetch } = useHsrEvent();
  const hsrEventsList = hsrEvents?.data?.list;

  const onRefresh = React.useCallback(() => {
    hsrEventsRefetch();
  }, []);

  return (
    <View style={{ width: "100%" }} className="z-30">
      <ScrollView
        className="h-screen p-4 mt-[110px]"
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
          className="mb-24"
        >
          {hsrEventsList?.map((event: any, i: number) => (
            <TouchableOpacity
              key={event?.ann_id}
              className="w-full"
              activeOpacity={0.65}
              onPress={() => {
                // @ts-ignore
                navigation.push(SCREENS.EventPage.id, { id: event?.ann_id });
              }}
            >
              <View className="w-full">
                <AnimatedImage
                  className="w-full h-[130px]"
                  source={event?.banner}
                />
              </View>
            </TouchableOpacity>
          ))}
        </View>
      </ScrollView>
    </View>
  );
}

const AnimatedImage = Animated.createAnimatedComponent(Image);
