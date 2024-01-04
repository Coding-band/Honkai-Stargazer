import { KeyboardAvoidingView, View } from "react-native";
import React, { useEffect, useState } from "react";
import { Image } from "expo-image";
import CommentBox from "./CommentBox/CommentBox";
import db from "../../../../firebase/db";
import CharacterComments from "../../../../firebase/models/CharacterComments";
import CommentItem from "./CommentItem/CommentItem";
import useCharComments from "../../../../firebase/hooks/useCharComments";
import CommentInput from "./CommentInput/CommentInput";
import useCharId from "../../../../context/CharacterData/hooks/useCharId";
import { findKey } from "lodash";
import officalCharId from "../../../../../map/character_offical_id_map";

type Props = {
  containerRef: any;
};

export default function CharComment(props: Props) {
  const charId = useCharId();
  const officalId = findKey(officalCharId, (v) => v === charId);

  const { data: charComments } = useCharComments(officalId || "");

  return (
    <CommentBox containerRef={props.containerRef} bottom={<CommentInput />}>
      {charComments?.comments
        ?.slice()
        ?.reverse()
        ?.map((c, i) => (
          <CommentItem key={i} {...c} />
        ))}
    </CommentBox>
  );
}
