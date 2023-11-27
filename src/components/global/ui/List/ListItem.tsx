import { Text, View } from "react-native";
import { Shadow } from "react-native-shadow-2";

export default function ListItem({ children }: { children: string }) {
  return (
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
          <Text>{children}</Text>
        </View>
      </View>
    </Shadow>
  );
}
