import { View, findNodeHandle } from "react-native";
import React, { useState } from "react";
import { TouchableOpacity } from "react-native-gesture-handler";
import { Person, Sword } from "phosphor-react-native";

type Props = {
    selectedChild: number;
    setSelectedChild: (lotteryId: number) => void;
};
export default function LotteryHeaderChild({selectedChild,setSelectedChild} : Props){

    return(
        <View style={[{ flexDirection: 'row' }]}>
            <TouchableOpacity 
                id="charLottery1" 
                style={[{ paddingLeft: 3.5 },{ paddingRight: 3.5 }]}
                onPress={() => setSelectedChild(0)}
            >
                <Person size={32} color="white" weight={ selectedChild === 0 ? "fill" : "regular"}/>
            </TouchableOpacity>
            <TouchableOpacity id="charLottery2" style={[{ paddingLeft: 3.5 },{ paddingRight: 3.5 }]}
                onPress={() => setSelectedChild(1)}
            >
                <Person size={32} color="white" weight={ selectedChild === 1 ? "fill" : "regular"}/>
            </TouchableOpacity>
            <TouchableOpacity id="lcLottery1" style={[{ paddingLeft: 3.5 },{ paddingRight: 3.5 }]}
                onPress={() => setSelectedChild(2)}
            >
                <Sword size={32} color="white" weight={ selectedChild === 2 ? "fill" : "regular"}/>
            </TouchableOpacity>
        </View>
    )
}