import "./app.d";

import { Platform, View } from "react-native";
import { useFonts } from "expo-font";
import { useCallback, useEffect } from "react";
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

// import playground for testing
import "./playground";

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

  return (
    <Provider store={store}>
      <PersistGate persistor={persistor}>
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
      </PersistGate>
    </Provider>
  );
}
