package com.fpt.ppmtool.exceptions;

public class ProjecNotFoundExceptionResponse {
	private String ProjectNotFound;

    public ProjecNotFoundExceptionResponse(String ProjectNotFound) {
        this.ProjectNotFound = ProjectNotFound;
    }

	public String getProjectNotFound() {
		return ProjectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		ProjectNotFound = projectNotFound;
	}


}
