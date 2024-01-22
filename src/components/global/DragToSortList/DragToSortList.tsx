import React, { ReactElement } from "react";
import Animated, {
  useAnimatedRef,
  useAnimatedScrollHandler,
  useSharedValue,
} from "react-native-reanimated";

import Item from "./Item";
import { COL, Positions, SIZE } from "./config";
import { Dimensions } from "react-native";

interface ListProps {
  children: ReactElement<{ id: string }>[];
  editing: boolean;
  onDragEnd: (diff: Positions) => void;
}

const DragToSortList = ({ children, editing, onDragEnd }: ListProps) => {
  const scrollY = useSharedValue(0);
  const scrollView = useAnimatedRef<Animated.ScrollView>();
  const positions = useSharedValue<Positions>(
    Object.assign(
      {},
      ...children.map((child, index) => ({ [child.props.id]: index }))
    )
  );
  const onScroll = useAnimatedScrollHandler({
    onScroll: ({ contentOffset: { y } }) => {
      scrollY.value = y;
    },
  });

  return (
    <Animated.ScrollView
      onScroll={onScroll}
      ref={scrollView}
      contentContainerStyle={{
        height: Dimensions.get("screen").height - 316,
      }}
      showsVerticalScrollIndicator={false}
      bounces={false}
      scrollEventThrottle={16}
   >
      {children.map((child) => {
        return (
          <Item
            key={child.props.id}
            positions={positions}
            id={child.props.id}
            editing={editing}
            onDragEnd={onDragEnd}
            scrollView={scrollView}
            scrollY={scrollY}
          >
            {child}
          </Item>
        );
      })}
    </Animated.ScrollView>
  );
};

export default DragToSortList;
