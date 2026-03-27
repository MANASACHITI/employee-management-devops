package com.bnmit.employeemanagement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bnmit.employeemanagement.entity.Employee;
import com.bnmit.employeemanagement.repository.EmployeeRepository;
import com.bnmit.employeemanagement.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testSaveEmployee() {
        Employee emp = new Employee(null, "Manasa", "A",
                "manasa@bnmit.com", "CSE", 50000.0);
        when(employeeRepository.save(emp)).thenReturn(emp);

        Employee saved = employeeService.saveEmployee(emp);

        assertNotNull(saved);
        assertEquals("Manasa", saved.getFirstName());
        verify(employeeRepository, times(1)).save(emp);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> mockList = Arrays.asList(
            new Employee(1L, "Manasa", "A", "manasa@bnmit.com", "CSE", 50000.0),
            new Employee(2L, "Monisha", "KM", "monisha@bnmit.com", "CSE", 48000.0)
        );
        when(employeeRepository.findAll()).thenReturn(mockList);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById() {
        Employee emp = new Employee(1L, "Manasa", "A",
                "manasa@bnmit.com", "CSE", 50000.0);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));

        Optional<Employee> result = employeeService.getEmployeeById(1L);

        assertTrue(result.isPresent());
        assertEquals("Manasa", result.get().getFirstName());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }
}