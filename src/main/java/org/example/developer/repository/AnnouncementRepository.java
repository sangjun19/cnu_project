package org.example.developer.repository;

import org.example.developer.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByDepartmentId(Long departmentId);
}