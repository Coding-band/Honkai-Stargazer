import { View, findNodeHandle } from "react-native";
import React, { useState } from "react";
import { TouchableOpacity } from "react-native-gesture-handler";
import { Person, Sword } from "phosphor-react-native";

type Props = {
    selectedChild: string;
    setSelectedChild: (lotteryId: string) => void;
};
export default function LotteryHeaderChild({selectedChild,setSelectedChild} : Props){

    return(
        <View style={[{ flexDirection: 'row' }]}>
            <TouchableOpacity 
                id="charLottery1" 
                style={[{ paddingLeft: 3.5 },{ paddingRight: 3.5 }]}
                onPress={() => setSelectedChild("charLottery1")}
            >
                <Person size={32} color="white" weight={ selectedChild ==="charLottery1" ? "fill" : "regular"}/>
            </TouchableOpacity>
            <TouchableOpacity id="charLottery2" style={[{ paddingLeft: 3.5 },{ paddingRight: 3.5 }]}
                onPress={() => setSelectedChild("charLottery2")}
            >
                <Person size={32} color="white" weight={ selectedChild ==="charLottery2" ? "fill" : "regular"}/>
            </TouchableOpacity>
            <TouchableOpacity id="lcLottery1" style={[{ paddingLeft: 3.5 },{ paddingRight: 3.5 }]}
                onPress={() => setSelectedChild("lcLottery1")}
            >
                <Sword size={32} color="white" weight={ selectedChild ==="lcLottery1" ? "fill" : "regular"}/>
            </TouchableOpacity>
        </View>
    )
}