import "./app.d";

import { Platform, Text, TextInput, View } from "react-native";
import { useFonts } from "expo-font";
import { useCallback, useEffect, useRef, useState } from "react";
import * as SplashScreen from "expo-splash-screen";
import * as NavigationBar from "expo-navigation-bar";
import { ClickOutsideProvider } from "./lib/react-native-click-outside/src";
import { ClickOutsideProvider as ClickOutsideProviderOffical } from "react-native-click-outside";
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
import { pushExpoNotiType } from "./src/notifications/constant/pushExpoNotiType";
import { SCREENS } from "./src/constant/screens";
import checkCharWeightList from "./src/hooks/charWeightList/checkCharWeightList";
import getSurveyURL from "./src/hooks/survey/getSurveyURL";


// import playground for testing
// import "./playground";

//SplashScreen.preventAutoHideAsync();
const queryClient = new QueryClient();

//https://stackoverflow.com/questions/65192622/initializing-text-defaultprops-with-typescript
interface TextWithDefaultProps extends Text {
    defaultProps?: { allowFontScaling?: boolean };
}

interface TextInputWithDefaultProps extends TextInput {
    defaultProps?: { allowFontScaling?: boolean };
}

((Text as unknown) as TextWithDefaultProps).defaultProps = ((Text as unknown) as TextWithDefaultProps).defaultProps || {};
((Text as unknown) as TextWithDefaultProps).defaultProps!.allowFontScaling = false;
((TextInput as unknown) as TextInputWithDefaultProps).defaultProps = ((TextInput as unknown) as TextInputWithDefaultProps).defaultProps || {};
((TextInput as unknown) as TextInputWithDefaultProps).defaultProps!.allowFontScaling = false;


export default function App() {
  const [initialRouteName, setInitialRouteName] = useState(SCREENS.HomePage.id);

  useEffect(() => {
    // 在组件加载后设置导航栏
    async function setupNavigationBar() {
      await NavigationBar.setPositionAsync("absolute");
      await NavigationBar.setBackgroundColorAsync("#00000000");
      await NavigationBar.setVisibilityAsync("visible");
      //await NavigationBar.setBehaviorAsync('overlay-swipe');
    }

    if (Platform.OS === "android") {
      setupNavigationBar();
    }

    //用來檢查評分表
    checkCharWeightList();

    //用來更新問卷的
    getSurveyURL();
    
  }, []);

  const [fontsLoaded] = useFonts({
    //HY55: require("./assets/fonts/HYRunYuan-55W.ttf"),
    //HY65: require("./assets/fonts/HYRunYuan-65W.ttf"),
    //HY75: require("./assets/fonts/HYRunYuan-75W.ttf"),
    HY65: require("./assets/fonts/MiSans-Regular.ttf"),
  });

  const onLayoutRootView = useCallback(async () => {
    if (fontsLoaded) {
      //await SplashScreen.hideAsync();
    }
  }, [fontsLoaded]);

  if (!fontsLoaded) {
    return null;
  }

  return (
    <QueryClientProvider client={queryClient}>
      <Provider store={store}>
        <PersistGate persistor={persistor}>
          {/* <NotificationWrapper
            onResponseReceived={(data, type) => {
              //* 角色留言提及通知
              if (type === pushExpoNotiType.sendCharacterComment) {
                const charId = data.charId;
                setInitialRouteName("/Character/" + charId);
              }
            }}
          > */}
          <AppLanguageProvider>
            <TextLanguageProvider>
              <RootSiblingParent>
                <GestureHandlerRootView style={{ flex: 1 }}>
                  <ClickOutsideProviderOffical>
                    <View style={{ flex: 1 }} onLayout={onLayoutRootView}>
                      {/* <StatusBar hidden /> */}
                      <FixedProvider>
                        <Navigation initialRouteName={initialRouteName} />
                      </FixedProvider>
                    </View>
                  </ClickOutsideProviderOffical>
                </GestureHandlerRootView>
              </RootSiblingParent>
            </TextLanguageProvider>
          </AppLanguageProvider>
          {/* </NotificationWrapper> */}
        </PersistGate>
      </Provider>
    </QueryClientProvider>
  );
}
