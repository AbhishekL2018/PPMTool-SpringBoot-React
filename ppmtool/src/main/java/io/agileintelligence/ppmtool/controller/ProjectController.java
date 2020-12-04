package io.agileintelligence.ppmtool.controller;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.services.MapValidationErrorService;
import io.agileintelligence.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/project") //http://9878/api/project
public class ProjectController {

    @Autowired
    private ProjectService service;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
                                              BindingResult result){
        ResponseEntity<?> error = mapValidationErrorService.mapValidatorError(result);
        if(null != error) return error;
        Project returnValue = service.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(returnValue, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProjectByIdentifier(@PathVariable("id") String identifier){
        Project returnValue = service.getProjectByIdentifier(identifier);
        if(null == returnValue){
            return new ResponseEntity<String>("Identifier doesn't exists",HttpStatus.OK);
        }
        return new ResponseEntity<Project>(returnValue,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects(){
        return new ResponseEntity<List<Project>>(service.getAllProjects(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable("id") String identifier){
        service.deleteProject(identifier);
        return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
    }
}
