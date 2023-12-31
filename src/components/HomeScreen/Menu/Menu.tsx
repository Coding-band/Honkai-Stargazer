import React, { useEffect, useState } from "react";
import {
  View,
  ScrollView,
  LayoutChangeEvent,
  Platform,
  Text,
} from "react-native";
import { cn } from "../../../utils/css/cn";
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
  Planet,
  ShootingStar,
  StarOfDavid,
  Sword,
  Ticket,
  Users,
} from "phosphor-react-native";
import MenuItemLarge from "./MenuItemLarge/MenuItemLarge";
import { Dimensions } from "react-native";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../constant/screens";
import useHsrNote from "../../../hooks/hoyolab/useHsrNote";
import _ from "lodash";
import { formatTimePoint } from "../../../utils/date/formatTime";

const OEPN_LONG_MENU = false;

export default function Menu() {
  const navigation = useNavigation();

  const [layout, setLayout] = useState({ width: 0, height: 0 });
  const [menuItemSize, setMenuItemSize] = useState({ width: 0, height: 0 });
  const [menuItemLargeSize, setMenuItemLargeSize] = useState({
    width: 0,
    height: 0,
  });

  const playerNote = useHsrNote();

  useEffect(() => {
    MenuItem;
    setMenuItemSize({
      width: (layout.width - 72) / 4,
      height: (((layout.width - 72) / 4) * 9) / 8,
    });
    setMenuItemLargeSize({
      width: ((layout.width - 72) / 4) * 2 + 13,
      height: (((layout.width - 72) / 4) * 9) / 8,
    });
  }, [layout]);

  const onLayout = (event: LayoutChangeEvent) => {
    const { width, height } = event.nativeEvent.layout;
    setLayout({ width, height });
  };

  return (
    <View
      style={{
        height: 530,
      }}
    >
      <ScrollView>
        <View
          onLayout={onLayout}
          className={cn("w-full pt-3 px-4 pb-5")}
          style={{ flexDirection: "row", flexWrap: "wrap", gap: 13 }}
        >
          <MenuItem
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.CharacterListPage.id);
            }}
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={SCREENS.CharacterListPage.icon}
          >
            {SCREENS.CharacterListPage.shortName}
          </MenuItem>
          <MenuItem
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.LightconeListPage.id);
            }}
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={SCREENS.LightconeListPage.icon}
          >
            {SCREENS.LightconeListPage.shortName}
          </MenuItem>
          <MenuItem
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.RelicListPage.id);
            }}
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={SCREENS.RelicListPage.icon}
          >
            {SCREENS.RelicListPage.shortName}
          </MenuItem>
          <MenuItem
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.MemoryOfChaosPage.id);
            }}
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={SCREENS.MemoryOfChaosPage.icon}
          >
            {SCREENS.MemoryOfChaosPage.shortName}
          </MenuItem>
          <MenuItemLarge
            width={menuItemLargeSize.width}
            height={menuItemLargeSize.height}
            Icon={Moon}
            title={
              playerNote.data ? (
                <>
                  <Text className="text-[24px] leading-[26px]">
                    {playerNote.data?.current_stamina}
                  </Text>
                  <Text>/{playerNote.data?.max_stamina}</Text>
                </>
              ) : (
                <></>
              )
            }
            subtitle={
              playerNote.data ? (
                <Text>
                  {playerNote.data.current_stamina >= 240
                    ? "開拓力已滿"
                    : formatTimePoint(
                        (playerNote.data.max_stamina -
                          playerNote.data.current_stamina) *
                          360
                      )}
                </Text>
              ) : (
                <></>
              )
            }
          >
            开拓力
          </MenuItemLarge>
          {/* 每日實訓 */}
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Calendar}
          >
            {playerNote.data
              ? `${playerNote.data?.current_train_score}/${playerNote.data?.max_train_score}`
              : "暫無數據"}
          </MenuItem>
          <MenuItem
            width={menuItemSize.width}
            height={menuItemSize.height}
            Icon={Planet}
          >
            暫無數據
          </MenuItem>
          <MenuItemLarge
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.ExpeditionPage.id);
            }}
            width={menuItemLargeSize.width}
            height={menuItemLargeSize.height}
            Icon={Users}
            title={
              playerNote.data ? (
                <>
                  <Text className="text-[24px] leading-[26px]">
                    {playerNote.data?.accepted_epedition_num}
                  </Text>
                  <Text>/{playerNote.data?.total_expedition_num}</Text>
                </>
              ) : (
                <></>
              )
            }
            subtitle={
              playerNote.data ? (
                <Text>
                  {_.maxBy(
                    playerNote.data.expeditions,
                    (e: any) => e.remaining_time
                  ).remaining_time === 0
                    ? "已完成"
                    : formatTimePoint(
                        _.maxBy(
                          playerNote.data.expeditions,
                          (e: any) => e.remaining_time
                        ).remaining_time
                      )}
                </Text>
              ) : (
                <></>
              )
            }
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
            Icon={SCREENS.MapPage.icon}
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.MapPage.id);
            }}
          >
            {SCREENS.MapPage.shortName}
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
          {OEPN_LONG_MENU && (
            <>
              <MenuItem
                onPress={() => {
                  // @ts-ignore
                  navigation.navigate(SCREENS.CharacterListPage.id);
                }}
                width={menuItemSize.width}
                height={menuItemSize.height}
                Icon={SCREENS.CharacterListPage.icon}
              >
                {SCREENS.CharacterListPage.shortName}
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
              <MenuItem
                onPress={() => {
                  // @ts-ignore
                  navigation.navigate(SCREENS.CharacterListPage.id);
                }}
                width={menuItemSize.width}
                height={menuItemSize.height}
                Icon={SCREENS.CharacterListPage.icon}
              >
                {SCREENS.CharacterListPage.shortName}
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
            </>
          )}
        </View>
      </ScrollView>
    </View>
  );
}
