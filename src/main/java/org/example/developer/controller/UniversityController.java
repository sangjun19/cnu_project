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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import java.util.List;

@Controller
@RequestMapping("/cnu")
public class UniversityController {

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

    private void getData() {

        contentRepository.deleteAll();
        announcementRepository.deleteAll();
        departmentRepository.deleteAll();
        universityRepository.deleteAll();

        String url = "https://computer.cnu.ac.kr/computer/index.do";

        University engineering = new University();
        engineering.setName("Engineering university");
        universityService.saveUniversity(engineering);

        Department computer = new Department();
        computer.setName("Computer Science");
        computer.setUniversity(engineering);
        departmentService.saveDepartment(computer);

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

                Announcement announcement = new Announcement();
                announcement.setTitle(title);
                announcement.setDepartment(computer);
                announcementService.saveAnnouncement(announcement);

                Content con = new Content();
                con.setContent(content);
                con.setAnnouncement(announcement);
                contentService.saveContent(con);


                //System.out.println("제목: " + title);
                //System.out.println("링크: " + link);
                //System.out.println("내용 : " + content + "\n");
                if(i == 6) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/universities")
    public String showUniversity(Model model) {
        getData();
        List<University> universities = universityRepository.findAll();
        model.addAttribute("universities", universities);
        return "cnu/universities";
    }

    @GetMapping("/departments/{universityId}")
    public String showDepartments(@PathVariable Long universityId, Model model) {
        List<Department> departments = departmentRepository.findByUniversityId(universityId);
        model.addAttribute("departments", departments);
        return "cnu/departments";
    }

    @GetMapping("/announcements/{departmentId}")
    public String showAnnouncements(@PathVariable Long departmentId, Model model) {
        List<Announcement> announcements = announcementRepository.findByDepartmentId(departmentId);
        model.addAttribute("announcements", announcements);
        return "cnu/announcements";
    }

    @GetMapping("/contents/{announcementId}")
    public String showContent(@PathVariable Long announcementId, Model model) {
        List<Content> contents = contentRepository.findByAnnouncementId(announcementId);
        model.addAttribute("contents", contents);
        return "cnu/contents";
    }
}