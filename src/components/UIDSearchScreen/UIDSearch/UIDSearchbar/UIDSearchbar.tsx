import { View, Text, TextInput, TouchableOpacity } from 'react-native'
import React from 'react'
import Button from '../../../global/Button/Button'
import { Image } from 'expo-image'

type Props = {
    value: string | undefined;
    onChangeText: ((text: string) => void) | undefined;
    onSubmit: () => void;
    placeholder: string;
}

export default function UIDSearchbar(props: Props) {
    return (
        <Button
            style={{ marginHorizontal: 30 }}
            disable
            width={"90%"}
            height={46}
        >
            <View
                className="w-full h-full pr-3 pl-6"
                style={{ flexDirection: "row", alignItems: "center", gap: 9 }}
            >

                <TextInput
                    value={props.value}
                    onChangeText={props.onChangeText}
                    className="w-full font-[HY65] text-[16px]"
                    placeholder={props.placeholder}
                    onSubmitEditing={props.onSubmit}
                    keyboardType="number-pad"
                />
                <TouchableOpacity activeOpacity={0.35} onPress={props.onSubmit}>
                    <Image cachePolicy="none" style={{ width: 18, height: 18 }} source={SearchIcon} />
                </TouchableOpacity>
            </View>
        </Button>
    )
}

const SearchIcon = require("../../../../../assets/icons/Search.svg")