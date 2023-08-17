package org.example.developer.service;

import org.example.developer.domain.Department;
import org.example.developer.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getDepartmentByUniversity(Long universityId) {
        return departmentRepository.findByUniversityId(universityId);
    }
}
