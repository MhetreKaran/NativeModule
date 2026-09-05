import {Button, Text, View} from 'react-native';
import MyDeviceInfo from './specs/NativeMyDeviceInfo';
import { useState } from 'react';

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

      <Text>
        Battery: {battery !== null ? `${battery}%` : '--'}
      </Text>
    </View>
  );
}

export default App;