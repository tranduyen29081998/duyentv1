package com.fpt.ppmtool.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.ppmtool.domain.Project;
import com.fpt.ppmtool.services.MapValidationErrorService;
import com.fpt.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorService mapValidatetionErrorService;
	@PostMapping
	// sử dụng bindingresult: để tự động xuất ra 1 object lỗi và để customize lỗi
	//Valid kiểm tra nguồn vào có hợp lệ hay không sử dụng javax.validation, ở đây chỉ có kiểm tra ở nguồn vào
	//chưa đụng tới tầng Database
	// @colum mới là validate ở tầng db
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,Principal principal){
		ResponseEntity<?> erroResult = mapValidatetionErrorService.MapValidationService(result);
		if(erroResult!=null) return erroResult;
		Project projectNew = projectService.saveOrUpdateProject(project,principal.getName());
		return new ResponseEntity<Project>(projectNew, HttpStatus.CREATED);
	}
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){

        Project project = projectService.findProjectByIdentifier(projectId, principal.getName());

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    //Xuất ra là 1 mảng các object -- nếu sử dụng restTemplate bên Frontend phải Arrays.asList ra
    public Iterable<Project> getAllProjects(Principal principal){return projectService.findAllProjects(principal.getName());}
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
        projectService.deleteProjectByIdentifier(projectId, principal.getName());

        return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted", HttpStatus.OK);
    }
}