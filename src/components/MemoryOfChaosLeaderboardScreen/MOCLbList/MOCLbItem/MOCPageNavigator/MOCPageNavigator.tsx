import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../locales";

type Props = {
  pageCount: number;
  page: number;
  onPageChange: (page: number) => void;
};

export default function MOCPageNavigator({
  pageCount,
  page,
  onPageChange,
}: Props) {
  const { language } = useAppLanguage();

  return (
    <View
      className="flex-row justify-center items-center pt-4"
      style={{ gap: 20 }}
    >
      <TouchableOpacity
        disabled={page === 0}
        onPress={() => {
          onPageChange(page - 1);
        }}
        activeOpacity={0.35}
        style={{ opacity: page === 0 ? 0.4 : 1 }}
        className="border border-[#DDDDDD20] rounded-[4px] px-4 py-3"
      >
        <Text className="text-text font-[HY65]">
          {LOCALES[language].PrevPage}
        </Text>
      </TouchableOpacity>

      <TouchableOpacity
        disabled={page === pageCount - 1}
        activeOpacity={0.35}
        style={{ opacity: page === pageCount - 1 ? 0.4 : 1 }}
        onPress={() => {
          onPageChange(page + 1);
        }}
        className="border border-[#DDDDDD20] rounded-[4px] px-4 py-3"
      >
        <Text className="text-text font-[HY65]">
          {LOCALES[language].NextPage}
        </Text>
      </TouchableOpacity>
    </View>
  );
}
