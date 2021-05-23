import {createStore} from 'redux';
import rootReducers from './reducers/rootReducer';
const Store = createStore(rootReducers);

export default Store;
