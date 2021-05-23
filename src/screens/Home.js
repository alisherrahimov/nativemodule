import React, {useEffect, useState} from 'react';
import {
  FlatList,
  Image,
  SafeAreaView,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';
import Swipeable from 'react-native-gesture-handler/Swipeable';
import {connect} from 'react-redux';
import {ADD_ITEM, DELETE_ITEM} from '../store/type/Type';

function Home(props) {
  const data = props.state.AddItem;
  const [text, setText] = useState('');
  console.log(props);
  const LeftSwipe = (progress, dragX, index) => {
    return (
      <View
        style={{
          alignItems: 'center',
          justifyContent: 'center',
          flexDirection: 'row',
          borderRadius: 25,
        }}>
        <TouchableOpacity
          onPress={() => {
            props.delete_item(index);
          }}
          style={{
            alignItems: 'center',
            justifyContent: 'center',
            width: 70,
            height: 50,
            backgroundColor: 'red',
            borderRadius: 15,
          }}>
          <Text>DEL</Text>
        </TouchableOpacity>
      </View>
    );
  };

  return (
    <SafeAreaView style={styles.container}>
      <View
        style={{
          flexDirection: 'row',
          alignItems: 'center',
          justifyContent: 'center',
        }}>
        <TextInput
          value={text}
          onChangeText={val => {
            setText(val);
          }}
          style={{
            width: 300,
            height: 50,
            backgroundColor: '#333333',
            color: '#fff',
          }}
        />
        <TouchableOpacity
          disabled={text.length == 0 ? true : false}
          onPress={() => {
            props.add_item(text);
            setText('');
          }}
          style={{
            alignItems: 'center',
            justifyContent: 'center',
            backgroundColor: 'green',
            width: 60,
            height: 50,
            marginLeft: 10,
          }}>
          <Text>ADD</Text>
        </TouchableOpacity>
      </View>
      <View style={{alignItems: 'center'}}>
        {data.length == 0 ? (
          <Text style={{marginTop: 20}}>
            siz hali hech narsa saqlamadingiz.!!!
          </Text>
        ) : (
          <FlatList
            style={{width: 400}}
            data={data}
            keyExtractor={(item, index) => index}
            renderItem={({item, index}) => {
              return (
                <Swipeable
                  key={index}
                  renderRightActions={(progress, dragX) => {
                    return LeftSwipe(progress, dragX, index);
                  }}
                  containerStyle={{backgroundColor: '#fff'}}>
                  <TouchableOpacity
                    style={{backgroundColor: '#fff', borderRadius: 25}}>
                    <View
                      style={{
                        flex: 1,
                        flexDirection: 'row',
                        alignItems: 'center',
                        margin: 5,
                        backgroundColor: '#fff',
                      }}>
                      <View>
                        <Image
                          resizeMode="cover"
                          source={require('../data/photo_2021-05-21_01-27-49.jpg')}
                          style={{width: 60, height: 60, borderRadius: 50}}
                        />
                      </View>
                      <View
                        style={{
                          flex: 1,
                          alignItems: 'stretch',
                          justifyContent: 'space-between',
                          flexDirection: 'row',
                          marginLeft: 15,
                        }}>
                        <Text>{item.name}</Text>
                      </View>
                    </View>
                  </TouchableOpacity>
                </Swipeable>
              );
            }}
          />
        )}
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 50,
    backgroundColor: '#fff',
    alignItems: 'center',
  },
});
const MapStateToProps = state => {
  return {
    state: state,
  };
};
const MapDispatchToProps = dispatch => ({
  add_item: name => {
    dispatch({type: ADD_ITEM, name: name});
  },
  delete_item: index => {
    dispatch({type: DELETE_ITEM, index: index});
  },
});

export default connect(MapStateToProps, MapDispatchToProps)(Home);
