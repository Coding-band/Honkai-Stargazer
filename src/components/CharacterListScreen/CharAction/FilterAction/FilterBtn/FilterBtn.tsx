import React from "react";
import Button from "../../../../global/Button/Button";
import { Image } from "expo-image";

const FilterIcon = require("../../../../../../assets/icons/Filter.svg");

type Props = {
  onPress: () => void;
};

export default function FilterBtn(props: Props) {
  return (
    <Button onPress={props.onPress} width={46} height={46}>
      <Image cachePolicy="none"
        style={{
          width: 16,
          height: 16,
        }}
        source={FilterIcon}
      />
    </Button>
  );
}
