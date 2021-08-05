import React from "react";
import { Route, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
//otherProps là các thuộc tính của <Route></Route>:'exact path="/dashboard" ' không có thuộc tính component
// security là props của component con gửi lên :{validToken: false, user: {…}}
// Component là tất cả nội dung bên trong  component con: name: "Dashboard"
//{...otherProps}: destrutering ra các thuộc tính của nó
// (<Component {...props} />): tức là render ra <Dashboard  props của component/>
const SecuredRoute = ({ component: Component, security, ...otherProps }) => (
  <Route
    {...otherProps}
    render={(props) =>
      security.validToken === true ? (
        <Component {...props} />
      ) : (
        <Redirect to="/login" />
      )
    }
  />
);

SecuredRoute.propTypes = {
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps)(SecuredRoute);
