import { combineReducers } from "redux";
import erroReducer from "./erroReducer";
import projectReducer from "./projectReducer";
import backlogReducer from "./backlogReducer";
import securityReducer from "./securityReducer";
export default combineReducers({
  errors: erroReducer,
  project: projectReducer,
  backlog: backlogReducer,
  security: securityReducer,
});
