import { ExpoConfig, ConfigContext } from "expo/config";

// process.env.NODE_ENV;
export const ENV = "beta";

export const APP_NAME = {
  development: "Stargazer Development Version",
  beta: "Stargazer",
  production: "Stargazer",
};

export const VERSION = {
  development: "2.0.0",
  beta: "2.0.16",
  production: "2.0.0",
};

export const APP_ICON = {
  iosBETA: "./assets/IOS-BETA.png",
  androidBETA: "./assets/Android-BETA.png",
  ios: "./assets/IOS.png",
  android: "./assets/Android.png",
};


export const PACKAGE_NAME = {
  iosBETA: "com.voc.honkaistargazerbeta",
  androidBETA: "com.voc.honkai_stargazer_beta",
  ios: "com.voc.honkaistargazer",
  android: "com.voc.honkai_stargazer_gp",
};

export default ({ config }: ConfigContext): ExpoConfig => ({
  ...config,
  name: askEnvDo({
    development: APP_NAME.development,
    beta: APP_NAME.beta,
    production: APP_NAME.production,
  }),
  slug: "honkai-stargazer",
  version: askEnvDo({
    development: VERSION.development,
    beta: VERSION.beta,
    production: VERSION.production,
  }),
  orientation: "portrait",
  icon: askEnvDo({
    development: APP_ICON.iosBETA,
    beta: APP_ICON.iosBETA,
    production: APP_ICON.ios,
  }),
  userInterfaceStyle: "light",
  splash: {
    image: "./assets/splash.png",
    resizeMode: "contain",
    backgroundColor: "#000000",
  },
  assetBundlePatterns: ["**/*"],
  ios: {
    bitcode: false,
    googleServicesFile: "./GoogleService-Info.plist",
    supportsTablet: true,
    bundleIdentifier: askEnvDo({
      development: PACKAGE_NAME.iosBETA,
      beta: PACKAGE_NAME.iosBETA,
      production: PACKAGE_NAME.ios,
    }),
    infoPlist: {
      NSAppTransportSecurity: {
        NSAllowsArbitraryLoads: true,
      },
    },
  },
  android: {
    googleServicesFile: "./google-services.json",
    adaptiveIcon: {
      foregroundImage: askEnvDo({
        development: APP_ICON.androidBETA,
        beta: APP_ICON.androidBETA,
        production: APP_ICON.android,
      }),
      backgroundColor: "#000000",
    },
    package: askEnvDo({
      development: PACKAGE_NAME.androidBETA,
      beta: PACKAGE_NAME.androidBETA,
      production: PACKAGE_NAME.android,
    }),
  },
  web: {
    favicon: "./assets/favicon.png",
  },
  extra: {
    eas: {
      projectId: "bb2e2b0e-bd4c-4c64-9feb-6d935193567c"
    }
  },
  owner: "vocaloid2048",
  runtimeVersion: {
    policy: "appVersion",
  },
  updates: {
    url: "https://u.expo.dev/bb2e2b0e-bd4c-4c64-9feb-6d935193567c",
  },
  plugins: [
    "@react-native-firebase/app",
    [
      "expo-build-properties",
      {
        ios: {
          useFrameworks: "static",
        },
      },
    ],
    [
      "expo-media-library",
      {
        photosPermission: "Allow $(PRODUCT_NAME) to access your photos.",
        savePhotosPermission: "Allow $(PRODUCT_NAME) to save photos.",
        isAccessMediaLocationEnabled: true,
        requestLegacyExternalStorage: true,
      },
    ],
    [
      "expo-image-picker",
      {
        photosPermission: "custom photos permission",
        cameraPermission: "Allow $(PRODUCT_NAME) to open the camera",

        "//": "Disables the microphone permission",
        microphonePermission: false,
      },
    ],
    "expo-build-properties",
  ],
});

function askEnvDo({
  development,
  beta,
  production,
}: {
  development: any;
  beta: any;
  production: any;
}) {
  if (ENV === "development") {
    return development;
  } else if (ENV === "beta") {
    return beta;
  } else if (ENV === "production") {
    return production;
  } else {
    return beta;
  }
}
