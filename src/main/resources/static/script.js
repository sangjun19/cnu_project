document.addEventListener("DOMContentLoaded", () => {
    const universitiesList = document.getElementById("universities");
    const departmentsList = document.getElementById("departments");
    const announcementsList = document.getElementById("announcements");
    const contentsList = document.getElementById("contents");

    // 대학을 가져와서 표시하는 함수
    async function fetchUniversities() {
        const universitiesResponse = await fetch("https://your-api-url/api/cnu/universities"); // 여기에 원하는 API URL을 입력하세요.
        const universitiesData = await universitiesResponse.json();

        universitiesData.forEach(university => {
            const li = document.createElement("li");
            li.textContent = university.name;
            universitiesList.appendChild(li);
        });
    }

    // 학과를 가져와서 표시하는 함수
    async function fetchDepartments(universityId) {
        const departmentsResponse = await fetch(`https://your-api-url/api/cnu/departments/${universityId}`); // 여기에 원하는 API URL을 입력하세요.
        const departmentsData = await departmentsResponse.json();

        departmentsList.innerHTML = ""; // 이전 목록 항목 삭제

        departmentsData.forEach(department => {
            const li = document.createElement("li");
            li.textContent = department.name;
            departmentsList.appendChild(li);
        });
    }

    // 공지사항을 가져와서 표시하는 함수
    async function fetchAnnouncements(departmentId) {
        const announcementsResponse = await fetch(`https://your-api-url/api/cnu/announcements/${departmentId}`); // 여기에 원하는 API URL을 입력하세요.
        const announcementsData = await announcementsResponse.json();

        announcementsList.innerHTML = ""; // 이전 목록 항목 삭제

        announcementsData.forEach(announcement => {
            const li = document.createElement("li");
            li.textContent = announcement.title;
            announcementsList.appendChild(li);
        });
    }

    // 내용을 가져와서 표시하는 함수
    async function fetchContents(announcementId) {
        const contentsResponse = await fetch(`https://your-api-url/api/cnu/contents/${announcementId}`); // 여기에 원하는 API URL을 입력하세요.
        const contentsData = await contentsResponse.json();

        contentsList.innerHTML = ""; // 이전 목록 항목 삭제

        contentsData.forEach(content => {
            const li = document.createElement("li");
            li.textContent = content.content;
            contentsList.appendChild(li);
        });
    }

    // 페이지 로딩 시 fetchUniversities 함수 호출
    fetchUniversities();

    // 대학 클릭 이벤트 리스너
    universitiesList.addEventListener("click", async event => {
        if (event.target.tagName === "LI") {
            const universityName = event.target.textContent;
            const university = universitiesData.find(u => u.name === universityName);
            if (university) {
                fetchDepartments(university.id);
            }
        }
    });

    // 학과 클릭 이벤트 리스너
    departmentsList.addEventListener("click", async event => {
        if (event.target.tagName === "LI") {
            const departmentName = event.target.textContent;
            const department = departmentsData.find(d => d.name === departmentName);
            if (department) {
                fetchAnnouncements(department.id);
            }
        }
    });

    // 공지사항 클릭 이벤트 리스너
    announcementsList.addEventListener("click", async event => {
        if (event.target.tagName === "LI") {
            const announcementTitle = event.target.textContent;
            const announcement = announcementsData.find(a => a.title === announcementTitle);
            if (announcement) {
                fetchContents(announcement.id);
            }
        }
    });
});
