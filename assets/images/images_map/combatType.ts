const CombatType = {
  Imaginary: {
    icon: require("../ui_icon/element_imaginary.webp"),
    localeName: "虚数", //Later can ask for require or sth else, suitable for translation ...
    constName: "Imaginary",
  },
  Quantum: {
    icon: require("../ui_icon/element_quantum.webp"),
    localeName: "量子",
    constName: "Quantum",
  },
  Lightning: {
    icon: require("../ui_icon/element_lightning.webp"),
    localeName: "雷",
    constName: "Lightning",
  },
  Thunder: {
    icon: require("../ui_icon/element_lightning.webp"),
    localeName: "雷",
    constName: "Lightning",
  },
  Fire: {
    icon: require("../ui_icon/element_fire.webp"),
    localeName: "火",
    constName: "Fire",
  },
  Ice: {
    icon: require("../ui_icon/element_ice.webp"),
    localeName: "冰",
    constName: "Ice",
  },
  Wind: {
    icon: require("../ui_icon/element_wind.webp"),
    localeName: "风",
    constName: "Wind",
  },
  Physical: {
    icon: require("../ui_icon/element_physical.webp"),
    localeName: "物理",
    constName: "Physical",
  },
};

export default CombatType;
