import {Button, View} from 'react-native';
import MyDeviceInfo from './specs/NativeMyDeviceInfo';

function App() {
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
    </View>
  );
}

export default App;