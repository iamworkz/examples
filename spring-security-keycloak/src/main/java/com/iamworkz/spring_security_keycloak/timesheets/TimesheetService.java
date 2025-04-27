package com.iamworkz.spring_security_keycloak.timesheets;

import com.iamworkz.spring_security_keycloak.domain.TimesheetEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TimesheetService {

    private Map<Long, Map<LocalDate, TimesheetEntry>> timesheets = new HashMap<>();

    public TimesheetEntry createTimesheet(Long employeeId, TimesheetEntry timesheetEntry) {        
        timesheets.computeIfAbsent(employeeId, k -> new HashMap<>());
        Map<LocalDate, TimesheetEntry> employeeTimesheet = timesheets.get(employeeId);
        employeeTimesheet.put(timesheetEntry.getDate(), timesheetEntry);
        return timesheetEntry;
    }

    public TimesheetEntry updateTimesheet(Long employeeId, LocalDate date, TimesheetEntry timesheetEntry) {        
        if(timesheets.containsKey(employeeId)){
            Map<LocalDate, TimesheetEntry> employeeTimesheet = timesheets.get(employeeId);
            if(employeeTimesheet.containsKey(date)){
                employeeTimesheet.put(date, timesheetEntry);
                return timesheetEntry;
            }
        }
        return null;
    }

    public void deleteTimesheet(Long employeeId, LocalDate date) {
        if (timesheets.containsKey(employeeId)) {
            Map<LocalDate, TimesheetEntry> employeeTimesheet = timesheets.get(employeeId);
            employeeTimesheet.remove(date);
        }
    }


    public Map<Long, Map<LocalDate, TimesheetEntry>> getTimesheets() {
        return timesheets;
    }
}
