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

export default function Navigation() {
  const Stack = createNativeStackNavigator();
  return (
    <NavigationContainer>
      <Stack.Navigator
        screenOptions={{
          animation: Platform.OS === "ios" ? "default" : "none",
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
        <Stack.Screen name={SCREENS.LoginPage.id} component={LoginScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
