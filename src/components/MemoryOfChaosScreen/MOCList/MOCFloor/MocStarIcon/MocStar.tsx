import React from "react";
import { Image } from "expo-image";

const StarIcon = require("./icons/Star.svg");

export default function MocStar() {
  return <Image source={StarIcon} className="w-6 h-6" />;
}
