
document.addEventListener("DOMContentLoaded", function () {

    // Common elements
    const getElement = (id) => document.getElementById(id);
    let toastTimer;

    // Show notification
    function showToast(message) {
        const toast = getElement("toast");
        if (!toast) return;

        toast.textContent = message;
        toast.classList.add("show");

        clearTimeout(toastTimer);
        toastTimer = setTimeout(() => {
            toast.classList.remove("show");
        }, 2500);
    }

    // Display current date
    const currentDate = getElement("currentDate");

    if (currentDate) {
        currentDate.textContent = new Date().toLocaleDateString("en-IN", {
            weekday: "short",
            day: "numeric",
            month: "short",
            year: "numeric"
        });
    }

    // Switch between light and dark themes
    const themeToggle = getElement("themeToggle");
    const themeIcon = getElement("themeIcon");

    const savedTheme = localStorage.getItem("peakprosys-theme") || "light";

    document.documentElement.setAttribute("data-theme", savedTheme);

    function updateThemeIcon(theme) {
        if (!themeIcon || !themeToggle) return;

        themeIcon.textContent = theme === "dark" ? "☀" : "☾";
        themeToggle.setAttribute(
            "aria-label",
            theme === "dark" ? "Switch to light theme" : "Switch to dark theme"
        );
    }

    updateThemeIcon(savedTheme);

    if (themeToggle) {
        themeToggle.addEventListener("click", function () {
            const currentTheme = document.documentElement.getAttribute("data-theme");
            const newTheme = currentTheme === "dark" ? "light" : "dark";

            document.documentElement.setAttribute("data-theme", newTheme);
            localStorage.setItem("peakprosys-theme", newTheme);

            updateThemeIcon(newTheme);
        });
    }

    // Mobile sidebar navigation
    const sidebar = getElement("sidebar");
    const menuToggle = getElement("menuToggle");
    const sidebarOverlay = getElement("sidebarOverlay");

    function closeSidebar() {
        if (sidebar) sidebar.classList.remove("open");
        if (sidebarOverlay) sidebarOverlay.classList.remove("show");
    }

    if (menuToggle) {
        menuToggle.addEventListener("click", function () {
            if (sidebar) sidebar.classList.toggle("open");
            if (sidebarOverlay) sidebarOverlay.classList.toggle("show");
        });
    }

    if (sidebarOverlay) {
        sidebarOverlay.addEventListener("click", closeSidebar);
    }

    // Update the selected navigation item
    const navLinks = document.querySelectorAll(".nav-link");
    const breadcrumbPage = getElement("breadcrumbPage");
    const pageTitle = getElement("pageTitle");

    navLinks.forEach(link => {
        link.addEventListener("click", function () {
            navLinks.forEach(item => item.classList.remove("active"));
            this.classList.add("active");

            const page = this.dataset.page;

            if (breadcrumbPage) breadcrumbPage.textContent = page;
            if (pageTitle) pageTitle.textContent = page;

            closeSidebar();
        });
    });

    // Load saved students or use sample records
    const defaultStudents = [
        {
            name: "Aarav Sharma",
            email: "aarav@example.com",
            course: "Java Full Stack",
            date: "2026-11-20",
            status: "Active"
        },
        {
            name: "Priya Patil",
            email: "priya@example.com",
            course: "Python Development",
            date: "2026-11-19",
            status: "Pending"
        },
        {
            name: "Rohan Deshmukh",
            email: "rohan@example.com",
            course: "Web Development",
            date: "2026-11-17",
            status: "Active"
        },
        {
            name: "Sneha Kulkarni",
            email: "sneha@example.com",
            course: "Java Full Stack",
            date: "2026-11-15",
            status: "Completed"
        },
        {
            name: "Aditya Joshi",
            email: "aditya@example.com",
            course: "Python Development",
            date: "2026-11-12",
            status: "Active"
        }
    ];

    let students;

    try {
        const savedStudents = localStorage.getItem("peakprosys-students");
        students = savedStudents ? JSON.parse(savedStudents) : defaultStudents;
        if (!Array.isArray(students)) students = defaultStudents;
    } catch {
        students = defaultStudents;
    }

    // Save student records
    function saveStudents() {
        localStorage.setItem("peakprosys-students", JSON.stringify(students));
    }

    // Student table elements
    const studentTableBody = getElement("studentTableBody");
    const studentSearch = getElement("studentSearch");
    const statusFilter = getElement("statusFilter");
    const studentCount = getElement("studentCount");
    const emptyStudents = getElement("emptyStudents");

    let sortField = "date";
    let sortAscending = false;

    // Display students in the table
    function renderStudents() {
        if (!studentTableBody) return;

        const searchText = studentSearch
            ? studentSearch.value.trim().toLowerCase()
            : "";

        const selectedStatus = statusFilter
            ? statusFilter.value.toLowerCase()
            : "all";

        const filteredStudents = students.filter(student => {
            const matchesSearch =
                student.name.toLowerCase().includes(searchText) ||
                student.email.toLowerCase().includes(searchText) ||
                student.course.toLowerCase().includes(searchText);

            const matchesStatus =
                selectedStatus === "all" ||
                student.status.toLowerCase() === selectedStatus;

            return matchesSearch && matchesStatus;
        });

        filteredStudents.sort((a, b) => {
            const first = String(a[sortField]).toLowerCase();
            const second = String(b[sortField]).toLowerCase();

            return sortAscending
                ? first.localeCompare(second)
                : second.localeCompare(first);
        });

        studentTableBody.replaceChildren();

        filteredStudents.forEach(student => {
            const row = document.createElement("tr");

            const initials = student.name
                .split(" ")
                .map(part => part.charAt(0))
                .slice(0, 2)
                .join("")
                .toUpperCase();

            const studentCell = document.createElement("td");
            const studentInfo = document.createElement("div");
            const avatar = document.createElement("div");
            const nameContainer = document.createElement("div");
            const name = document.createElement("strong");
            const email = document.createElement("span");

            studentInfo.className = "student-cell";
            avatar.className = "student-avatar";
            nameContainer.className = "student-name";

            avatar.textContent = initials;
            name.textContent = student.name;
            email.textContent = student.email;

            nameContainer.append(name, email);
            studentInfo.append(avatar, nameContainer);
            studentCell.appendChild(studentInfo);

            const courseCell = document.createElement("td");
            courseCell.textContent = student.course;

            const dateCell = document.createElement("td");
            dateCell.textContent = new Date(student.date + "T00:00:00")
                .toLocaleDateString("en-IN", {
                    day: "2-digit",
                    month: "short",
                    year: "numeric"
                });

            const statusCell = document.createElement("td");
            const status = document.createElement("span");

            status.className = "status-pill status-" + student.status.toLowerCase();
            status.textContent = student.status;

            statusCell.appendChild(status);
            row.append(studentCell, courseCell, dateCell, statusCell);
            studentTableBody.appendChild(row);
        });

        if (studentCount) {
            studentCount.textContent =
                `Showing ${filteredStudents.length} of ${students.length} students`;
        }

        if (emptyStudents) {
            emptyStudents.hidden = filteredStudents.length !== 0;
        }
    }

    if (studentSearch) {
        studentSearch.addEventListener("input", renderStudents);
    }

    if (statusFilter) {
        statusFilter.addEventListener("change", renderStudents);
    }

    // Sort students by name or joining date
    document.querySelectorAll("[data-sort]").forEach(button => {
        button.addEventListener("click", function () {
            const field = this.dataset.sort;

            if (sortField === field) {
                sortAscending = !sortAscending;
            } else {
                sortField = field;
                sortAscending = true;
            }

            renderStudents();
        });
    });

    // Open and close the add student modal
    const studentModal = getElement("studentModal");
    const addStudentButton = getElement("addStudentButton");
    const closeModal = getElement("closeModal");
    const cancelModal = getElement("cancelModal");
    const newStudentForm = getElement("newStudentForm");

    function openStudentModal() {
        if (!studentModal) return;

        studentModal.hidden = false;
        getElement("newStudentName")?.focus();
    }

    function closeStudentModal() {
        if (!studentModal) return;

        studentModal.hidden = true;
        if (newStudentForm) newStudentForm.reset();
    }

    if (addStudentButton) {
        addStudentButton.addEventListener("click", openStudentModal);
    }

    if (closeModal) {
        closeModal.addEventListener("click", closeStudentModal);
    }

    if (cancelModal) {
        cancelModal.addEventListener("click", closeStudentModal);
    }

    if (studentModal) {
        studentModal.addEventListener("click", function (event) {
            if (event.target === studentModal) {
                closeStudentModal();
            }
        });
    }

    // Add a new student to the table
    if (newStudentForm) {
        newStudentForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const name = getElement("newStudentName").value.trim();
            const email = getElement("newStudentEmail").value.trim();
            const course = getElement("newStudentCourse").value;

            if (!name || !email || !course) {
                showToast("Please complete all student details");
                return;
            }

            students.unshift({
                name,
                email,
                course,
                date: new Date().toISOString().slice(0, 10),
                status: "Pending"
            });

            saveStudents();
            renderStudents();
            closeStudentModal();
            showToast("Student added successfully");
        });
    }

    // Load saved tasks or start with an empty list
    let tasks;

    try {
        const savedTasks = localStorage.getItem("peakprosys-tasks");
        tasks = savedTasks ? JSON.parse(savedTasks) : [];
        if (!Array.isArray(tasks)) tasks = [];
    } catch {
        tasks = [];
    }

    const taskForm = getElement("taskForm");
    const taskInput = getElement("taskInput");
    const taskList = getElement("taskList");
    const taskCounter = getElement("taskCounter");

    // Save tasks in the browser
    function saveTasks() {
        localStorage.setItem("peakprosys-tasks", JSON.stringify(tasks));
    }

    // Display the task list

// Display tasks with their current status
    function renderTasks() {
        if (!taskList) return;

        taskList.replaceChildren();

        tasks.forEach((task, index) => {
            const item = document.createElement("li");
            item.className = "task-item";

            const label = document.createElement("label");
            const checkbox = document.createElement("input");
            const text = document.createElement("span");
            const status = document.createElement("span");
            const deleteButton = document.createElement("button");

            checkbox.type = "checkbox";
            checkbox.checked = task.completed;
            checkbox.setAttribute("aria-label", "Mark task complete");

            text.textContent = task.text;

            status.textContent = task.completed ? "Completed" : "Pending";
            status.className = task.completed
                ? "task-status completed"
                : "task-status pending";

            deleteButton.type = "button";
            deleteButton.textContent = "×";
            deleteButton.setAttribute("aria-label", "Delete task");

            checkbox.addEventListener("change", function () {
                task.completed = checkbox.checked;
                saveTasks();
                renderTasks();
            });

            deleteButton.addEventListener("click", function () {
                tasks.splice(index, 1);
                saveTasks();
                renderTasks();
            });

            label.append(checkbox, text);
            item.append(label, status, deleteButton);
            taskList.appendChild(item);
        });

        if (taskCounter) {
            const remaining = tasks.filter(task => !task.completed).length;
            taskCounter.textContent = `${remaining} tasks remaining`;
        }
    }


    // Add a new task
    if (taskForm) {
        taskForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const text = taskInput.value.trim();
            if (!text) return;

            tasks.push({
                text,
                completed: false
            });

            saveTasks();
            renderTasks();
            taskForm.reset();
        });
    }

    // Export student records as a CSV file
    const exportButton = getElement("exportButton");

    if (exportButton) {
        exportButton.addEventListener("click", function () {
            if (students.length === 0) {
                showToast("No student records to export");
                return;
            }

            const headers = ["Name", "Email", "Course", "Joining Date", "Status"];

            const rows = students.map(student => [
                student.name,
                student.email,
                student.course,
                student.date,
                student.status
            ]);

            const csvContent = [headers, ...rows]
                .map(row => row.map(value =>
                    `"${String(value).replace(/"/g, '""')}"`
                ).join(","))
                .join("\n");

            const blob = new Blob(["\uFEFF" + csvContent], {
                type: "text/csv;charset=utf-8;"
            });

            const url = URL.createObjectURL(blob);
            const link = document.createElement("a");

            link.href = url;
            link.download = "peakprosys-students.csv";
            link.click();

            URL.revokeObjectURL(url);
            showToast("Student report exported");
        });
    }

    // Update the chart period selection
    const chartPeriod = getElement("chartPeriod");

    if (chartPeriod) {
        chartPeriod.addEventListener("change", function () {
            showToast("Selected period: " + this.value);
        });
    }

    // Close the modal with the Escape key
    document.addEventListener("keydown", function (event) {
        if (event.key === "Escape") {
            closeStudentModal();
            closeSidebar();
        }
    });

    // Show the initial dashboard data
    renderStudents();
    renderTasks();

});
