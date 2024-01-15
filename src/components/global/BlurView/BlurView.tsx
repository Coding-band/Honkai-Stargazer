import { View } from "react-native";
import React from "react";
import { BlurView as ExpoBlurView } from "expo-blur";
import * as Device from "expo-device";
import { omit } from "lodash";
import useDoUseBlurEffect from "../../../redux/doUseBlurEffect/useDoUseBlurEffect";

type Props = React.ComponentProps<typeof ExpoBlurView> & { disable?: boolean };

export default function BlurView(props: Props) {
  const osVersion = Device.osVersion;
  const osVersionNumber = Number(osVersion?.split(".")[0]);

  const { doUseBlurEffect } = useDoUseBlurEffect();

  if (!doUseBlurEffect || osVersionNumber < 12 || props?.disable) {
    return (
      <View {...omit(props, ["intensity", "tint", "children"])}>
        {props.children}
      </View>
    );
  } else
    return (
      <ExpoBlurView {...omit(props, ["children"])}>
        {props.children}
      </ExpoBlurView>
    );
}
