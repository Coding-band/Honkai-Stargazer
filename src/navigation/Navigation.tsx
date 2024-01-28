import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { Platform } from "react-native";
import { SCREENS } from "../constant/screens";
import HomeScreen from "../screens/HomeScreen";
import CharacterListScreen from "../screens/CharacterListScreen";
import CharacterScreen from "../screens/CharacterScreen";
import LightconeListScreen from "../screens/LightconeListScreen";
import LightconeScreen from "../screens/LightconeScreen";
import LoginScreen from "../screens/LoginScreen";
import MapScreen from "../screens/MapScreen";
import ExpeditionScreen from "../screens/ExpeditionScreen";
import SettingScreen from "../screens/SettingScreen";
import WallPaperScreen from "../screens/WallPaperScreen";
import RelicListScreen from "../screens/RelicListScreen";
import RelicScreen from "../screens/RelicScreen";
import MemoryOfChaosScreen from "../screens/MemoryOfChaosScreen";
import EventListScreen from "../screens/EventListScreen";
import EventScreen from "../screens/EventScreen";
import CodeScreen from "../screens/CodeScreen";
import UserInfoScreen from "../screens/UserInfoScreen";
import UserCharDetailScreen from "../screens/UserCharDetailScreen";
import UIDSearchScreen from "../screens/UIDSearchScreen";
import MemoryOfChaosStatsScreen from "../screens/MemoryOfChaosStatsScreen";
import MemoryOfChaosLeaderboardScreen from "../screens/MemoryOfChaosLeaderboardScreen";
import InviteScreen from "../screens/InviteScreen";
import ScoreLeaderboardScreen from "../screens/ScoreLeaderboardScreen";
import PureFictionScreen from "../screens/PureFictionScreen";
import PureFictionStatsScreen from "../screens/PureFictionStatsScreen";
import PureFictionLeaderboardScreen from "../screens/PureFictionLeaderboardScreen";
import DescriptionScreen from "../screens/DescriptionScreen";

export default function Navigation({
  initialRouteName,
}: {
  initialRouteName: string;
}) {
  const Stack = createNativeStackNavigator();

  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName={initialRouteName}
        screenOptions={{
          animation: Platform.OS === "ios" ? "simple_push" : "fade",
        }}
      >
        <Stack.Screen
          name={SCREENS.HomePage.id}
          component={HomeScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.CharacterListPage.id}
          component={CharacterListScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.CharacterPage.id}
          component={CharacterScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.LightconeListPage.id}
          component={LightconeListScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.LightconePage.id}
          component={LightconeScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.RelicListPage.id}
          component={RelicListScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.RelicPage.id}
          component={RelicScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.MemoryOfChaosPage.id}
          component={MemoryOfChaosScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.MemoryOfChaosStatsPage.id}
          component={MemoryOfChaosStatsScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.MemoryOfChaosLeaderboardPage.id}
          component={MemoryOfChaosLeaderboardScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.PureFictionPage.id}
          component={PureFictionScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.PureFictionStatsPage.id}
          component={PureFictionStatsScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.PureFictionLeaderboardPage.id}
          component={PureFictionLeaderboardScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.ExpeditionPage.id}
          component={ExpeditionScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.MapPage.id}
          component={MapScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.LoginPage.id}
          component={LoginScreen}
          options={{
            headerShown: false,
            animation: Platform.OS === "ios" ? "fade_from_bottom" : "none",
          }}
        />
        <Stack.Screen
          name={SCREENS.SettingPage.id}
          component={SettingScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.InvitePage.id}
          component={InviteScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.WallPaperPage.id}
          component={WallPaperScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.EventListPage.id}
          component={EventListScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.EventPage.id}
          component={EventScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.CodePage.id}
          component={CodeScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.UserInfoPage.id}
          component={UserInfoScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.UserCharDetailPage.id}
          component={UserCharDetailScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.UIDSearchPage.id}
          component={UIDSearchScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.ScoreLeaderboardPage.id}
          component={ScoreLeaderboardScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.DescriptionPage.id}
          component={DescriptionScreen}
          options={{ headerShown: false }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
