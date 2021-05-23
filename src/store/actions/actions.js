export const add_item = (state, name) => {
  const id = Math.round(Math.random() * Math.pow(10, 10));
  state.push({id: id, name: name});
  return [...state];
};
export const delete_item = (state, idx) => {
  state.splice(idx, 1);
  return [...state];
};
