import { useNavigation } from "@react-navigation/native";
import { ClockCounterClockwise } from "phosphor-react-native"
import { TouchableOpacity } from "react-native-gesture-handler"
import Toast from "../../utils/toast/Toast";

export default function LotterRecordBtn(){
    const navigation = useNavigation();
    function startLotteryRecordPage(){
        Toast("I am dumb")
    }

    return(
        <TouchableOpacity 
                id="charLottery1" 
                style={[{ paddingLeft: 3.5 },{ paddingRight: 3.5 }]}
                onPress={() => startLotteryRecordPage()}
            >
            <ClockCounterClockwise size={40} color="white" weight={"regular"}/>
        </TouchableOpacity>
    )
}