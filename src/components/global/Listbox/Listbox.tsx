import { View } from "react-native";
import React, { useLayoutEffect, useState } from "react";
import { TouchableOpacity } from "react-native-gesture-handler";
import { Shadow } from "react-native-shadow-2";

import ListboxItem from "./ListboxItem/ListboxItem";
import { useClickOutside } from "react-native-click-outside";

type Props = {
  button: React.ReactNode;
  value?: any;
  onChange?: (v: any) => void;
  onOpen?: (v: boolean) => void;
  children: any[];
  top?: number;
  bottom?: number;
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
    <View>
      <TouchableOpacity
        onPress={() => {
          setOpen(!open);
        }}
        activeOpacity={0.65}
      >
        {props.button}
      </TouchableOpacity>
      {open && (
        <View
          ref={ref}
          className="absolute w-full bg-[#DDDDDD]"
          style={{
            shadowColor: "#00000025",
            shadowOffset: { width: 0, height: 4 },
            elevation: 16,
            top: props.top,
            bottom: props.bottom,
          }}
        >
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
      )}
    </View>
  );
}

Listbox.Item = function ({ children }: { children: any; value: any }) {
  return children;
};
