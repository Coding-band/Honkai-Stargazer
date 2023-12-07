import { GestureResponderEvent, Text, TouchableOpacity, View } from "react-native";
import { Shadow } from "react-native-shadow-2";

export default function ListItem({
  children,
  onPress,
}: {
  children: string;
  onPress?: (e: GestureResponderEvent) => void;
}) {
  return (
    <TouchableOpacity onPress={onPress} activeOpacity={0.65}>
      <Shadow offset={[0, 4]}>
        <View
          className="w-[140px] h-[34px] bg-[#dddddd] rounded-[17px]"
          style={{ justifyContent: "center", alignItems: "center" }}
        >
          <View
            className="w-[134px] h-[28px] rounded-[49px] border border-solid border-[#0000001a]"
            style={{
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Text className="text-[14px] font-[HY55]">{children}</Text>
          </View>
        </View>
      </Shadow>
    </TouchableOpacity>
  );
}
