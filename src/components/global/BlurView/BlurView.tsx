import { View } from "react-native";
import React from "react";
import { BlurView as ExpoBlurView } from "expo-blur";
import * as Device from "expo-device";
import { omit } from "lodash";

type Props = React.ComponentProps<typeof ExpoBlurView> & { disable?: boolean };

export default function BlurView(props: Props) {
  const osVersion = Device.osVersion;
  const osVersionNumber = Number(osVersion?.split(".")[0]);

  if (osVersionNumber < 12)
    return (
      <View {...omit(props, ["intensity", "tint", "children"])}>
        {props.children}
      </View>
    );
  else if (props?.disable)
    return (
      <View {...omit(props, ["intensity", "tint", "children"])}>
        {props.children}
      </View>
    );
  else
    return (
      <ExpoBlurView {...omit(props, ["children"])}>
        {props.children}
      </ExpoBlurView>
    );
}
