import { Sword } from "phosphor-react-native";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { View } from "react-native";

export default function CharSuggestLightCone() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={Sword}>推荐光锥</CharPageHeading>
    </View>
  );
}
