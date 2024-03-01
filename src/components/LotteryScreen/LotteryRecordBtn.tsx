import { useNavigation } from "@react-navigation/native";
import { ClockCounterClockwise } from "phosphor-react-native"
import { TouchableOpacity } from "react-native-gesture-handler"
import Toast from "../../utils/toast/Toast";

type Props = {
    onPress?: () => void;
  };

export default function LotterRecordBtn(props : Props){
    const navigation = useNavigation();

    return(
        <TouchableOpacity 
                id="charLottery1" 
                style={[{ paddingLeft: 3.5 },{ paddingRight: 3.5 }]}
                onPress={() => {props.onPress && props.onPress()}}
            >
            <ClockCounterClockwise size={40} color="white" weight={"regular"}/>
        </TouchableOpacity>
    )
}