//ấn rcc
import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";
import CreateProjectButton from "./Project/CreateProjectButton";
import { connect } from "react-redux";
import { getProjects } from "../actions/projectActions";
import PropTypes from "prop-types";

class Dashboard extends Component {
  //Bước 1 : thực hiện lấy state trong store qua function props có tên là getProjects(),
  // Đến projectAction thực hiện gọi API , dispatch cùng type và data, cập nhật lại trạng thái state
  componentDidMount() {
    this.props.getProjects();
  }

  render() {
    // bước 3
    // Lấy ra key project trong props project ở bước 2
    // {projects} = {projects:[....],project}
    const { projects } = this.props.project;
    console.log(this.props);
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />
              <CreateProjectButton />

              <br />
              <hr />
              {projects.map((project) => (
                //Truyền props tới component ProjectItem
                <ProjectItem key={project.id} project={project} />
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  project: PropTypes.object.isRequired,
  getProjects: PropTypes.func.isRequired,
};
// Bước 2: gán key có tên là project trong state vào props của component có tên là project
// lúc này props project = {projects:[....],project}
const mapStateToProps = (state) => ({
  project: state.project,
});

export default connect(mapStateToProps, { getProjects })(Dashboard);
