package org.example.developer.controller;

import org.example.developer.domain.Announcement;
import org.example.developer.domain.Content;
import org.example.developer.domain.Department;
import org.example.developer.domain.University;
import org.example.developer.repository.AnnouncementRepository;
import org.example.developer.repository.ContentRepository;
import org.example.developer.repository.DepartmentRepository;
import org.example.developer.repository.UniversityRepository;
import org.example.developer.service.AnnouncementService;
import org.example.developer.service.ContentService;
import org.example.developer.service.DepartmentService;
import org.example.developer.service.UniversityService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cnu")
@CrossOrigin(origins = "*")
public class UniversityApiController {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private ContentService contentService;

//    @Autowired
//    ArrayList<String> contentList = new ArrayList<>();

    private void getData() {

        contentRepository.deleteAll();
        announcementRepository.deleteAll();
        departmentRepository.deleteAll();
        universityRepository.deleteAll();

        String url = "https://computer.cnu.ac.kr/computer/index.do";

//        University engineering = new University();
//        engineering.setName("공과대학");
//        universityService.saveUniversity(engineering);
//
//        Department computer = new Department();
//        computer.setName("컴퓨터융합학부");
//        computer.setUniversity(engineering);
//        departmentService.saveDepartment(computer);

        try {
            Document document = Jsoup.connect(url).get();
            Elements articles = document.select("a.articleTitle");
            int i = 0;
            for (Element article : articles) {
                i++;
                String title = article.select("span.mini-title").text();
                String link = article.attr("href");

                String url2 = "https://computer.cnu.ac.kr"+link;
                Document document2 = Jsoup.connect(url2).get();

                Elements contentDiv = document2.select(".fr-view");
                String content = contentDiv.text();

//                Announcement announcement = new Announcement();
//                announcement.setTitle(title);
//                announcement.setDepartment(computer);
//                announcementService.saveAnnouncement(announcement);
//
//                Content con = new Content();
//                con.setContent(content);
//                con.setAnnouncement(announcement);
//                contentService.saveContent(con);


                //contentList.add(content);
                University tit = new University();
                tit.setName(title);
                universityService.saveUniversity(tit);

                University con = new University();
                con.setName(content);
                universityService.saveUniversity(con);


                //System.out.println("제목: " + title);
                //System.out.println("링크: " + link);
                System.out.println("내용 : " + content + "\n");
                if(i == 6) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/universities")
    public List<University> getAllUniversities() {
        getData();
        return universityRepository.findAll();
        //return contentList;
    }

    @GetMapping("/departments/{universityId}")
    public List<Department> getDepartmentsByUniversity(@PathVariable Long universityId) {
        return departmentRepository.findByUniversityId(universityId);
    }

    @GetMapping("/announcements/{departmentId}")
    public List<Announcement> getAnnouncementsByDepartment(@PathVariable Long departmentId) {
        return announcementRepository.findByDepartmentId(departmentId);
    }

    @GetMapping("/contents/{announcementId}")
    public List<Content> getContentsByAnnouncement(@PathVariable Long announcementId) {
        return contentRepository.findByAnnouncementId(announcementId);
    }

    @PostMapping("/departments")
    public List<Department> getDepartmentsByUniversityId(@RequestBody Long universityId) {
        return departmentRepository.findByUniversityId(universityId);
    }

    @PostMapping("/announcements")
    public List<Announcement> getAnnouncementsByDepartmentId(@RequestBody Long departmentId) {
        return announcementRepository.findByDepartmentId(departmentId);
    }

    @PostMapping("/contents")
    public List<Content> getContentsByAnnouncementId(@RequestBody Long announcementId) {
        return contentRepository.findByAnnouncementId(announcementId);
    }
}
