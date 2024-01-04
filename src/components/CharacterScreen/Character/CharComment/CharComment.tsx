import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { Image } from "expo-image";
import CommentBox from "./CommentBox/CommentBox";
import db from "../../../../firebase/db";
import CharacterComments from "../../../../firebase/models/CharacterComments";
import CommentItem from "./CommentItem/CommentItem";
import useCharComments from "../../../../firebase/hooks/useCharComments";

type Props = {
  containerRef: any;
};

export default function CharComment(props: Props) {
  const { data: charComments } = useCharComments("1107");

  return (
    <CommentBox containerRef={props.containerRef}>
      {charComments?.comments.map((c, i) => (
        <CommentItem key={i} {...c} />
      ))}
    </CommentBox>
  );
}
