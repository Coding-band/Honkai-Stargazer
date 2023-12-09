import AsyncStorage from "@react-native-async-storage/async-storage";

const STORAGE_KEY = "user-is-accept-binding-policy";

export const getUserIsAcceptBindingPolicy = async () => {
  const jsonValue = await AsyncStorage.getItem(STORAGE_KEY);
  return jsonValue ? JSON.parse(jsonValue) : null;
};

export const setUserIsAcceptBindingPolicy = async (value: boolean) => {
  await AsyncStorage.setItem(STORAGE_KEY, JSON.stringify(value));
};
