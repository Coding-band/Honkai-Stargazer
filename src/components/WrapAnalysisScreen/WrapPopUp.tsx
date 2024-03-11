import {
    View,
    Pressable,
    Dimensions,
    Keyboard,
} from "react-native";

import React, { useEffect, useState } from "react";
import ReactNativeModal from "react-native-modal";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";
import PopUpCard from "../global/PopUpCard/PopUpCard";
import WrapLinkPaste from "./WrapLinkPaste";
import { LOCALES } from "../../../locales";

type Props = {
    isOpened : boolean;
    setIsOpened : (open : boolean) => void;
    setWrapURL : (url : string) => void;
};

export default function WrapPopUp(props : Props) {
    const [isVisable, setIsVisable] = useState(true);
    const { language } = useAppLanguage();
    const [isUsingLinkPaste, setIsUsingLinkPaste] = useState(true);
    const [isAgreedSpecialLoginPolicy, setIsAgreedSpecialLoginPolicy] = useState(false);

    return (
        <ReactNativeModal
            useNativeDriverForBackdrop
            isVisible={isVisable}
            statusBarTranslucent
            deviceHeight={Dimensions.get("screen").height}
        >
            <Pressable
                onPress={Keyboard.dismiss}
                style={{
                    flex: 1,
                    justifyContent: "center",
                    alignItems: "center",
                    gap: 24,
                    transform: [{ translateY: 24 }],
                }}
            >
                <PopUpCard
                    onClose={() => {
                        setIsVisable(false);
                        props.setIsOpened(false);
                    }}
                    title={
                        LOCALES[language].WrapPopUpURLTitle
                    }
                    content={
                        <View className="p-4">
                            <WrapLinkPaste setWrapURL={props.setWrapURL} setClose={() => {
                                setIsVisable(false);
                                props.setIsOpened(false);
                            }}/>
                        </View>
                    }
                />
            </Pressable>
        </ReactNativeModal>
    );
}
