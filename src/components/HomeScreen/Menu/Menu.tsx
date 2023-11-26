import React, { useEffect, useState } from "react";
import { View, Text, ScrollView, LayoutChangeEvent } from "react-native";
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
import { Dimensions } from "react-native";

export default function Menu() {
  const [layout, setLayout] = useState({ width: 0, height: 0 });

  const [menuItemSize, setMenuItemSize] = useState({ width: 0, height: 0 });
  const [menuItemLargeSize, setMenuItemLargeSize] = useState({
    width: 0,
    height: 0,
  });

  useEffect(() => {
    setMenuItemSize({
      width: (layout.width - 88) / 4,
      height: (((layout.width - 88) / 4) * 9) / 8,
    });
    setMenuItemLargeSize({
      width: (((((layout.width - 88) / 4) * 9) / 8) * 172) / 90,
      height: (((layout.width - 88) / 4) * 9) / 8,
    });
  }, [layout]);

  const onLayout = (event: LayoutChangeEvent) => {
    const { width, height } = event.nativeEvent.layout;
    setLayout({ width, height });
  };

  return (
    <View
      style={{
        height: Dimensions.get("window").height - 298,
      }}
    >
      <ScrollView bounces>
        <View
          onLayout={onLayout}
          className={cn("w-full pt-3 px-5 pb-5")}
          style={{ flexDirection: "row", flexWrap: "wrap", gap: 13 }}
        >
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Person}
          >
            角色
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Sword}
          >
            光锥
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={BaseballCap}
          >
            遺器
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={MedalMilitary}
          >
            混沌回忆
          </MenuItem>
          <MenuItemLarge
            width={menuItemLargeSize.width}
            height={menuItemLargeSize.height}
            Icon={Moon}
          >
            开拓力
          </MenuItemLarge>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Calendar}
          >
            100/500
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Planet}
          >
            270/350
          </MenuItem>
          <MenuItemLarge
            width={menuItemLargeSize.width}
            height={menuItemLargeSize.height}
            Icon={Users}
          >
            派遣委托
          </MenuItemLarge>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={MathOperations}
          >
            养成计算
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Gauge}
          >
            伤害模拟
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Books}
          >
            百科
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={MapTrifold}
          >
            地图
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={ShootingStar}
          >
            祈愿分析
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={StarOfDavid}
          >
            祈愿模拟
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Ticket}
          >
            兑换码
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={ClockClockwise}
          >
            未来卡池
          </MenuItem>
          {/*  */}
          {/* <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Person}
          >
            角色
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Sword}
          >
            光锥
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={BaseballCap}
          >
            遺器
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={MedalMilitary}
          >
            混沌回忆
          </MenuItem>
          <MenuItemLarge
            width={menuItemLargeSize.width}
            height={menuItemLargeSize.height}
            Icon={Moon}
          >
            开拓力
          </MenuItemLarge>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Calendar}
          >
            100/500
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Planet}
          >
            2700/3500
          </MenuItem>
          <MenuItemLarge
            width={menuItemLargeSize.width}
            height={menuItemLargeSize.height}
            Icon={Users}
          >
            派遣委托
          </MenuItemLarge>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={MathOperations}
          >
            养成计算
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Gauge}
          >
            伤害模拟
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Books}
          >
            百科
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={MapTrifold}
          >
            地图
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={ShootingStar}
          >
            祈愿分析
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={StarOfDavid}
          >
            祈愿模拟
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Ticket}
          >
            兑换码
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={ClockClockwise}
          >
            未来卡池
          </MenuItem> */}
        </View>
      </ScrollView>
    </View>
  );
}
