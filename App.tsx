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
import NotificationWrapper from "./src/notifications/NotificationWrapper";

// import playground for testing
// import "./playground";

const queryClient = new QueryClient();
SplashScreen.preventAutoHideAsync();

export default function App() {
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
        <NotificationWrapper>
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
        </NotificationWrapper>
      </PersistGate>
    </Provider>
  );
}
