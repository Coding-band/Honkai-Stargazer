import { Sword } from "phosphor-react-native";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { ScrollView, View } from "react-native";
import LightConeCard from "../../../global/layout/LightConeCard/LightConeCard";

const testImage1 = require("../../../../../assets/images/test-light-cone-1.png");
const testImage2 = require("../../../../../assets/images/test-light-cone-2.png");

const testData = [
  { image: testImage1, star: 5, name: "星海巡航" },
  { image: testImage2, star: 4, name: "论剑" },
  { image: testImage1, star: 5, name: "星海巡航" },
  { image: testImage2, star: 4, name: "论剑" },
];

export default function CharSuggestLightCone() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={Sword}>推荐光锥</CharPageHeading>
      <ScrollView horizontal>
        <View
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            columnGap: 8,
          }}
        >
          {testData.map((l, i) => (
            // @ts-ignore
            <LightConeCard key={i} {...l} />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
