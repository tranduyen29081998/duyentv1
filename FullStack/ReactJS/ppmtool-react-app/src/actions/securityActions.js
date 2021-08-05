import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";
export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
export const login = (LoginRequest) => async (dispatch) => {
  try {
    //post  => Login request
    const res = await axios.post("/api/users/login", LoginRequest);
    // extract token from res.data
    const { token } = res.data;
    //store the token into localStorage
    localStorage.setItem("jwtToken", token);
    // set our token into header client request
    setJWTToken(token);
    // decode token on react
    // giải nén token để lấy thông tin người dùng, như id,username,fullname, thời hạn sử dụng
    const decoded = jwt_decode(token);
    //dispatch to our securityReducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem("jwtToken");

  // xét header thành false axios.defaults.headers.common["Authorization"] = false
  setJWTToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {},
  });
};
