package org.example.developer.repository;

import org.example.developer.domain.Announcement;
import org.example.developer.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByAnnouncementId(Long announcementId);
}