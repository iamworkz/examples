package com.iamworkz.spring_security_keycloak.service;

import com.iamworkz.spring_security_keycloak.domain.Employee;
import com.iamworkz.spring_security_keycloak.domain.Project;
import com.iamworkz.spring_security_keycloak.domain.TimesheetEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TimesheetServiceTest {

    private TimesheetService timesheetService;

    private static final Long EMPLOYEE_ID = 1L;
    private static final LocalDate DATE_1 = LocalDate.of(2024, 1, 1);
    private static final LocalDate DATE_2 = LocalDate.of(2024, 1, 2);
    private static final TimesheetEntry TIMESHEET_ENTRY_1 = new TimesheetEntry();
    private static final TimesheetEntry TIMESHEET_ENTRY_2 = new TimesheetEntry();
    private static final Employee EMPLOYEE = new Employee();
    private static final Project PROJECT = new Project();

    @BeforeEach
    void setUp() {
        timesheetService = new TimesheetService();

        EMPLOYEE.setId(EMPLOYEE_ID);
        EMPLOYEE.setName("Test Employee");

        PROJECT.setId("p1");
        PROJECT.setName("Test Project");

        TIMESHEET_ENTRY_1.setEmployee(EMPLOYEE);
        TIMESHEET_ENTRY_1.setProject(PROJECT);
        TIMESHEET_ENTRY_1.setDate(DATE_1);
        TIMESHEET_ENTRY_1.setHours(8.0);
        TIMESHEET_ENTRY_1.setDescription("Test Description 1");

        TIMESHEET_ENTRY_2.setEmployee(EMPLOYEE);
        TIMESHEET_ENTRY_2.setProject(PROJECT);
        TIMESHEET_ENTRY_2.setDate(DATE_2);
        TIMESHEET_ENTRY_2.setHours(6.0);
        TIMESHEET_ENTRY_2.setDescription("Test Description 2");

    }

    @Test
    void createTimesheet() {
        TimesheetEntry createdEntry = timesheetService.createTimesheet(EMPLOYEE_ID, TIMESHEET_ENTRY_1);
        assertNotNull(createdEntry);
        assertEquals(TIMESHEET_ENTRY_1.getDate(), createdEntry.getDate());
    }

    @Test
    void updateTimesheet() {
        timesheetService.createTimesheet(EMPLOYEE_ID, TIMESHEET_ENTRY_1);
        TimesheetEntry updatedEntry = timesheetService.updateTimesheet(EMPLOYEE_ID, DATE_1, TIMESHEET_ENTRY_2);
        assertNotNull(updatedEntry);
        assertEquals(TIMESHEET_ENTRY_2.getHours(), updatedEntry.getHours());
        assertEquals(TIMESHEET_ENTRY_2.getDescription(), updatedEntry.getDescription());
    }

    @Test
    void deleteTimesheet() {
        timesheetService.createTimesheet(EMPLOYEE_ID, TIMESHEET_ENTRY_1);
        timesheetService.deleteTimesheet(EMPLOYEE_ID, DATE_1);
        TimesheetEntry updatedEntry = timesheetService.updateTimesheet(EMPLOYEE_ID, DATE_1, TIMESHEET_ENTRY_2);
        assertNull(updatedEntry);
    }
}