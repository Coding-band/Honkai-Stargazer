import { View, ViewProps } from "react-native";
import { cn } from "../../../../utils/cn";
import { omit } from "lodash";

export default function List(props: { children: any } & ViewProps) {
  return (
    <View
      className={cn(
        "bg-[#00000099] rounded-[10px] p-[15px] z-10",
        props.className || ""
      )}
      style={{
        shadowColor: "#000",
        shadowOffset: { width: 0, height: 4 },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 4,
        flexDirection: "column",
        gap: 8,
        ...(props.style as object),
      }}
      {...omit(props, ["style", "children", "className"])}
    >
      {props.children}
    </View>
  );
}
