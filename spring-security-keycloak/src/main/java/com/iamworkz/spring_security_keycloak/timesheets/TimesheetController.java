package com.iamworkz.spring_security_keycloak.timesheets;

import com.iamworkz.spring_security_keycloak.domain.TimesheetEntry;
import org.springframework.http.HttpStatus;
import com.iamworkz.spring_security_keycloak.service.TimesheetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/timesheets")
@PreAuthorize("hasRole('employee')")
public class TimesheetController {

    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @PostMapping
    public ResponseEntity<TimesheetEntry> createTimesheet(@RequestBody TimesheetEntry timesheetEntry, @RequestParam Long employeeId) {
        TimesheetEntry createdTimesheet = timesheetService.createTimesheet(employeeId, timesheetEntry);        
        return new ResponseEntity<>(createdTimesheet, HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<TimesheetEntry> updateTimesheet(@PathVariable Long employeeId, @RequestBody TimesheetEntry timesheetEntry, LocalDate date) {
        TimesheetEntry updatedTimesheet = timesheetService.updateTimesheet(employeeId, date, timesheetEntry);
        return new ResponseEntity<>(updatedTimesheet, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}/{date}")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable Long employeeId, @PathVariable LocalDate date) {
        timesheetService.deleteTimesheet(employeeId, date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}