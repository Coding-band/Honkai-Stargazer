import { View, Text } from "react-native";
import React from "react";
import { LightconeName } from "../../../../../types/lightcone";
import useProfileCharFullData from "../../../../../context/UserCharDetailData/hooks/useProfileCharFullData";
import useProfileHsrInGameInfo from "../../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import { Image } from "expo-image";
import AttributeImage from "../../../../../../assets/images/images_map/attributeImage";

export default React.memo(function LightconeAttribute({
  lcId,
  lcFullData,
  lcInGameData,
}: {
  lcId: LightconeName;
  lcFullData: any;
  lcInGameData: any;
}) {
  const attributes = [
    {
      key: "hp",
      icon: AttributeImage.hp,
      value: Math.floor(
        lcInGameData.attributes.filter((attr: any) => attr.field === "hp")[0]
          ?.value
      ),
    },
    {
      key: "atk",
      icon: AttributeImage.atk,
      value: Math.floor(
        lcInGameData.attributes.filter((attr: any) => attr.field === "atk")[0]
          ?.value
      ),
    },
    {
      key: "def",
      icon: AttributeImage.def,
      value: Math.floor(
        lcInGameData.attributes.filter((attr: any) => attr.field === "def")[0]
          ?.value
      ),
    },
  ];

  return (
    lcInGameData && (
      <View
        style={{
          flexDirection: "row",
          gap: 4,
          flexWrap: "wrap",
          justifyContent: "center",
        }}
      >
        {attributes.map(
          (attr) =>
            attr.value ? (
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image cachePolicy="none" source={attr.icon} className="w-6 h-6" />
                <Text className="text-text text-[14px] font-[HY65]">
                  {" "}
                  {attr.value}
                </Text>
              </View>
            ) : (<></>)
        )}
      </View>
    )
  );
});
