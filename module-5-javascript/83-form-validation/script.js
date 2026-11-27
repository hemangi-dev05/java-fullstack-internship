
const form = document.getElementById("registrationForm");

const fields = {
    fullName: document.getElementById("fullName"),
    email: document.getElementById("email"),
    mobile: document.getElementById("mobile"),
    course: document.getElementById("course"),
    password: document.getElementById("password"),
    confirmPassword: document.getElementById("confirmPassword"),
    terms: document.getElementById("terms")
};

const errors = {
    fullName: document.getElementById("nameError"),
    email: document.getElementById("emailError"),
    mobile: document.getElementById("mobileError"),
    course: document.getElementById("courseError"),
    password: document.getElementById("passwordError"),
    confirmPassword: document.getElementById("confirmPasswordError"),
    terms: document.getElementById("termsError")
};

const successMessage = document.getElementById("successMessage");

function showError(field, message) {
    errors[field].textContent = message;
    fields[field].classList.add("invalid");
}

function clearError(field) {
    errors[field].textContent = "";
    fields[field].classList.remove("invalid");
}

function validateName() {
    const name = fields.fullName.value.trim();

    if (name.length < 3) {
        showError("fullName", "Name must contain at least 3 characters.");
        return false;
    }

    if (!/^[a-zA-Z ]+$/.test(name)) {
        showError("fullName", "Name can contain only letters and spaces.");
        return false;
    }

    clearError("fullName");
    return true;
}

function validateEmail() {
    const email = fields.email.value.trim();
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!pattern.test(email)) {
        showError("email", "Please enter a valid email address.");
        return false;
    }

    clearError("email");
    return true;
}

function validateMobile() {
    const mobile = fields.mobile.value.trim();

    if (!/^[6-9]\d{9}$/.test(mobile)) {
        showError("mobile", "Enter a valid 10-digit mobile number.");
        return false;
    }

    clearError("mobile");
    return true;
}

function validateCourse() {
    if (fields.course.value === "") {
        showError("course", "Please select a course.");
        return false;
    }

    clearError("course");
    return true;
}

function validatePassword() {
    const password = fields.password.value;

    if (password.length < 8) {
        showError("password", "Password must contain at least 8 characters.");
        return false;
    }

    clearError("password");
    return true;
}

function validateConfirmPassword() {
    if (fields.confirmPassword.value === "") {
        showError("confirmPassword", "Please confirm your password.");
        return false;
    }

    if (fields.confirmPassword.value !== fields.password.value) {
        showError("confirmPassword", "Passwords do not match.");
        return false;
    }

    clearError("confirmPassword");
    return true;
}

function validateTerms() {
    if (!fields.terms.checked) {
        showError("terms", "Please accept the terms and conditions.");
        return false;
    }

    clearError("terms");
    return true;
}

const validators = {
    fullName: validateName,
    email: validateEmail,
    mobile: validateMobile,
    course: validateCourse,
    password: validatePassword,
    confirmPassword: validateConfirmPassword,
    terms: validateTerms
};

Object.keys(validators).forEach(field => {
    fields[field].addEventListener("input", () => {
        validators[field]();
    });

    fields[field].addEventListener("change", () => {
        validators[field]();
    });
});

form.addEventListener("submit", function (event) {
    event.preventDefault();

    const isValid = Object.values(validators)
        .every(validate => validate());

    if (!isValid) {
        successMessage.textContent = "";
        return;
    }

    successMessage.textContent =
        `Registration successful! Welcome, ${fields.fullName.value.trim()}.`;

    form.reset();

    Object.keys(errors).forEach(clearError);
});
