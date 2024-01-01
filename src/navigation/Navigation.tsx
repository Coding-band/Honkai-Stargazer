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

export default function Navigation() {
  const Stack = createNativeStackNavigator();
  return (
    <NavigationContainer>
      <Stack.Navigator
        screenOptions={{
          animation: "default",
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
          options={{ headerShown: false, animation: "fade_from_bottom" }}
        />
        <Stack.Screen
          name={SCREENS.SettingPage.id}
          component={SettingScreen}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name={SCREENS.WallPaperPage.id}
          component={WallPaperScreen}
          options={{ headerShown: false }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
