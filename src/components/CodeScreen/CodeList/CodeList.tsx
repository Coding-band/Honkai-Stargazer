import { View, ScrollView } from 'react-native';
import React, { useState } from 'react';
import { RefreshControl } from 'react-native';
import CodeItem from './CodeItem/CodeItem';
import useAppLanguage from '../../../language/AppLanguage/useAppLanguage';
import { LOCALES } from '../../../../locales';
import { useQuery } from 'react-query';
import db from '../../../firebase/db';

export default function CodeList() {
  const { language } = useAppLanguage();

  //Also can select RedeemCodeUntil, RedeemCodeExpired

  const { data: codes } = useQuery(
    'codes-list',
    async () =>
      (await db.Codes.doc('codes_list').get()).data()?.codes_list || []
  );

  // const [codes, setCodes] = useState([
  //   {
  //     time: '12/24 - 3/4',
  //     code: 'X24532322F343',
  //     server: 'INT',
  //   },
  //   {
  //     time: '12/24 - 3/4',
  //     code: 'STARCS123213T',
  //     server: 'CN',
  //   },
  //   {
  //     time: 'FOREVER',
  //     code: 'STARRAILGIFT',
  //     server: 'INT',
  //   },
  // ]);

  const onRefresh = React.useCallback(() => {
    //
  }, []);

  return (
    <View style={{ width: '100%' }} className="z-30">
      <ScrollView
        className="h-screen p-4 pb-0 mt-[110px]"
        refreshControl={
          <RefreshControl refreshing={false} onRefresh={onRefresh} />
        }
        scrollEnabled={false}
      >
        <View style={{ gap: 16, alignItems: 'center' }} className="mb-16">
          {codes?.map((code) => (
            <CodeItem key={code.code} {...code} />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
