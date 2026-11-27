
/* =========================================================
   PEAKPROSYS - MAIN JAVASCRIPT
   Course Selection | Pricing | Authentication
   Student Portal | Registration | Animations
========================================================= */

document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       1. COURSE DATA
    ===================================================== */

    const courses = {
        java: {
            name: "Java Full Stack Development",
            duration: "6 Months",
            description: "Learn Java, Spring Boot, databases and frontend development.",
            plans: {
                basic: { name: "Basic", price: 4999, duration: "6 Weeks" },
                professional: { name: "Professional", price: 14999, duration: "12 Weeks" },
                advanced: { name: "Advanced", price: 24999, duration: "20 Weeks" }
            }
        },

        python: {
            name: "Python Programming",
            duration: "4 Months",
            description: "Learn Python programming, backend development and databases.",
            plans: {
                basic: { name: "Basic", price: 4499, duration: "5 Weeks" },
                professional: { name: "Professional", price: 11999, duration: "10 Weeks" },
                advanced: { name: "Advanced", price: 19999, duration: "18 Weeks" }
            }
        },

        web: {
            name: "Frontend Web Development",
            duration: "3 Months",
            description: "Build responsive websites using HTML, CSS and JavaScript.",
            plans: {
                basic: { name: "Basic", price: 3999, duration: "4 Weeks" },
                professional: { name: "Professional", price: 9999, duration: "10 Weeks" },
                advanced: { name: "Advanced", price: 17999, duration: "16 Weeks" }
            }
        },

        react: {
            name: "MERN Stack Development",
            duration: "5 Months",
            description: "Create full-stack applications using MongoDB, Express, React and Node.js.",
            plans: {
                basic: { name: "Basic", price: 3999, duration: "4 Weeks" },
                professional: { name: "Professional", price: 8999, duration: "8 Weeks" },
                advanced: { name: "Advanced", price: 14999, duration: "12 Weeks" }
            }
        },

        database: {
            name: "SQL and Database Management",
            duration: "2 Months",
            description: "Understand SQL, database design and practical queries.",
            plans: {
                basic: { name: "Basic", price: 2999, duration: "3 Weeks" },
                professional: { name: "Professional", price: 6999, duration: "6 Weeks" },
                advanced: { name: "Advanced", price: 9999, duration: "8 Weeks" }
            }
        },

        ai: {
            name: "AI and Machine Learning",
            duration: "5 Months",
            description: "Explore Python, machine learning fundamentals and AI projects.",
            plans: {
                basic: { name: "Basic", price: 4999, duration: "5 Weeks" },
                professional: { name: "Professional", price: 13999, duration: "12 Weeks" },
                advanced: { name: "Advanced", price: 22999, duration: "20 Weeks" }
            }
        }
    };


    /* =====================================================
       2. STORAGE KEYS
    ===================================================== */

    const DEMO_USER_KEY = "peakprosys_demo_user";
    const DEMO_SESSION_KEY = "peakprosys_demo_session";
    const ENROLLMENT_KEY = "peakprosys_pending_enrollment";
    const REGISTRATION_KEY = "peakprosys_registrations";


    /* =====================================================
       3. ELEMENT REFERENCES
    ===================================================== */

    const header = document.getElementById("header");
    const footer = document.getElementById("footer");
    const publicWebsite = document.getElementById("publicWebsite");
    const studentPortal = document.getElementById("studentPortal");

    const menuToggle = document.getElementById("menuToggle");
    const navLinks = document.getElementById("navLinks");

    const authModal = document.getElementById("authModal");
    const closeAuth = document.getElementById("closeAuth");

    const loginTab = document.getElementById("loginTab");
    const signupTab = document.getElementById("signupTab");

    const loginForm = document.getElementById("loginForm");
    const signupForm = document.getElementById("signupForm");

    const authTitle = document.getElementById("authTitle");
    const authSubtitle = document.getElementById("authSubtitle");
    const authFeedback = document.getElementById("authFeedback");

    const forgotPassword = document.getElementById("forgotPassword");

    const pricingCourse = document.getElementById("pricingCourse");
    const registrationCourse = document.getElementById("registrationCourse");
    const registrationPlan = document.getElementById("registrationPlan");

    const registrationForm = document.getElementById("registrationForm");
    const registrationFeedback = document.getElementById("registrationFeedback");

    const selectionMessage = document.getElementById("selectionMessage");
    const toast = document.getElementById("toast");


    /* =====================================================
       4. APPLICATION STATE
    ===================================================== */

    let selectedCourse = null;
    let selectedPlan = null;
    let loggedInUser = null;


    /* =====================================================
       5. HELPER FUNCTIONS
    ===================================================== */

    function showToast(message, type = "success") {

        if (!toast) {
            alert(message);
            return;
        }

        toast.textContent = message;
        toast.className = "toast show";

        if (type === "error") {
            toast.classList.add("error");
        }

        clearTimeout(showToast.timeout);

        showToast.timeout = setTimeout(() => {
            toast.classList.remove("show", "error");
        }, 3500);
    }


    function formatPrice(price) {
        return "₹" + Number(price).toLocaleString("en-IN");
    }


    function getCourse(courseKey) {
        return courses[courseKey] || null;
    }


    function getCourseKey(courseName) {

        return Object.keys(courses).find(
            key => courses[key].name === courseName
        ) || null;

    }


    function getPlanKey(planName) {

        if (!planName) return null;

        const key = planName.toLowerCase();

        return ["basic", "professional", "advanced"].includes(key)
            ? key
            : null;

    }


    function savePendingEnrollment() {

        if (!selectedCourse) {
            sessionStorage.removeItem(ENROLLMENT_KEY);
            return;
        }

        sessionStorage.setItem(
            ENROLLMENT_KEY,
            JSON.stringify({
                course: selectedCourse,
                plan: selectedPlan
            })
        );

    }


    function loadPendingEnrollment() {

        try {

            const saved = sessionStorage.getItem(ENROLLMENT_KEY);

            if (!saved) return;

            const enrollment = JSON.parse(saved);

            if (getCourse(enrollment.course)) {

                selectedCourse = enrollment.course;

                selectedPlan = getCourse(enrollment.course)
                    .plans[enrollment.plan]
                    ? enrollment.plan
                    : null;

            }

        } catch (error) {

            console.error("Unable to restore enrollment:", error);

            sessionStorage.removeItem(ENROLLMENT_KEY);

        }

    }


    /* =====================================================
       6. MOBILE NAVIGATION
    ===================================================== */

    if (menuToggle && navLinks) {

        menuToggle.addEventListener("click", () => {

            const isActive = navLinks.classList.toggle("active");

            menuToggle.classList.toggle("active", isActive);

            menuToggle.setAttribute("aria-expanded", String(isActive));

        });


        navLinks.querySelectorAll("a").forEach(link => {

            link.addEventListener("click", () => {

                navLinks.classList.remove("active");
                menuToggle.classList.remove("active");

                menuToggle.setAttribute("aria-expanded", "false");

            });

        });

    }


    /* =====================================================
       7. SCROLL REVEAL ANIMATION
    ===================================================== */

    const revealElements = document.querySelectorAll(
        ".reveal, .course-card, .benefit-card, .pricing-card"
    );


    if ("IntersectionObserver" in window) {

        const observer = new IntersectionObserver(
            (entries, observerInstance) => {

                entries.forEach(entry => {

                    if (entry.isIntersecting) {

                        entry.target.classList.add("visible");

                        observerInstance.unobserve(entry.target);

                    }

                });

            },
            {
                threshold: 0.12
            }
        );


        revealElements.forEach(element => observer.observe(element));

    } else {

        revealElements.forEach(element => {
            element.classList.add("visible");
        });

    }


    /* =====================================================
       8. COURSE CATEGORY FILTER
    ===================================================== */

    const categoryButtons = document.querySelectorAll("[data-category]");

    const courseCards = document.querySelectorAll(".course-card");


    categoryButtons.forEach(button => {

        button.addEventListener("click", () => {

            const category = button.dataset.category;

            categoryButtons.forEach(item => {
                item.classList.remove("active");
            });

            button.classList.add("active");


            courseCards.forEach(card => {

                const cardCategory = card.dataset.courseCategory;

                const shouldShow =
                    category === "all" || cardCategory === category;

                card.style.display = shouldShow ? "" : "none";

            });

        });

    });


    /* =====================================================
       9. COURSE SELECTION
    ===================================================== */

    function selectCourse(courseKey, shouldScroll = true) {

        const course = getCourse(courseKey);

        if (!course) {

            showToast("Please select a valid course.", "error");

            return;

        }

        selectedCourse = courseKey;

        // A new course requires a fresh plan selection.
        selectedPlan = null;

        if (pricingCourse) {
            pricingCourse.value = course.name;
        }

        if (registrationCourse) {
            registrationCourse.value = course.name;
        }

        savePendingEnrollment();

        updatePricingDisplay();

        updateEnrollmentSummary();

        if (selectionMessage) {

            selectionMessage.textContent =
                `${course.name} selected. Now choose your learning plan.`;

        }

        if (shouldScroll) {

            const pricingSection = document.getElementById("pricing");

            pricingSection?.scrollIntoView({
                behavior: "smooth",
                block: "start"
            });

        }

    }


    // Course buttons from the course cards.
    document.querySelectorAll(".select-course, [data-select-course]")
        .forEach(button => {

            button.addEventListener("click", () => {

                const courseName = button.dataset.course;

                const courseKey = getCourseKey(courseName);

                if (courseKey) {
                    selectCourse(courseKey);
                } else {
                    showToast("Course information could not be found.", "error");
                }

            });

        });


    /* =====================================================
       10. COURSE-SPECIFIC PRICING
    ===================================================== */

    function updatePricingDisplay() {

        if (!selectedCourse) return;

        const course = getCourse(selectedCourse);

        if (!course) return;


        document.querySelectorAll(".pricing-card").forEach(card => {

            const planButton = card.querySelector("[data-plan]");

            const planKey = getPlanKey(planButton?.dataset.plan);

            if (!planKey) return;

            const plan = course.plans[planKey];

            const priceElement = card.querySelector(".price, .plan-price");
            const durationElement = card.querySelector(".duration, .plan-duration");

            if (priceElement) {
                priceElement.textContent = formatPrice(plan.price);
            }

            if (durationElement) {
                durationElement.textContent = plan.duration;
            }

            card.querySelector(".plan-course-name")?.replaceChildren(
                document.createTextNode(course.name)
            );

        });


        // Optional dynamic pricing fields.
        document.querySelectorAll("[data-course-price]").forEach(element => {

            const planKey = getPlanKey(element.dataset.coursePrice);

            if (planKey) {
                element.textContent = formatPrice(course.plans[planKey].price);
            }

        });


        document.querySelectorAll("[data-course-duration]").forEach(element => {

            const planKey = getPlanKey(element.dataset.courseDuration);

            if (planKey) {
                element.textContent = course.plans[planKey].duration;
            }

        });

    }


    if (pricingCourse) {

        pricingCourse.addEventListener("change", () => {

            const courseKey = getCourseKey(pricingCourse.value);

            if (courseKey) {
                selectCourse(courseKey, false);
            } else {

                selectedCourse = null;
                selectedPlan = null;

                savePendingEnrollment();

                updateEnrollmentSummary();

            }

        });

    }


    /* =====================================================
       11. PLAN SELECTION
    ===================================================== */

    function choosePlan(planName) {

        if (!selectedCourse) {

            showToast("Please select a course first.", "error");

            document.getElementById("pricing")?.scrollIntoView({
                behavior: "smooth"
            });

            return;

        }

        const planKey = getPlanKey(planName);

        const course = getCourse(selectedCourse);

        if (!course || !planKey || !course.plans[planKey]) {

            showToast("Please select a valid learning plan.", "error");

            return;

        }

        selectedPlan = planKey;

        savePendingEnrollment();

        updateEnrollmentSummary();

        const plan = course.plans[planKey];

        if (selectionMessage) {

            selectionMessage.textContent =
                `${course.name} - ${plan.name} plan selected.`;

        }

        showToast(`${plan.name} plan selected successfully!`);

        // Continue with signup.
        openAuthModal("signup");

    }


    document.querySelectorAll(
        ".select-plan, .plan-button, [data-select-plan]"
    ).forEach(button => {

        button.addEventListener("click", () => {

            const planName =
                button.dataset.selectPlan ||
                button.dataset.plan;

            choosePlan(planName);

        });

    });


    /* =====================================================
       12. AUTHENTICATION MODAL
    ===================================================== */

    function openAuthModal(tab = "login") {

        if (!authModal) return;

        authModal.hidden = false;

        authModal.classList.add("active");

        document.body.classList.add("modal-open");

        switchAuthTab(tab);

    }


    function closeAuthModal() {

        if (!authModal) return;

        authModal.classList.remove("active");

        authModal.hidden = true;

        document.body.classList.remove("modal-open");

    }


    function switchAuthTab(tab) {

        const isLogin = tab === "login";

        if (loginTab) {
            loginTab.classList.toggle("active", isLogin);
        }

        if (signupTab) {
            signupTab.classList.toggle("active", !isLogin);
        }

        if (loginForm) {
            loginForm.hidden = !isLogin;
            loginForm.classList.toggle("active", isLogin);
        }

        if (signupForm) {
            signupForm.hidden = isLogin;
            signupForm.classList.toggle("active", !isLogin);
        }

        if (authTitle) {

            authTitle.textContent = isLogin
                ? "Welcome Back!"
                : "Create Your Account";

        }

        if (authSubtitle) {

            authSubtitle.textContent = isLogin
                ? "Login to continue your learning journey."
                : "Sign up to begin your journey with PeakProSys.";

        }

        if (authFeedback) {
            authFeedback.textContent = "";
        }

    }


    // Navbar login/signup buttons.
    document.querySelectorAll("[data-open-auth]").forEach(button => {

        button.addEventListener("click", () => {

            openAuthModal(button.dataset.openAuth);

        });

    });


    // Authentication tabs and inline links.
    document.querySelectorAll("[data-auth-tab]").forEach(button => {

        button.addEventListener("click", () => {

            switchAuthTab(button.dataset.authTab);

        });

    });


    // Close button.
    closeAuth?.addEventListener("click", closeAuthModal);


    // Click outside modal.
    authModal?.addEventListener("click", event => {

        if (
            event.target === authModal ||
            event.target.hasAttribute("data-close-auth")
        ) {

            closeAuthModal();

        }

    });


    // Escape key.
    document.addEventListener("keydown", event => {

        if (event.key === "Escape" && authModal && !authModal.hidden) {

            closeAuthModal();

        }

    });


    /* =====================================================
       13. FORGOT PASSWORD
    ===================================================== */

    forgotPassword?.addEventListener("click", event => {

        event.preventDefault();

        if (authFeedback) {

            authFeedback.textContent =
                "Password recovery will be available after backend integration.";

        }

    });

    /* =====================================================
       PASSWORD VISIBILITY TOGGLE
    ===================================================== */

    document.querySelectorAll(".toggle-password").forEach(button => {

        button.addEventListener("click", () => {

            const targetId = button.dataset.target;
            const passwordInput = document.getElementById(targetId);
            const icon = button.querySelector("i");

            if (!passwordInput || !icon) return;

            const isHidden = passwordInput.type === "password";

            // Show or hide password
            passwordInput.type = isHidden ? "text" : "password";

            // Change eye icon
            icon.classList.toggle("fa-eye", !isHidden);
            icon.classList.toggle("fa-eye-slash", isHidden);

            // Update accessibility label
            button.setAttribute(
                "aria-label",
                isHidden ? "Hide password" : "Show password"
            );

        });

    });

       /* =====================================================
          14. SIGNUP
       ===================================================== */

    if (signupForm) {

        signupForm.addEventListener("submit", event => {

            event.preventDefault();

            const name = document.getElementById("signupName")?.value.trim();

            const email = document.getElementById("signupEmail")?.value.trim().toLowerCase();

            const mobile = document.getElementById("signupMobile")?.value.trim();

            const password = document.getElementById("signupPassword")?.value;

            const confirmPassword = document.getElementById("confirmPassword")?.value;

            const terms = document.getElementById("signupTerms");


            if (!name || !email || !mobile || !password || !confirmPassword) {

                showToast("Please complete all required fields.", "error");

                return;

            }


            if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {

                showToast("Please enter a valid email address.", "error");

                return;

            }


            if (!/^[6-9]\d{9}$/.test(mobile)) {

                showToast("Please enter a valid 10-digit mobile number.", "error");

                return;

            }


            if (password.length < 6) {

                showToast("Password must contain at least 6 characters.", "error");

                return;

            }


            if (password !== confirmPassword) {

                showToast("Passwords do not match.", "error");

                return;

            }


            if (!terms?.checked) {

                showToast("Please accept the terms and conditions.", "error");

                return;

            }


            let existingUser = null;

            try {

                existingUser = JSON.parse(
                    localStorage.getItem(DEMO_USER_KEY)
                );

            } catch (error) {

                console.error("Unable to read demo account:", error);

            }


            if (existingUser && existingUser.email === email) {

                showToast("An account with this email already exists. Please login.", "error");

                return;

            }


            const user = {
                name,
                email,
                mobile
            };


            // Frontend demonstration only.
            // Never store passwords in localStorage.

            localStorage.setItem(
                DEMO_USER_KEY,
                JSON.stringify(user)
            );


            loggedInUser = user;

            sessionStorage.setItem(DEMO_SESSION_KEY, "true");


            signupForm.reset();

            closeAuthModal();

            showStudentPortal();

            showToast("Account created successfully! Welcome to PeakProSys.");

        });

    }


    /* =====================================================
       15. LOGIN
    ===================================================== */

    if (loginForm) {

        loginForm.addEventListener("submit", event => {

            event.preventDefault();

            const email = document.getElementById("loginEmail")?.value.trim().toLowerCase();

            const password = document.getElementById("loginPassword")?.value;

            if (!email || !password) {

                showToast("Please enter your email and password.", "error");

                return;

            }


            if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {

                showToast("Please enter a valid email address.", "error");

                return;

            }


            let savedUser = null;

            try {

                savedUser = JSON.parse(
                    localStorage.getItem(DEMO_USER_KEY)
                );

            } catch (error) {

                console.error("Unable to read demo account:", error);

            }


            if (!savedUser || savedUser.email !== email) {

                showToast("Account not found. Please sign up first.", "error");

                return;

            }


            /*
             * IMPORTANT:
             * This is frontend-only demo authentication.
             * The password is not verified.
             * Real authentication requires a backend.
             */

            loggedInUser = savedUser;

            sessionStorage.setItem(DEMO_SESSION_KEY, "true");


            loginForm.reset();

            closeAuthModal();

            showStudentPortal();

            showToast("Demo login successful! Welcome back.");

        });

    }


    /* =====================================================
       16. STUDENT PORTAL VISIBILITY
    ===================================================== */

    function hideStudentPortal() {

        if (studentPortal) {

            studentPortal.hidden = true;

            studentPortal.style.display = "none";

            studentPortal.classList.remove("active");

        }

        if (publicWebsite) {
            publicWebsite.hidden = false;
        }

        if (header) {
            header.hidden = false;
        }

        if (footer) {
            footer.hidden = false;
        }

        document.body.classList.remove("portal-open");

    }


    function showStudentPortal() {

        if (!sessionStorage.getItem(DEMO_SESSION_KEY)) {

            showToast("Please login to access student registration.", "error");

            openAuthModal("login");

            return;

        }


        if (!studentPortal) {

            showToast("Student portal could not be found.", "error");

            return;

        }


        studentPortal.hidden = false;

        studentPortal.style.display = "";

        studentPortal.classList.add("active");


        if (publicWebsite) {
            publicWebsite.hidden = true;
        }

        if (header) {
            header.hidden = true;
        }

        if (footer) {
            footer.hidden = true;
        }


        document.body.classList.add("portal-open");


        populateStudentDetails();

        updateEnrollmentSummary();


        studentPortal.scrollIntoView({
            behavior: "smooth",
            block: "start"
        });

    }


    /* =====================================================
       17. POPULATE STUDENT DETAILS
    ===================================================== */

    function populateStudentDetails() {

        if (!loggedInUser) return;


        const nameInput = document.getElementById("fullName");

        const emailInput = document.getElementById("studentEmail");

        const mobileInput = document.getElementById("mobile");

        const portalName = document.getElementById("portalStudentName");


        if (nameInput) {
            nameInput.value = loggedInUser.name || "";
        }

        if (emailInput) {
            emailInput.value = loggedInUser.email || "";
        }

        if (mobileInput) {
            mobileInput.value = loggedInUser.mobile || "";
        }

        if (portalName) {
            portalName.textContent = loggedInUser.name || "Student";
        }

    }


    /* =====================================================
       18. ENROLLMENT SUMMARY
    ===================================================== */

    function updateEnrollmentSummary() {

        const summaryCourse = document.getElementById("summaryCourse");

        const summaryCourseDuration = document.getElementById("summaryCourseDuration");

        const summaryPlan = document.getElementById("summaryPlan");

        const summaryPlanDuration = document.getElementById("summaryPlanDuration");

        const summaryPrice = document.getElementById("summaryPrice");


        if (!selectedCourse || !getCourse(selectedCourse)) {

            if (summaryCourse) summaryCourse.textContent = "Not selected";

            if (summaryCourseDuration) summaryCourseDuration.textContent = "Not selected";

            if (summaryPlan) summaryPlan.textContent = "Not selected";

            if (summaryPlanDuration) summaryPlanDuration.textContent = "Not selected";

            if (summaryPrice) summaryPrice.textContent = "Not selected";

            return;

        }


        const course = getCourse(selectedCourse);


        if (summaryCourse) {
            summaryCourse.textContent = course.name;
        }

        if (summaryCourseDuration) {
            summaryCourseDuration.textContent = course.duration;
        }


        if (registrationCourse) {
            registrationCourse.value = selectedCourse ? course.name : "";
        }


        const plan = selectedPlan ? course.plans[selectedPlan] : null;


        if (summaryPlan) {
            summaryPlan.textContent = plan ? plan.name : "Not selected";
        }

        if (summaryPlanDuration) {
            summaryPlanDuration.textContent = plan ? plan.duration : "Not selected";
        }

        if (summaryPrice) {
            summaryPrice.textContent = plan ? formatPrice(plan.price) : "Not selected";
        }


        if (registrationPlan) {
            registrationPlan.value = plan ? plan.name : "";
        }

    }


    /* =====================================================
       19. REGISTRATION COURSE / PLAN CHANGES
    ===================================================== */

    registrationCourse?.addEventListener("change", () => {

        const courseKey = getCourseKey(registrationCourse.value);

        if (!courseKey) return;

        selectedCourse = courseKey;

        selectedPlan = null;

        if (pricingCourse) {
            pricingCourse.value = registrationCourse.value;
        }

        savePendingEnrollment();

        updatePricingDisplay();

        updateEnrollmentSummary();

    });


    registrationPlan?.addEventListener("change", () => {

        const planKey = getPlanKey(registrationPlan.value);

        if (!selectedCourse || !planKey) return;

        selectedPlan = planKey;

        savePendingEnrollment();

        updateEnrollmentSummary();

    });


    /* =====================================================
       20. STUDENT REGISTRATION FORM
    ===================================================== */

    if (registrationForm) {

        registrationForm.addEventListener("submit", event => {

            event.preventDefault();


            if (!sessionStorage.getItem(DEMO_SESSION_KEY)) {

                showToast("Please login before registering.", "error");

                hideStudentPortal();

                openAuthModal("login");

                return;

            }


            if (!selectedCourse || !selectedPlan) {

                showToast("Please select a course and learning plan first.", "error");

                return;

            }


            if (!registrationForm.checkValidity()) {

                registrationForm.reportValidity();

                return;

            }


            const course = getCourse(selectedCourse);

            const plan = course.plans[selectedPlan];


            const registrationData = {

                studentName: document.getElementById("fullName").value.trim(),

                email: document.getElementById("studentEmail").value.trim(),

                mobile: document.getElementById("mobile").value.trim(),

                dateOfBirth: document.getElementById("dob").value,

                gender: document.getElementById("gender").value,

                qualification: document.getElementById("qualification").value,

                course: course.name,

                courseDuration: course.duration,

                plan: plan.name,

                planDuration: plan.duration,

                price: plan.price,

                trainingMode: document.getElementById("trainingMode").value,

                address: document.getElementById("address").value.trim(),

                submittedAt: new Date().toISOString()

            };


            // Save demo registration records locally.
            let registrations = [];

            try {

                registrations = JSON.parse(
                    localStorage.getItem(REGISTRATION_KEY)
                ) || [];

            } catch (error) {

                console.error("Unable to load registrations:", error);

            }


            registrations.push(registrationData);

            localStorage.setItem(
                REGISTRATION_KEY,
                JSON.stringify(registrations)
            );


            console.log("Demo registration submitted:", registrationData);


            registrationForm.reset();

            populateStudentDetails();

            updateEnrollmentSummary();


            if (registrationFeedback) {

                registrationFeedback.textContent =
                    "Your registration has been submitted successfully!";

            }


            showToast("Registration submitted successfully!");

        });

    }


    /* =====================================================
       21. CHANGE COURSE / PLAN
    ===================================================== */

    document.querySelectorAll(
        ".change-selection-button, [data-change-selection]"
    ).forEach(button => {

        button.addEventListener("click", () => {

            hideStudentPortal();

            document.getElementById("pricing")?.scrollIntoView({
                behavior: "smooth"
            });

        });

    });


    /* =====================================================
       22. STUDENT PORTAL NAVIGATION
    ===================================================== */

    document.querySelectorAll(
        '[href="#student-registration"], [data-open-registration]'
    ).forEach(link => {

        link.addEventListener("click", event => {

            event.preventDefault();

            if (!sessionStorage.getItem(DEMO_SESSION_KEY)) {

                showToast("Please login to access student registration.", "error");

                openAuthModal("login");

                return;

            }

            showStudentPortal();

        });

    });


    /* =====================================================
       23. LOGOUT
    ===================================================== */

    document.querySelectorAll(
        "#logoutButton, .logout-button, [data-logout]"
    ).forEach(button => {

        button.addEventListener("click", () => {

            sessionStorage.removeItem(DEMO_SESSION_KEY);

            sessionStorage.removeItem(ENROLLMENT_KEY);

            loggedInUser = null;

            selectedCourse = null;

            selectedPlan = null;


            if (registrationForm) {
                registrationForm.reset();
            }

            if (pricingCourse) {
                pricingCourse.value = "";
            }

            if (selectionMessage) {

                selectionMessage.textContent =
                    "Select a course and a learning plan to continue.";

            }

            updateEnrollmentSummary();

            hideStudentPortal();

            showToast("You have been logged out successfully.");

            window.scrollTo({
                top: 0,
                behavior: "smooth"
            });

        });

    });


    /* =====================================================
       24. FOOTER YEAR
    ===================================================== */

    const yearElement = document.getElementById("currentYear");

    if (yearElement) {

        yearElement.textContent = new Date().getFullYear();

    }


    /* =====================================================
       25. INITIAL PAGE SETUP
    ===================================================== */

    loadPendingEnrollment();

    updatePricingDisplay();

    updateEnrollmentSummary();


    // Restore the demo user session if available.
    if (sessionStorage.getItem(DEMO_SESSION_KEY)) {

        try {

            loggedInUser = JSON.parse(
                localStorage.getItem(DEMO_USER_KEY)
            );

        } catch (error) {

            loggedInUser = null;

        }


        if (!loggedInUser) {

            sessionStorage.removeItem(DEMO_SESSION_KEY);

        }

    }


    // Always start on the public homepage.
    hideStudentPortal();


    // Restore any previously selected course in pricing.
    if (selectedCourse && pricingCourse) {

        pricingCourse.value = courses[selectedCourse].name;

        updatePricingDisplay();

    }


    console.log("PeakProSys JavaScript initialized successfully.");

});
