import { View, Text } from "react-native";
import React, { ComponentProps } from "react";
import Button from "../Button/Button";

type Props = {
  children: string;
} & ComponentProps<typeof Button>;

export default function TextButton(props: Props) {
  return (
    <Button {...props}>
      <Text className="text-[14px] font-[HY65]">{props.children}</Text>
    </Button>
  );
}
