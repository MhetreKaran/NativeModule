import {Button, NativeEventEmitter, NativeModule, Text, View} from 'react-native';
import MyDeviceInfo from './specs/NativeMyDeviceInfo';
import { useEffect, useState } from 'react';

function App() {
  const [battery, setBattery] = useState<number | null>(null);

  const getBattery = () => {
    const level = MyDeviceInfo.getBatteryLevel();

    setBattery(level);
  };
  const getDevice = () => {
    const model = MyDeviceInfo.getDeviceModel();

    console.log('Device Model:', model);
  };
  const batteryLevel = MyDeviceInfo.getBatteryLevel();

  useEffect(() => {

    const eventEmitter =
      new NativeEventEmitter(MyDeviceInfo as NativeModule);

    const subscription =
      eventEmitter.addListener(
        'batteryChanged',
        (level: any) => {

          console.log(
            'Battery changed:',
            level,
          );

          setBattery(level);
        },
      );

    return () => {
      subscription.remove();
    };

  }, []);

  return (
    <View
      style={{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      }}>
      <Button
        title="Get Device"
        onPress={getDevice}
      />
       <Button
        title="Get Battery"
        onPress={getBattery}
      />

      <Text style={{ marginTop: 20, fontSize: 20, fontWeight: 'bold' ,color: 'white'}}>
        Battery: {batteryLevel !== null ? `${batteryLevel}%` : '--'}
      </Text>
    </View>
  );
}

export default App;