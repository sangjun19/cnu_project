package org.example.developer.service;

import org.example.developer.domain.Announcement;
import org.example.developer.domain.Content;
import org.example.developer.repository.AnnouncementRepository;
import org.example.developer.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {


    @Autowired
    private ContentRepository contentRepository;

    public Content saveContent(Content content) {
        return contentRepository.save(content);
    }

    public List<Content> getContentByAnnouncement(Long announcementId) {
        return contentRepository.findByAnnouncementId(announcementId);
    }
}
