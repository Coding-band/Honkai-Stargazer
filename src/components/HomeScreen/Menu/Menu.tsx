import React from "react";
import { View, Text, ScrollView } from "react-native";
import { cn } from "../../../utils/cn";
import MenuItem from "./MenuItem/MenuItem";
import {
  BaseballCap,
  Books,
  Calendar,
  ClockClockwise,
  Gauge,
  MapTrifold,
  MathOperations,
  MedalMilitary,
  Moon,
  Person,
  Planet,
  ShootingStar,
  StarOfDavid,
  Sword,
  Ticket,
  Users,
} from "phosphor-react-native";
import MenuItemLarge from "./MenuItemLarge/MenuItemLarge";

export default function Menu() {
  return (
    <ScrollView >
      <View
        className={cn("pt-3 px-5")}
        style={{ flexDirection: "row", flexWrap: "wrap", gap: 13 }}
      >
        <MenuItem Icon={Person}>角色</MenuItem>
        <MenuItem Icon={Sword}>光锥</MenuItem>
        <MenuItem Icon={BaseballCap}>遺器</MenuItem>
        <MenuItem Icon={MedalMilitary}>混沌回忆</MenuItem>
        <MenuItemLarge Icon={Moon}>开拓力</MenuItemLarge>
        <MenuItem Icon={Calendar}>100/500</MenuItem>
        <MenuItem Icon={Planet}>2700/3500</MenuItem>
        <MenuItemLarge Icon={Users}>派遣委托</MenuItemLarge>
        <MenuItem Icon={MathOperations}>养成计算</MenuItem>
        <MenuItem Icon={Gauge}>伤害模拟</MenuItem>
        <MenuItem Icon={Books}>百科</MenuItem>
        <MenuItem Icon={MapTrifold}>地图</MenuItem>
        <MenuItem Icon={ShootingStar}>祈愿分析</MenuItem>
        <MenuItem Icon={StarOfDavid}>祈愿模拟</MenuItem>
        <MenuItem Icon={Ticket}>兑换码</MenuItem>
        <MenuItem Icon={ClockClockwise}>未来卡池</MenuItem>
        {/*  */}
        <MenuItem Icon={Person}>角色</MenuItem>
        <MenuItem Icon={Sword}>光锥</MenuItem>
        <MenuItem Icon={BaseballCap}>遺器</MenuItem>
        <MenuItem Icon={MedalMilitary}>混沌回忆</MenuItem>
        <MenuItem Icon={Person}>角色</MenuItem>
        <MenuItem Icon={Sword}>光锥</MenuItem>
        <MenuItem Icon={BaseballCap}>遺器</MenuItem>
        <MenuItem Icon={MedalMilitary}>混沌回忆</MenuItem>
        <MenuItem Icon={Person}>角色</MenuItem>
        <MenuItem Icon={Sword}>光锥</MenuItem>
        <MenuItem Icon={BaseballCap}>遺器</MenuItem>
        <MenuItem Icon={MedalMilitary}>混沌回忆</MenuItem>
        <MenuItem Icon={Person}>角色</MenuItem>
        <MenuItem Icon={Sword}>光锥</MenuItem>
        <MenuItem Icon={BaseballCap}>遺器</MenuItem>
        <MenuItem Icon={MedalMilitary}>混沌回忆</MenuItem>
      </View>
    </ScrollView>
  );
}
