package org.example.developer.service;

import org.example.developer.domain.University;
import org.example.developer.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepository universityRepository;

    public University saveUniversity(University department) {
        return universityRepository.save(department);
    }

    public List<University> getAllDepartments() {
        return universityRepository.findAll();
    }
}
