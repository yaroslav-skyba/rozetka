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

    document.getElementById("headline").innerHTML = headlineInnerHtml;
    document.getElementById("submit").innerHTML = submitInnerHtml;

    for (const formControlElement of formControlElements) {
        formControlElement.onchange = function () {
            localStorage.setItem(storageKeyPrefix + formControlElement.id, formControlElement.value);
        }
    }

    const role = document.getElementById("roleValue");
    role.value = localStorage.getItem(storageKeyPrefix + role.id);

    const login = document.getElementById("login");
    login.value = localStorage.getItem(storageKeyPrefix + login.id);

    const email = document.getElementById("email");
    email.value = localStorage.getItem(storageKeyPrefix + email.id);

    const firstName = document.getElementById("firstName");
    firstName.value = localStorage.getItem(storageKeyPrefix + firstName.id);

    const secondName = document.getElementById("secondName");
    secondName.value = localStorage.getItem(storageKeyPrefix + secondName.id);

    const birthday = document.getElementById("birthday");
    birthday.value = localStorage.getItem(storageKeyPrefix + birthday.id);
}

function sendModificationRequest(userUuid, passwordValue, httpMethod, url) {
    for (const formOutlineElement of document.getElementsByClassName("form-outline")) {
        const formControlElement = formOutlineElement.getElementsByClassName("form-control")[0];

        if (!formControlElement.checkValidity()) {
            alert("danger", formOutlineElement.getElementsByClassName("invalid-feedback")[0].innerHTML);
            return;
        }

        const maxElementLength = 255;

        if (formControlElement.value.length > maxElementLength) {
            alert("danger", "A field should be equal or less than " + maxElementLength + " symbols");
            return;
        }
    }

    if (document.getElementById("password").value !== document.getElementById("passwordConformation").value) {
        alert("danger", "Passwords do not match");
        return;
    }

    const body = {};
    body[userUuidDtoKey] = userUuid;

    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (value === document.getElementById("roleValue").value) {
            body[userRoleUuidDtoKey] = key;
        }
    }

    body[userLoginDtoKey] = document.getElementById("login").value;
    body[userPasswordDtoKey] = passwordValue;
    body[userEmailDtoKey] = document.getElementById("email").value;
    body[userFirstNameDtoKey] = document.getElementById("firstName").value;
    body[userLastNameDtoKey] = document.getElementById("secondName").value;
    body[userBirthdayDtoKey] = document.getElementById("birthday").value;

    xmlHttpRequest.open(httpMethod, url);
    xmlHttpRequest.setRequestHeader("Content-Type", userMediaType);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
    xmlHttpRequest.send(JSON.stringify(body));
}

function setXmlHttpRequest(successStatus, storageKeyPrefix) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            for (const formControlElement of formControlElements) {
                localStorage.removeItem(storageKeyPrefix + formControlElement.id);
            }

            alert("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}