
document.addEventListener("DOMContentLoaded", () => {

    // 1. Common helpers

    const getElement = id => document.getElementById(id);

    const toast = getElement("toast");

    function showToast(message) {
        if (!toast) return;

        toast.textContent = message;
        toast.classList.add("show");

        clearTimeout(showToast.timeout);

        showToast.timeout = setTimeout(() => {
            toast.classList.remove("show");
        }, 2500);
    }

    function readStorage(key, fallback) {
        try {
            const savedData = localStorage.getItem(key);
            return savedData ? JSON.parse(savedData) : fallback;
        } catch (error) {
            console.error(`Unable to read ${key}:`, error);
            return fallback;
        }
    }

    function saveStorage(key, value) {
        try {
            localStorage.setItem(key, JSON.stringify(value));
        } catch (error) {
            console.error(`Unable to save ${key}:`, error);
            showToast("Unable to save changes in this browser.");
        }
    }

    function setText(id, value) {
        const element = getElement(id);
        if (element) element.textContent = value;
    }


    // 2. Current date

    const currentDate = getElement("currentDate");

    if (currentDate) {
        currentDate.textContent = new Intl.DateTimeFormat("en-IN", {
            weekday: "short",
            day: "numeric",
            month: "short",
            year: "numeric"
        }).format(new Date());
    }


    // 3. Theme settings

    const themeToggle = getElement("themeToggle");
    const themeIcon = getElement("themeIcon");
    const htmlElement = document.documentElement;

    function applyTheme(theme) {
        htmlElement.setAttribute("data-theme", theme);

        if (themeIcon) {
            themeIcon.textContent = theme === "dark" ? "☀" : "☾";
        }

        themeToggle?.setAttribute(
            "aria-label",
            theme === "dark" ? "Switch to light theme" : "Switch to dark theme"
        );
    }

    const initialTheme = localStorage.getItem("peakprosys-theme") === "dark"
        ? "dark"
        : "light";

    applyTheme(initialTheme);

    themeToggle?.addEventListener("click", () => {
        const currentTheme = htmlElement.getAttribute("data-theme");
        const newTheme = currentTheme === "dark" ? "light" : "dark";

        applyTheme(newTheme);
        localStorage.setItem("peakprosys-theme", newTheme);

        showToast(`${newTheme === "dark" ? "Dark" : "Light"} theme enabled.`);
    });


    // 4. Mobile sidebar

    const sidebar = getElement("sidebar");
    const menuToggle = getElement("menuToggle");
    const sidebarOverlay = getElement("sidebarOverlay");

    function closeSidebar() {
        sidebar?.classList.remove("open");
        sidebarOverlay?.classList.remove("show");
    }

    function openSidebar() {
        sidebar?.classList.add("open");
        sidebarOverlay?.classList.add("show");
    }

    menuToggle?.addEventListener("click", () => {
        sidebar?.classList.contains("open")
            ? closeSidebar()
            : openSidebar();
    });

    sidebarOverlay?.addEventListener("click", closeSidebar);


    // 5. Sidebar navigation

    const navLinks = document.querySelectorAll(".nav-link");
    const pageTitle = getElement("breadcrumbPage");

    navLinks.forEach(link => {
        link.addEventListener("click", () => {
            navLinks.forEach(item => item.classList.remove("active"));
            link.classList.add("active");

            const page = link.dataset.page;

            if (pageTitle) {
                pageTitle.textContent = page;
            }

            closeSidebar();

            if (page !== "Overview" && page !== "Students") {
                showToast(`${page} section is planned for a future module.`);
            }
        });
    });


    // 6. Student data

    const defaultStudents = [
        {
            id: 1,
            name: "Aarav Sharma",
            email: "aarav@example.com",
            course: "Java Full Stack",
            date: "2026-06-10",
            status: "Active"
        },
        {
            id: 2,
            name: "Priya Patil",
            email: "priya@example.com",
            course: "Python Development",
            date: "2026-06-12",
            status: "Active"
        },
        {
            id: 3,
            name: "Rohan Deshmukh",
            email: "rohan@example.com",
            course: "Web Development",
            date: "2026-06-15",
            status: "Pending"
        },
        {
            id: 4,
            name: "Sneha Kulkarni",
            email: "sneha@example.com",
            course: "Java Full Stack",
            date: "2026-06-18",
            status: "Completed"
        },
        {
            id: 5,
            name: "Aditya Joshi",
            email: "aditya@example.com",
            course: "Data Analytics",
            date: "2026-06-20",
            status: "Active"
        }
    ];

    let students = readStorage("peakprosys-students", null);

    if (!Array.isArray(students)) {
        students = defaultStudents;
        saveStorage("peakprosys-students", students);
    }

    let currentSort = "date";
    let sortAscending = false;

    const studentTableBody = getElement("studentTableBody");
    const studentSearch = getElement("studentSearch");
    const statusFilter = getElement("statusFilter");
    const emptyStudents = getElement("emptyStudents");
    const studentCount = getElement("studentCount");


    // 7. Student analytics

    function updateStudentAnalytics() {

        // filter(): count students by status

        const activeCount = students.filter(
            student => student.status === "Active"
        ).length;

        const pendingCount = students.filter(
            student => student.status === "Pending"
        ).length;

        const completedCount = students.filter(
            student => student.status === "Completed"
        ).length;

        setText("totalStudents", students.length);
        setText("activeStudents", activeCount);
        setText("pendingStudents", pendingCount);
        setText("completedStudents", completedCount);
        setText("studentNavCount", students.length);

        // reduce(): calculate course-wise enrollment

        const courseTotals = students.reduce((totals, student) => {
            totals[student.course] = (totals[student.course] || 0) + 1;
            return totals;
        }, {});

        // map(): convert course totals into readable summaries

        const courseSummary = Object.entries(courseTotals).map(
            ([course, count]) => `${course}: ${count} students`
        );

        // some(): check whether at least one student is pending

        const hasPendingStudents = students.some(
            student => student.status === "Pending"
        );

        // every(): check whether all student records are valid

        const allStudentsValid = students.every(student =>
            student.name?.trim() &&
            student.email?.trim() &&
            student.course?.trim() &&
            student.date &&
            ["Active", "Pending", "Completed"].includes(student.status)
        );

        // Store analytics information for dashboard inspection

        const dashboard = document.querySelector(".app-shell");

        if (dashboard) {
            dashboard.dataset.hasPendingStudents = String(hasPendingStudents);
            dashboard.dataset.allStudentsValid = String(allStudentsValid);
        }

        console.log("Course enrollment summary:", courseSummary);
    }


    // 8. Student table rendering

    function renderStudents() {
        if (!studentTableBody) return;

        const searchText = studentSearch?.value.trim().toLowerCase() || "";
        const selectedStatus = statusFilter?.value || "all";

        // filter(): search students by name, email, course and status

        const filteredStudents = students.filter(student => {

            const matchesSearch =
                student.name.toLowerCase().includes(searchText) ||
                student.email.toLowerCase().includes(searchText) ||
                student.course.toLowerCase().includes(searchText);

            const matchesStatus =
                selectedStatus === "all" ||
                student.status === selectedStatus;

            return matchesSearch && matchesStatus;
        });

        // sort(): arrange students by name or enrollment date

        filteredStudents.sort((first, second) => {
            const comparison = currentSort === "name"
                ? first.name.localeCompare(second.name)
                : first.date.localeCompare(second.date);

            return sortAscending ? comparison : -comparison;
        });

        studentTableBody.replaceChildren();

        // map(): create a table row for every filtered student

        const rows = filteredStudents.map(student => {

            const row = document.createElement("tr");

            const initials = student.name
                .split(" ")
                .map(part => part[0])
                .join("")
                .slice(0, 2)
                .toUpperCase();

            const studentCell = document.createElement("td");
            const studentWrapper = document.createElement("div");

            studentWrapper.className = "student-cell";

            const avatar = document.createElement("div");
            avatar.className = "student-avatar";
            avatar.textContent = initials;

            const nameWrapper = document.createElement("div");
            nameWrapper.className = "student-name";

            const name = document.createElement("strong");
            name.textContent = student.name;

            const email = document.createElement("span");
            email.textContent = student.email;

            nameWrapper.append(name, email);
            studentWrapper.append(avatar, nameWrapper);
            studentCell.appendChild(studentWrapper);

            const courseCell = document.createElement("td");
            courseCell.textContent = student.course;

            const dateCell = document.createElement("td");
            dateCell.textContent = new Date(
                `${student.date}T00:00:00`
            ).toLocaleDateString("en-IN", {
                day: "2-digit",
                month: "short",
                year: "numeric"
            });

            const statusCell = document.createElement("td");
            const statusBadge = document.createElement("span");

            statusBadge.className =
                `status-pill status-${student.status.toLowerCase()}`;

            statusBadge.textContent = student.status;
            statusCell.appendChild(statusBadge);

            row.append(
                studentCell,
                courseCell,
                dateCell,
                statusCell
            );

            return row;
        });

        rows.forEach(row => studentTableBody.appendChild(row));

        if (emptyStudents) {
            emptyStudents.hidden = filteredStudents.length !== 0;
        }

        if (studentCount) {
            studentCount.textContent =
                `Showing ${filteredStudents.length} of ${students.length} students`;
        }

        updateStudentAnalytics();
    }


    // 9. Student search and filtering

    studentSearch?.addEventListener("input", renderStudents);
    statusFilter?.addEventListener("change", renderStudents);


    // 10. Student sorting

    document.querySelectorAll(".sort-button").forEach(button => {

        button.addEventListener("click", () => {
            const selectedSort = button.dataset.sort;

            if (!["name", "date"].includes(selectedSort)) return;

            if (currentSort === selectedSort) {
                sortAscending = !sortAscending;
            } else {
                currentSort = selectedSort;
                sortAscending = true;
            }

            renderStudents();
        });
    });


    // 11. Add student modal

    const studentModal = getElement("studentModal");
    const addStudentButton = getElement("addStudentButton");
    const closeModalButton = getElement("closeModal");
    const cancelModalButton = getElement("cancelModal");
    const newStudentForm = getElement("newStudentForm");

    function openStudentModal() {
        if (!studentModal) return;

        studentModal.hidden = false;
        getElement("newStudentName")?.focus();
    }

    function closeStudentModal() {
        if (studentModal) {
            studentModal.hidden = true;
        }

        newStudentForm?.reset();
    }

    addStudentButton?.addEventListener("click", openStudentModal);
    closeModalButton?.addEventListener("click", closeStudentModal);
    cancelModalButton?.addEventListener("click", closeStudentModal);

    studentModal?.addEventListener("click", event => {
        if (event.target === studentModal) {
            closeStudentModal();
        }
    });

    newStudentForm?.addEventListener("submit", event => {
        event.preventDefault();

        const name = getElement("newStudentName")?.value.trim() || "";
        const email = getElement("newStudentEmail")?.value.trim() || "";
        const course = getElement("newStudentCourse")?.value || "";

        if (!name || !email || !course) {
            showToast("Please complete all student details.");
            return;
        }

        // find(): check whether the email already belongs to a student

        const existingStudent = students.find(
            student => student.email.toLowerCase() === email.toLowerCase()
        );

        if (existingStudent) {
            showToast("A student with this email already exists.");
            return;
        }

        const newStudent = {
            id: Date.now(),
            name,
            email,
            course,
            date: new Date().toISOString().slice(0, 10),
            status: "Active"
        };

        students.push(newStudent);

        saveStorage("peakprosys-students", students);

        // Reset filters to make the new student visible

        if (studentSearch) studentSearch.value = "";
        if (statusFilter) statusFilter.value = "all";

        currentSort = "date";
        sortAscending = false;

        renderStudents();
        closeStudentModal();

        showToast("Student added successfully.");
    });


    // 12. View all students

    getElement("viewAllStudents")?.addEventListener("click", () => {
        if (studentSearch) studentSearch.value = "";
        if (statusFilter) statusFilter.value = "all";

        renderStudents();

        getElement("students")?.scrollIntoView({
            behavior: "smooth",
            block: "start"
        });
    });


    // 13. Export students as CSV

    getElement("exportButton")?.addEventListener("click", () => {

        if (students.length === 0) {
            showToast("There are no student records to export.");
            return;
        }

        // every(): verify that all records contain valid details

        const allStudentsValid = students.every(student =>
            student.name?.trim() &&
            student.email?.trim() &&
            student.course?.trim() &&
            student.date &&
            student.status
        );

        if (!allStudentsValid) {
            showToast("Some student records are incomplete.");
            return;
        }

        const headers = [
            "ID",
            "Name",
            "Email",
            "Course",
            "Joined Date",
            "Status"
        ];

        // map(): convert student objects into CSV rows

        const csvRows = students.map(student => [
            student.id,
            student.name,
            student.email,
            student.course,
            student.date,
            student.status
        ]);

        function escapeCSV(value) {
            return `"${String(value ?? "").replace(/"/g, '""')}"`;
        }

        const csvContent = [
            headers,
            ...csvRows
        ]
            .map(row => row.map(escapeCSV).join(","))
            .join("\n");

        const blob = new Blob(
            ["\uFEFF" + csvContent],
            { type: "text/csv;charset=utf-8;" }
        );

        const downloadUrl = URL.createObjectURL(blob);
        const downloadLink = document.createElement("a");

        downloadLink.href = downloadUrl;
        downloadLink.download = "peakprosys-students.csv";

        document.body.appendChild(downloadLink);
        downloadLink.click();
        downloadLink.remove();

        URL.revokeObjectURL(downloadUrl);

        showToast("Student report exported.");
    });


    // 14. Quick tasks

    const defaultTasks = [
        {
            id: 1,
            text: "Review student registrations",
            completed: false
        },
        {
            id: 2,
            text: "Prepare weekly batch schedule",
            completed: true
        }
    ];

    let tasks = readStorage("peakprosys-tasks", null);

    if (!Array.isArray(tasks)) {
        tasks = defaultTasks;
        saveStorage("peakprosys-tasks", tasks);
    }

    let activeTaskFilter = "all";

    const taskForm = getElement("taskForm");
    const taskInput = getElement("taskInput");
    const taskList = getElement("taskList");
    const taskCounter = getElement("taskCounter");


    // 15. Save tasks

    function saveTasks() {
        saveStorage("peakprosys-tasks", tasks);
    }


    // 16. Render tasks

    function renderTasks() {
        if (!taskList) return;

        taskList.replaceChildren();

        // map(): retain each task's original index
        // filter(): display tasks according to the selected filter

        const filteredTasks = tasks
            .map((task, index) => ({ task, index }))
            .filter(({ task }) => {

                if (activeTaskFilter === "pending") {
                    return !task.completed;
                }

                if (activeTaskFilter === "completed") {
                    return task.completed;
                }

                return true;
            });

        filteredTasks.forEach(({ task, index }) => {

            const listItem = document.createElement("li");
            listItem.className = "task-item";

            const label = document.createElement("label");

            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.checked = task.completed;

            const taskText = document.createElement("span");
            taskText.textContent = task.text;

            if (task.completed) {
                taskText.classList.add("completed");
            }

            label.append(checkbox, taskText);

            const status = document.createElement("span");

            status.className = task.completed
                ? "task-status completed"
                : "task-status pending";

            status.textContent = task.completed
                ? "Completed"
                : "Pending";

            const deleteButton = document.createElement("button");
            deleteButton.type = "button";
            deleteButton.textContent = "×";
            deleteButton.setAttribute(
                "aria-label",
                `Delete task: ${task.text}`
            );

            checkbox.addEventListener("change", () => {
                tasks[index].completed = checkbox.checked;

                saveTasks();
                renderTasks();
            });

            deleteButton.addEventListener("click", () => {
                tasks.splice(index, 1);

                saveTasks();
                renderTasks();

                showToast("Task deleted.");
            });

            listItem.append(label, status, deleteButton);
            taskList.appendChild(listItem);
        });

        if (filteredTasks.length === 0) {
            const emptyMessage = document.createElement("li");
            emptyMessage.className = "empty-state";

            if (activeTaskFilter === "pending") {
                emptyMessage.textContent = "No pending tasks.";
            } else if (activeTaskFilter === "completed") {
                emptyMessage.textContent = "No completed tasks yet.";
            } else {
                emptyMessage.textContent = "No tasks yet. Add your first task.";
            }

            taskList.appendChild(emptyMessage);
        }

        // filter(): count tasks that are not completed

        const remainingTasks = tasks.filter(
            task => !task.completed
        ).length;

        if (taskCounter) {
            taskCounter.textContent = `${remainingTasks} left`;
        }

        document.querySelectorAll(".task-filters button").forEach(button => {
            const isActive = button.dataset.filter === activeTaskFilter;

            button.classList.toggle("active", isActive);
            button.setAttribute("aria-pressed", String(isActive));
        });
    }


    // 17. Add task

    taskForm?.addEventListener("submit", event => {
        event.preventDefault();

        const taskText = taskInput?.value.trim() || "";

        if (!taskText) {
            showToast("Please enter a task.");
            return;
        }

        tasks.push({
            id: Date.now(),
            text: taskText,
            completed: false
        });

        saveTasks();

        activeTaskFilter = "all";
        renderTasks();

        taskForm.reset();
        taskInput?.focus();

        showToast("Task added successfully.");
    });


    // 18. Task filters

    document.querySelectorAll(".task-filters button").forEach(button => {

        button.addEventListener("click", () => {
            const filter = button.dataset.filter;

            if (!["all", "pending", "completed"].includes(filter)) return;

            activeTaskFilter = filter;
            renderTasks();
        });
    });


    // 19. Enrollment chart period

    getElement("chartPeriod")?.addEventListener("change", event => {

        if (event.target.value === "year") {
            showToast("Yearly chart view selected. Sample chart data is displayed.");
        } else {
            showToast("Six-month chart view selected.");
        }
    });


    // 20. Keyboard accessibility

    document.addEventListener("keydown", event => {

        if (event.key === "Escape") {
            closeStudentModal();
            closeSidebar();
        }
    });


    // 21. Initial dashboard rendering

    renderStudents();
    renderTasks();

});
