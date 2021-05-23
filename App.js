import React from 'react';
import {StatusBar} from 'react-native';
import Navigation from './src/navigation/Navigation';
StatusBar.setBackgroundColor('rgba(0,0,0,0)');
StatusBar.setTranslucent(true);
StatusBar.setBarStyle('dark-content');
import {Provider} from 'react-redux';
import Store from './src/store/Store';

const App = () => {
  return (
    <Provider store={Store}>
      <Navigation />
    </Provider>
  );
};

export default App;
