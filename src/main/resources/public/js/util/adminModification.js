const xmlHttpRequest = new XMLHttpRequest();
const formControlElements = document.getElementsByClassName("form-control");

function setRole() {
    document.getElementById("role").innerHTML =
        `<div class="form-outline mb-4">
            <select id="roleValue" class="form-control form-control-lg" required>
                <option value="">-Select a role-</option>
                <option value="user">User</option>
                <option value="admin">Admin</option>
            </select>
    
            <label class="form-label" for="roleValue">A role</label>
            <div class="invalid-feedback">Please select a role</div>
        </div>`;
}

function setAdminModification(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    if (!localStorage.getItem(rolesStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }

    setNavigation("../../index.html", "../../img/logo.png", "../../cart.html", "../../about.html",
        "../../login.html", "../../registration.html", "admin.html", "../user.html");
    setUserModification();
    setRole();

    document.getElementById("headline").innerHTML = headlineInnerHtml;
    document.getElementById("submit").innerHTML = submitInnerHtml;

    for (const formControlElement of formControlElements) {
        formControlElement.onchange = function () {
            localStorage.setItem(storageKeyPrefix + formControlElement.id, formControlElement.value);
        }

        formControlElement.value = localStorage.getItem(storageKeyPrefix + formControlElement.id);
    }
}

function areInputsValid() {
    for (const formOutlineElement of document.getElementsByClassName("form-outline")) {
        const formControlElement = formOutlineElement.getElementsByClassName("form-control")[0];

        if (!formControlElement.checkValidity()) {
            alert("danger", formOutlineElement.getElementsByClassName("invalid-feedback")[0].innerHTML);
            return false;
        }

        const maxElementLength = 255;

        if (formControlElement.value.length > maxElementLength) {
            alert("danger", "A field should be equal or less than " + maxElementLength + " symbols");
            return false;
        }
    }

    if (document.getElementById("password").value !== document.getElementById("passwordConformation").value) {
        alert("danger", "Passwords do not match");
        return false;
    }

    return true;
}

function createBody() {
    const body = {};
    body[userUuidDtoKey] = null;

    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (value === document.getElementById("roleValue").value) {
            body[userRoleUuidDtoKey] = key;
        }
    }

    body[userLoginDtoKey] = document.getElementById("login").value;
    body[userEmailDtoKey] = document.getElementById("email").value;
    body[userFirstNameDtoKey] = document.getElementById("firstName").value;
    body[userLastNameDtoKey] = document.getElementById("secondName").value;
    body[userBirthdayDtoKey] = document.getElementById("birthday").value;

    return body;
}

function setXmlHttpRequest(successStatus, storageKeyPrefix, successMessage, dangerMessage) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            for (const formControlElement of formControlElements) {
                localStorage.removeItem(storageKeyPrefix + formControlElement.id);
            }

            alert("success", successMessage);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", dangerMessage);
        }
    }
}