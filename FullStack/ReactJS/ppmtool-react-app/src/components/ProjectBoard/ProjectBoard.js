import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
//Bước 1 import hết cái này, sau khi tạo types ->action ->reducer
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getBacklog } from "../../actions/backlogActions";
class ProjectBoard extends Component {
  //constructor
  constructor() {
    super();
    this.state = {
      errors: {},
    };
  }
  //Bước 5: gọi api getlist task
  // sau khi componetDidMount này được gọi
  // state của store thay đổi
  // nên ta sẽ có props mới của component này
  // nên component này sẽ render() lại 1 lần nữa
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }
  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;
    const { errors } = this.state;

    let BoardContent;
    // in ra lỗi khi không có task trong backlog hoặc đường link user nhập vào sai
    const boardAlgorithm = (errors, project_tasks) => {
      if (project_tasks.length < 1) {
        if (errors.projectIdentifier) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectIdentifier}
            </div>
          );
        } else if (errors.projectNotFound) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectNotFound}
            </div>
          );
        } else {
          return (
            <div className="alert alert-info text-center" role="alert">
              No Project Tasks on this board
            </div>
          );
        }
      } else {
        return <Backlog project_tasks_prop={project_tasks} />;
      }
    };

    BoardContent = boardAlgorithm(errors, project_tasks);

    return (
      <div className="container">
        {!errors.projectNotFound && (
          <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
            <i className="fas fa-plus-circle"> Create Project Task</i>
          </Link>
        )}

        {errors.projectNotFound && (
          <Link to="/dashboard" className="btn btn-light">
            Back to Project Board
          </Link>
        )}
        <br />
        <hr />
        {BoardContent}
      </div>
    );
  }
}

//Bước 4
ProjectBoard.protoTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired,
  erros: PropTypes.object.isRequired,
};
/// Bước 3 tạo mapStateToProps
const mapStateToProps = (state) => ({
  backlog: state.backlog,
  errors: state.errors,
});

//bước 2 connect và chèn getBacklog vào
export default connect(mapStateToProps, { getBacklog })(ProjectBoard);
