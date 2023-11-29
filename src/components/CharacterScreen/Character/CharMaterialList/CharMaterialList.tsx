import { View, Text, ScrollView } from "react-native";
import React from "react";
import MaterialCard from "../../../global/layout/MaterialCard/MaterialCard";

const TestMaterial1 = require("../../../../../assets/images/test-material-1.png");
const TestMaterial2 = require("../../../../../assets/images/test-material-2.png");
const TestMaterial3 = require("../../../../../assets/images/test-material-3.png");
const TestMaterial4 = require("../../../../../assets/images/test-material-4.png");
const TestMaterial5 = require("../../../../../assets/images/test-material-5.png");

export default function CharMaterialList() {
  return (
    <ScrollView horizontal className="mt-5">
      <View style={{ flexDirection: "row", gap: 14 }}>
        <MaterialCard count={148000} stars={3} image={TestMaterial1} />
        <MaterialCard count={30} stars={4} image={TestMaterial2} />
        <MaterialCard count={30} stars={2} image={TestMaterial3} />
        <MaterialCard count={15} stars={3} image={TestMaterial4} />
        <MaterialCard count={6} stars={4} image={TestMaterial5} />
        <MaterialCard count={148000} stars={3} image={TestMaterial1} />
        <MaterialCard count={30} stars={4} image={TestMaterial2} />
      </View>
    </ScrollView>
  );
}
