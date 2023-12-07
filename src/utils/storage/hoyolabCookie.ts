import AsyncStorage from "@react-native-async-storage/async-storage";

export const setHoyolabCookie = async (value: string) => {
  try {
    await AsyncStorage.setItem("hoyolab-cookie", value);
  } catch (e) {
    // saving error
  }
};

export const getHoyolabCookie = async () => {
  try {
    const value = await AsyncStorage.getItem("hoyolab-cookie");
    if (value !== null) {
      // value previously stored
    }
  } catch (e) {
    // error reading value
  }
};
