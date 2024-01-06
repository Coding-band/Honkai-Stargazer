import "./app.d";

import { Platform, View } from "react-native";
import { useFonts } from "expo-font";
import { useCallback, useEffect, useRef, useState } from "react";
import * as SplashScreen from "expo-splash-screen";
import * as NavigationBar from "expo-navigation-bar";
import { ClickOutsideProvider } from "react-native-click-outside";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import FixedProvider from "./src/components/global/Fixed/FixedProvider";
import Navigation from "./src/navigation/Navigation";
import { QueryClient, QueryClientProvider } from "react-query";
import { Provider } from "react-redux";
import { persistor, store } from "./src/redux/store";
import { PersistGate } from "redux-persist/integration/react";
import { RootSiblingParent } from "react-native-root-siblings";
import TextLanguageProvider from "./src/language/TextLanguage/TextLanguageProvider";
import AppLanguageProvider from "./src/language/AppLanguage/AppLanguageProvider";
import * as Device from "expo-device";
import * as Notifications from "expo-notifications";

// import playground for testing
// import "./playground";

Notifications.setNotificationHandler({
  handleNotification: async () => ({
    shouldShowAlert: true,
    shouldPlaySound: false,
    shouldSetBadge: false,
  }),
});

const queryClient = new QueryClient();

SplashScreen.preventAutoHideAsync();

export default function App() {
  registerForPushNotificationsAsync();

  useEffect(() => {
    // 在组件加载后设置导航栏
    async function setupNavigationBar() {
      await NavigationBar.setPositionAsync("absolute");
      await NavigationBar.setBackgroundColorAsync("#00000000");
      await NavigationBar.setVisibilityAsync("hidden");
    }

    if (Platform.OS === "android") {
      setupNavigationBar();
    }
  }, []);

  const [fontsLoaded] = useFonts({
    HY55: require("./assets/fonts/MiSans-Regular.ttf"),
    HY65: require("./assets/fonts/MiSans-Medium.ttf"),
    HY75: require("./assets/fonts/MiSans-Semibold.ttf"),
  });

  const onLayoutRootView = useCallback(async () => {
    if (fontsLoaded) {
      await SplashScreen.hideAsync();
    }
  }, [fontsLoaded]);

  if (!fontsLoaded) {
    return null;
  }

  return (
    <Provider store={store}>
      <PersistGate persistor={persistor}>
        <AppLanguageProvider>
          <TextLanguageProvider>
            <RootSiblingParent>
              <QueryClientProvider client={queryClient}>
                <GestureHandlerRootView style={{ flex: 1 }}>
                  <ClickOutsideProvider>
                    <View style={{ flex: 1 }} onLayout={onLayoutRootView}>
                      {/* <StatusBar hidden /> */}
                      <FixedProvider>
                        <Navigation />
                      </FixedProvider>
                    </View>
                  </ClickOutsideProvider>
                </GestureHandlerRootView>
              </QueryClientProvider>
            </RootSiblingParent>
          </TextLanguageProvider>
        </AppLanguageProvider>
      </PersistGate>
    </Provider>
  );
}

async function registerForPushNotificationsAsync() {
  let token;

  if (Platform.OS === "android") {
    await Notifications.setNotificationChannelAsync("default", {
      name: "default",
      importance: Notifications.AndroidImportance.MAX,
      vibrationPattern: [0, 250, 250, 250],
      lightColor: "#FF231F7C",
    });
  }

  if (Device.isDevice) {
    const { status: existingStatus } =
      await Notifications.getPermissionsAsync();
    let finalStatus = existingStatus;
    if (existingStatus !== "granted") {
      const { status } = await Notifications.requestPermissionsAsync();
      finalStatus = status;
    }
    if (finalStatus !== "granted") {
      alert("Failed to get push token for push notification!");
      return;
    }
    token = (await Notifications.getExpoPushTokenAsync()).data;
  } else {
    alert("Must use physical device for Push Notifications");
  }

  return token;
}
