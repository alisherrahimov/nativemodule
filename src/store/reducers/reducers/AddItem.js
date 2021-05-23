import {add_item, delete_item} from '../../actions/actions';
import {ADD_ITEM, DELETE_ITEM} from '../../type/Type';
const InitialState = [];
const AddItem = (state = InitialState, action) => {
  switch (action.type) {
    case ADD_ITEM:
      return add_item(state, action.name);
    case DELETE_ITEM:
      return delete_item(state, action.index);
    default:
      return state;
  }
};

export default AddItem;
