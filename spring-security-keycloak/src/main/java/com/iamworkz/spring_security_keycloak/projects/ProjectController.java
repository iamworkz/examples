package com.iamworkz.spring_security_keycloak.controller;

import com.iamworkz.spring_security_keycloak.domain.Project;
import com.iamworkz.spring_security_keycloak.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/projects")
@PreAuthorize("hasRole('admin')")
public class ProjectController {

    private final ProjectService projectService; // Make projectService final

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping // Add requestparam id
    public ResponseEntity<Project> createProject(@RequestBody Project project,@RequestParam String id) {
        Project createdProject = projectService.createProject(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable String id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        if (updatedProject == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {        
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}