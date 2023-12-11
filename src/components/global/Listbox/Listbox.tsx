import { View, Text, Pressable } from "react-native";
import React, { useState } from "react";
import { TouchableOpacity } from "react-native-gesture-handler";
import { Shadow } from "react-native-shadow-2";
import { cn } from "../../../utils/css/cn";
import ListboxItem from "./ListboxItem/ListboxItem";

type Props = {
  button: React.ReactNode;
  value: any;
  onChange: (v: any) => void;
  children: any[];
};

export default function Listbox(props: Props) {
  const [open, setOpen] = useState(false);
  return (
    <View>
      <TouchableOpacity
        activeOpacity={0.65}
        onPress={() => {
          setOpen(!open);
        }}
      >
        {props.button}
      </TouchableOpacity>
      {open && (
        <View
          className="absolute bottom-[46px] w-full bg-[#DDDDDD]"
          style={{
            shadowColor: "#00000025",
            shadowOffset: { width: 0, height: 4 },
            elevation: 16,
          }}
        >
          {props.children.map((listboxitem) => (
            <ListboxItem
              key={listboxitem.props.value}
              onPress={() => {
                setOpen(false);
                setTimeout(() => {
                  props.onChange(listboxitem.props.value);
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
