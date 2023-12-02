import { View } from "react-native";
import React, { useState } from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { TreeStructure } from "phosphor-react-native";
import { Image } from "expo-image";
import Edge from "./TraceItem/Edge";
import Inner from "./TraceItem/Inner";
import Outer from "./TraceItem/Outer";
import useDelayLoad from "../../../../hooks/useDelayLoad";

const TestTraceLine = require("../../../../../assets/images/test-trace-line.svg");
const CritDmgIcon = require("../../../../../assets/images/ui_icon/ic_crit_dmg.webp");
const AttackIcon = require("../../../../../assets/images/ui_icon/ic_atk.webp");
const DefIcon = require("../../../../../assets/images/ui_icon/ic_def.webp");
const TestOuter1 = require("../../../../../assets/images/test-outer-1.png");
const TestOuter2 = require("../../../../../assets/images/test-outer-2.png");
const TestOuter3 = require("../../../../../assets/images/test-outer-3.png");
const TestInner1 = require("../../../../../assets/images/test-inner-1.png");
const TestInner2 = require("../../../../../assets/images/test-inner-2.png");
const TestInner3 = require("../../../../../assets/images/test-inner-3.png");
const TestInner4 = require("../../../../../assets/images/test-inner-4.png");
const TestInner5 = require("../../../../../assets/images/test-inner-5.png");

export default function CharTrace() {
  const [selectedInner, setSelectedInner] = useState(0);
  const loaded = useDelayLoad(100);

  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={TreeStructure}>行迹树</CharPageHeading>
      <View
        style={{
          width: 325,
          height: 404,
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        {/* 軀幹 (線條) */}
        <Image source={TestTraceLine} style={{ width: 296, height: 374 }} />
        {/* 選項 */}
        {loaded && (
          <>
            <Edge left={85} top={16} icon={CritDmgIcon} />
            <Edge left={150} top={0} icon={DefIcon} />
            <Edge left={215} top={16} icon={AttackIcon} />
            <Edge left={0} top={120} icon={DefIcon} />
            <Edge left={290} top={120} icon={AttackIcon} />
            <Edge left={0} top={210} icon={AttackIcon} />
            <Edge left={30} top={260} icon={CritDmgIcon} />
            <Edge left={290} top={210} icon={CritDmgIcon} />
            <Edge left={265} top={260} icon={AttackIcon} />
            <Edge left={150} top={374} icon={AttackIcon} />
            <Outer left={135} top={55} icon={TestOuter1} />
            <Outer left={55} top={305} icon={TestOuter2} />
            <Outer left={215} top={305} icon={TestOuter3} />
            <Inner
              left={136}
              top={134}
              icon={TestInner1}
              selected={selectedInner === 0}
              onPress={() => {
                setSelectedInner(0);
              }}
            />
            <Inner
              left={55}
              top={178}
              icon={TestInner2}
              selected={selectedInner === 1}
              onPress={() => {
                setSelectedInner(1);
              }}
            />
            <Inner
              left={136}
              top={210}
              icon={TestInner3}
              selected={selectedInner === 2}
              onPress={() => {
                setSelectedInner(2);
              }}
            />
            <Inner
              left={214}
              top={178}
              icon={TestInner4}
              selected={selectedInner === 3}
              onPress={() => {
                setSelectedInner(3);
              }}
            />
            <Inner
              left={136}
              top={295}
              icon={TestInner5}
              selected={selectedInner === 4}
              onPress={() => {
                setSelectedInner(4);
              }}
            />
          </>
        )}
      </View>
    </View>
  );
}
