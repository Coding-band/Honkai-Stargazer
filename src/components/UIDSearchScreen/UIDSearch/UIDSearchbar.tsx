import { View, Text, ScrollView, Pressable, Keyboard } from 'react-native'
import React, { useEffect, useState } from 'react'
import Searchbar from './UIDSearchbar/UIDSearchbar'
import { Image } from 'expo-image'
import UIDSearchbar from './UIDSearchbar/UIDSearchbar'
import UIDSearchItem from './UIDSearchItem/UIDSearchItem'
import Toast from '../../../utils/toast/Toast'
import getServerFromUUID from '../../../utils/hoyolab/servers/getServerFromUUID'
import useLocalState from '../../../hooks/useLocalState'
import useHsrInGameInfo from '../../../hooks/mihomo/useHsrInGameInfo'
import { useNavigation } from '@react-navigation/native'
import { SCREENS } from '../../../constant/screens'

export default function UIDSearch() {

  const navigation = useNavigation();

  const [input, setInput] = useState("")
  const [uidHistory, setUidHistory] = useLocalState<string[]>("uid-history", [])

  const { refetch, error, data } = useHsrInGameInfo(input, { enabled: false, retry: 0 })

  const handleSubmit = () => {
    if (input.length !== 9 || !getServerFromUUID(input)) {
      Toast("UID 格式錯誤")
    } else {
      refetch()
    }
  }
  useEffect(() => {

    if (error) {
      Toast("查無資料")
    }

    if (data) {
      // @ts-ignore
      navigation.navigate(SCREENS.UserInfoPage.id, { uuid: input })
      setUidHistory([...uidHistory, input])
    }

  }, [error, data])

  return (
    <Pressable onPress={() => {
      Keyboard.dismiss()
    }} style={{ width: "100%" }} className="z-30">
      <ScrollView
        className="h-screen p-4 pb-0 mt-[110px]"
        contentContainerStyle={{ alignItems: "center", gap: 16 }}
        keyboardShouldPersistTaps="always"
      >
        <UIDSearchbar value={input} onChangeText={setInput} onSubmit={handleSubmit} />
        <Text className="text-text text-[12px] font-[HY65]">仅支持查询完整uid，不支持查询昵称或部分uid</Text>
        <View className="w-full" style={{ justifyContent: "space-between", flexDirection: "row" }}>
          <Text className="text-text text-[14px] font-[HY65]">查询记录</Text>
          <Text className="text-text text-[14px] font-[HY65]">切换</Text>
        </View>
        {uidHistory.map(uuid => <UIDSearchItem key={uuid} uuid={uuid} />)}
      </ScrollView>
    </Pressable>
  )
}