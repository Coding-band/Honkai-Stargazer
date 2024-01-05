import { View, Text } from "react-native";
import React from "react";
import PopUpCard from "../PopUpCard/PopUpCard";
import Button from "../Button/Button";
import FilterItem from "./FilterItem/FilterItem";
import { ExpoImage } from "../../../types/image";
import _ from "lodash";
import useAppLanguage from "../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";

type Props = {
  items: { value: string; name: string; icon: ExpoImage }[];
  value: string[];
  onChange: (v: string[]) => void;
  onClose?: () => void;
  onReset?: () => void;
  onConfirm?: () => void;
};

export default function FilterPopUp(props: Props) {
  const { language } = useAppLanguage();
  return (
    <View className="absolute bottom-0 translate-x-[-6px] translate-y-[18px]">
      <PopUpCard
        onClose={props.onClose && props.onClose}
        title={LOCALES[language].FilterTitle}
        content={
          <View className="pt-0">
            <View
              className="px-4 py-2.5"
              style={{ flexDirection: "row", gap: 12 }}
            >
              <View
                className="flex-1"
                style={{
                  flexDirection: "row",
                  flexWrap: "wrap",
                  gap: 8,
                }}
              >
                {props.items
                  .slice(
                    0,
                    props.items.length % 2 === 0
                      ? props.items.length / 2
                      : props.items.length / 2 + 1
                  )
                  .map((item) => (
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
              <View
                className="flex-1"
                style={{
                  flexDirection: "row",
                  flexWrap: "wrap",
                  gap: 8,
                }}
              >
                {props.items
                  .slice(
                    props.items.length % 2 === 0
                      ? props.items.length / 2
                      : props.items.length / 2 + 1
                  )
                  .map((item) => (
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
              className="p-4 bg-[#333]"
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
                  {LOCALES[language].Reset}
                </Text>
              </Button>
              <Button
                onPress={props.onConfirm}
                hasShadow={false}
                width={140}
                height={46}
              >
                <Text className="font-[HY65] text-[#222] text-[16px]">
                  {LOCALES[language].OK}
                </Text>
              </Button>
            </View>
          </View>
        }
      />
    </View>
  );
}
