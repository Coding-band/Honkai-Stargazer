import { KeyboardAvoidingView, View, Text } from "react-native";
import React, { useEffect, useState } from "react";
import { Image } from "expo-image";
import CommentBox from "./CommentBox/CommentBox";
import CommentItem from "./CommentItem/CommentItem";
import useCharComments from "../../../../firebase/hooks/CharComments/useCharComments";
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

  const [input, setInput] = useState("");

  return (
    <>
      <CommentBox
        containerRef={props.containerRef}
        bottom={<CommentInput value={input} onChange={setInput} />}
      >
        {charComments?.map((c, i) => (
          <CommentItem key={i} {...c} input={input} setInput={setInput} />
        ))}
      </CommentBox>
    </>
  );
}
