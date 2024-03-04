import { View } from "react-native";
import React, { useLayoutEffect, useState } from "react";
import { ScrollView, TouchableOpacity } from "react-native-gesture-handler";
import { Shadow } from "react-native-shadow-2";

import ListboxItem from "./ListboxItem/ListboxItem";
import { useClickOutside } from "react-native-click-outside";

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
    <View style={props.style} >
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
          style={[
            {
              position: "absolute",
              top: props.top,
              bottom: props.bottom,
              maxHeight: 150,
              alignSelf: "center",
              borderRadius: 12,
              backgroundColor: "#555555"
            },
          ]}
        >
          <ScrollView >
            <View ref={ref}>
              {props.children.map((listboxitem) => (
                <ListboxItem
                  key={listboxitem.props.value.versionCode}
                  showSelectPoint={false}
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
          </ScrollView>
        </View>
      )}
    </View>
  );
}

Listbox.Item = function ({ children }: { children: any; value: any }) {
  return children;
};
