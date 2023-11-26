import "./app.d";

import { StatusBar } from "expo-status-bar";
import { StyleSheet, Text, View } from "react-native";
import { useFonts } from "expo-font";
import { useCallback } from "react";
import * as SplashScreen from "expo-splash-screen";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import HomeScreen from "./src/screens/HomeScreen";
import { ClickOutsideProvider } from "react-native-click-outside";

// SplashScreen.preventAutoHideAsync();

export default function App() {
  // const [fontsLoaded] = useFonts({
  //   NotoSansSC: require("./assets/fonts/NotoSansSC-Regular.ttf"),
  // });

  // const onLayoutRootView = useCallback(async () => {
  //   if (fontsLoaded) {
  //     await SplashScreen.hideAsync();
  //   }
  // }, [fontsLoaded]);

  // if (!fontsLoaded) {
  //   return null;
  // }

  const Stack = createNativeStackNavigator();

  return (
    <ClickOutsideProvider>
      <View
        className="flex-1"
        // onLayout={onLayoutRootView}
      >
        <StatusBar hidden />
        <NavigationContainer>
          <Stack.Navigator>
            <Stack.Screen
              name="Home"
              component={HomeScreen}
              options={{ headerShown: false }}
            />
          </Stack.Navigator>
        </NavigationContainer>
      </View>
    </ClickOutsideProvider>
  );
}
