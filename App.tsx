import "./app.d";

import { StatusBar } from "expo-status-bar";
import { Platform, StyleSheet, Text, View } from "react-native";
import { useFonts } from "expo-font";
import { useCallback } from "react";
import * as SplashScreen from "expo-splash-screen";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import HomeScreen from "./src/screens/HomeScreen";
import { ClickOutsideProvider } from "react-native-click-outside";
import CharacterListScreen from "./src/screens/CharacterListScreen";
import { SCREENS } from "./src/constant/screens";
import CharacterScreen from "./src/screens/CharacterScreen";

// SplashScreen.preventAutoHideAsync();

export default function App() {
  const [fontsLoaded] = useFonts({
    NotoSansSC: require("./assets/fonts/NotoSansSC-Regular.ttf"),
    HY55: require("./assets/fonts/HYRunYuan-55W.ttf"),
    HY65: require("./assets/fonts/HYRunYuan-65W.ttf"),
    HY75: require("./assets/fonts/HYRunYuan-75W.ttf"),
  });

  const onLayoutRootView = useCallback(async () => {
    if (fontsLoaded) {
      await SplashScreen.hideAsync();
    }
  }, [fontsLoaded]);

  if (!fontsLoaded) {
    return null;
  }

  const Stack = createNativeStackNavigator();

  return (
    <ClickOutsideProvider>
      <View style={{ flex: 1 }} onLayout={onLayoutRootView}>
        {/* <StatusBar hidden /> */}
        <NavigationContainer>
          <Stack.Navigator
            screenOptions={{
              animation: "none",
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
          </Stack.Navigator>
        </NavigationContainer>
      </View>
    </ClickOutsideProvider>
  );
}
