package org.example.developer.service;

import org.example.developer.domain.Announcement;
import org.example.developer.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement saveAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public List<Announcement> getAnnouncementsByDepartment(Long departmentId) {
        return announcementRepository.findByDepartmentId(departmentId);
    }
}
