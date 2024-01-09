import { View } from "react-native";
import React from "react";
import { BlurView as ExpoBlurView } from "expo-blur";
import * as Device from "expo-device";
import { omit } from "lodash";

type Props = React.ComponentProps<typeof ExpoBlurView>;

export default function BlurView(props: Props) {
  const osVersion = Device.osVersion;
  const osVersionNumber = Number(osVersion?.split(".")[0]);


  return osVersionNumber < 12 ? (
    <View {...omit(props, ["intensity", "tint", "children"])}>
      {props.children}
    </View>
  ) : (
    <ExpoBlurView {...omit(props, ["children"])}>{props.children}</ExpoBlurView>
  );
}
