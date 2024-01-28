import { View } from "react-native";
import React, { useLayoutEffect, useState } from "react";
import { TouchableOpacity } from "react-native-gesture-handler";
import { Shadow } from "react-native-shadow-2";

import ListboxItem from "./ListboxItem/ListboxItem";
import { useClickOutside } from "../../../../lib/react-native-click-outside/src/useClickOutside";
import { BlurView } from "expo-blur";

type Props = {
  button: React.ReactNode;
  value?: any;
  onChange?: (v: any) => void;
  onOpen?: (v: boolean) => void;
  children: any[];
  bottom?: number;
  top?: number;
  style?: any;
};

export default function Listbox(props: Props) {
  const [open, setOpen] = useState(false);

  useLayoutEffect(() => {
    props.onOpen && props.onOpen(open);
  }, [open]);

  const ref = useClickOutside<View>(() => {
    setOpen(false);
  });

  return (
    <View style={props.style}>
      <TouchableOpacity
        onPress={() => {
          setOpen(!open);
        }}
        activeOpacity={0.65}
      >
        {props.button}
      </TouchableOpacity>
      {open && (
        <Shadow
          startColor="#00000025"
          distance={16}
          offset={[4, 8]}
          paintInside
          style={[
            {
              position: "absolute",
              top: props.top,
              bottom: props.bottom,
              width: "100%",
            },
          ]}
        >
          <View ref={ref} className="bg-[#DDDDDD]">
            {props.children.map((listboxitem) => (
              <ListboxItem
                key={listboxitem.props.value}
                onPress={() => {
                  setOpen(false);
                  setTimeout(() => {
                    props.onChange && props.onChange(listboxitem.props.value);
                  });
                }}
                selected={props.value === listboxitem.props.value}
              >
                {listboxitem}
              </ListboxItem>
            ))}
          </View>
        </Shadow>
      )}
    </View>
  );
}

Listbox.Item = function ({ children }: { children: any; value: any }) {
  return children;
};
