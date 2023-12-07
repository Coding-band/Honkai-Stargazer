import { View, ViewProps } from "react-native";
import { cn } from "../../../utils/css/cn";
import { omit } from "lodash";
import { Shadow } from "react-native-shadow-2";

export default function List(props: { children: any } & ViewProps) {
  return (
    <Shadow>
      <View
        className={cn("bg-[#00000099] p-[15px] z-10", props.className || "")}
        style={{
          borderRadius: 10,
          gap: 8,
          ...(props.style as object),
        }}
        {...omit(props, ["style", "children", "className"])}
      >
        {props.children}
      </View>
    </Shadow>
  );
}
