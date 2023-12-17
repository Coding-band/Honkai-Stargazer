import AsyncStorage from "@react-native-async-storage/async-storage";
import { useEffect, useState } from "react";

export default function useLocalState<T>(
  key: string,
  initial: any
): [T, React.Dispatch<React.SetStateAction<T>>] {
  const [value, setValue] = useState<T>(initial);

  useEffect(() => {
    AsyncStorage.getItem(key).then((data) => {
      if (data) {
        setValue(JSON.parse(data));
      } else {
        setValue(initial);
      }
    });
  }, []);

  useEffect(() => {
    AsyncStorage.setItem(key, JSON.stringify(value));
  }, [value]);

  return [value, setValue];
}
