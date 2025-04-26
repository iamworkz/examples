package com.iamworkz.spring_security_keycloak.service;

import com.iamworkz.spring_security_keycloak.domain.Project;
import com.iamworkz.spring_security_keycloak.domain.TimesheetEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Optional;

@Service
public final class ProjectService {

    private final TimesheetService timesheetService;//
    

    private final Map<String, Project> projects = new HashMap<>();

    public ProjectService(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }
    
    public Project createProject(Project project) {
        projects.put(project.getId(), project);
        return project;
    }

    public Project updateProject(String id, Project project) {
        if(projects.containsKey(id)) {
            projects.put(id, project);
            return project;
        }
        return null;
    }
    
    public void deleteProject(String id) {
        if (!projects.containsKey(id)) {
            return;
        }
        Map<Long, Map<LocalDate, TimesheetEntry>> timesheets = timesheetService.getTimesheets();
        boolean projectInUse = timesheets.values().stream()
                .flatMap(map -> map.values().stream())
                .anyMatch(entry -> entry.getProject().getId().equals(id));

        if (projectInUse) {
            throw new IllegalArgumentException("Cannot delete project because it has timesheet entries.");
        } else {
            projects.remove(id);
        }
    }
}