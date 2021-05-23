import {combineReducers} from 'redux';
import AddItem from './reducers/AddItem';

const rootReducers = combineReducers({AddItem: AddItem});
export default rootReducers;
