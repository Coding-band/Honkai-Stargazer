import { Text,View } from "react-native";

export default function ListItem({ children }: { children: string }) {
  return (
    <View
      className="w-[140px] h-[34px] bg-[#dddddd] rounded-[17px]"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <View
        className="w-[134px] h-[28px] rounded-[49px] border border-solid border-[#0000001a]"
        style={{
          justifyContent: "center",
          alignItems: "center",
          shadowColor: "#000",
          shadowOffset: { width: 0, height: 4 },
          shadowOpacity: 0.4,
          shadowRadius: 16,
          elevation: 16,
        }}
      >
        <Text>{children}</Text>
      </View>
    </View>
  );
}
