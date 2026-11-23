
// ========================================
// Assignment 65 - Login / Signup Page
// PeakProSys Training Institute
// ========================================

const loginForm = document.getElementById("loginForm");
const signupForm = document.getElementById("signupForm");

const loginTab = document.getElementById("loginTab");
const signupTab = document.getElementById("signupTab");

const showSignup = document.getElementById("showSignup");
const showLogin = document.getElementById("showLogin");

const signupPassword = document.getElementById("signupPassword");
const confirmPassword = document.getElementById("confirmPassword");

// Switch between Login and Signup forms

function displayLogin() {
    loginForm.classList.remove("hidden");
    signupForm.classList.add("hidden");

    loginTab.classList.add("active");
    signupTab.classList.remove("active");
}

function displaySignup() {
    signupForm.classList.remove("hidden");
    loginForm.classList.add("hidden");

    signupTab.classList.add("active");
    loginTab.classList.remove("active");
}

// Tab button events

loginTab.addEventListener("click", displayLogin);
signupTab.addEventListener("click", displaySignup);

// Form link button events

showSignup.addEventListener("click", displaySignup);
showLogin.addEventListener("click", displayLogin);

// Validate matching passwords

function validatePasswords() {
    if (signupPassword.value !== confirmPassword.value) {
        confirmPassword.setCustomValidity("Passwords do not match.");
    } else {
        confirmPassword.setCustomValidity("");
    }
}

signupPassword.addEventListener("input", validatePasswords);
confirmPassword.addEventListener("input", validatePasswords);

// Login form submission (frontend demonstration only)

loginForm.addEventListener("submit", function (event) {
    event.preventDefault();

    alert("Login form submitted successfully! Backend authentication is not connected yet.");

    loginForm.reset();
});

// Signup form submission (frontend demonstration only)

signupForm.addEventListener("submit", function (event) {
    event.preventDefault();

    validatePasswords();

    if (!signupForm.checkValidity()) {
        signupForm.reportValidity();
        return;
    }

    alert("Registration form submitted successfully! Backend registration is not connected yet.");

    signupForm.reset();
    displayLogin();
});
