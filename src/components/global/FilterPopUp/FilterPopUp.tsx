import { View, Text } from "react-native";
import React from "react";
import PopUpCard from "../PopUpCard/PopUpCard";
import Button from "../Button/Button";
import FilterItem from "./FilterItem/FilterItem";
import { ExpoImage } from "../../../types/image";
import _ from "lodash";

type Props = {
  items: { value: string; name: string; icon: ExpoImage }[];
  value: string[];
  onChange: (v: string[]) => void;
  onClose?: () => void;
  onReset?: () => void;
  onConfirm?: () => void;
};

export default function FilterPopUp(props: Props) {
  return (
    <View className="absolute bottom-0">
      <PopUpCard
        onClose={props.onClose && props.onClose}
        title="筛选规则"
        content={
          <View className="p-4 pt-0">
            <View className="py-2.5" style={{ flexDirection: "row", gap: 12 }}>
              {/* 屬性 */}
              <View
                className="flex-1"
                style={{
                  gap: 8,
                }}
              >
                {props.items.slice(0, props.items.length / 2).map((item) => (
                  <FilterItem
                    key={item.value}
                    selected={props.value.includes(item.value)}
                    onClick={() => {
                      props.onChange(
                        props.value.includes(item.value)
                          ? props.value.filter((i) => i !== item.value)
                          : [...props.value, item.value]
                      );
                    }}
                    icon={item.icon}
                  >
                    {item.name}
                  </FilterItem>
                ))}
              </View>
              {/* 命途 */}
              <View
                className="flex-1"
                style={{
                  gap: 8,
                }}
              >
                {props.items.slice(props.items.length / 2).map((item) => (
                  <FilterItem
                    key={item.value}
                    selected={props.value.includes(item.value)}
                    onClick={() => {
                      props.onChange(
                        props.value.includes(item.value)
                          ? props.value.filter((i) => i !== item.value)
                          : [...props.value, item.value]
                      );
                    }}
                    icon={item.icon}
                  >
                    {item.name}
                  </FilterItem>
                ))}
              </View>
            </View>
            <View
              style={{
                flexDirection: "row",
                alignItems: "center",
                gap: 25,
              }}
            >
              <Button
                onPress={props.onReset}
                hasShadow={false}
                width={140}
                height={46}
              >
                <Text className="font-[HY65] text-[#222] text-[16px]">
                  重置
                </Text>
              </Button>
              <Button
                onPress={props.onConfirm}
                hasShadow={false}
                width={140}
                height={46}
              >
                <Text className="font-[HY65] text-[#222] text-[16px]">
                  确定
                </Text>
              </Button>
            </View>
          </View>
        }
      />
    </View>
  );
}
