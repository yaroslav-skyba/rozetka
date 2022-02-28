const xmlHttpRequest = new XMLHttpRequest();
const formControlElements = document.getElementsByClassName("form-control");

function setAdminModification(headlineInnerHtml, submitInnerHtml, storageKeyPrefix, httpMethod, url) {
    if (!localStorage.getItem(rolesStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }

    setNavigation("../../index.html", "../../img/logo.png", "../../cart.html", "../../about.html",
        "../../login.html", "../../registration.html", "admin.html", "../user.html");
    setUserModification();

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

    document.getElementById("headline").innerHTML = headlineInnerHtml;
    document.getElementById("submit").innerHTML = submitInnerHtml;

    for (const formControlElement of formControlElements) {
        formControlElement.onchange = function () {
            localStorage.setItem(storageKeyPrefix + formControlElement.id, formControlElement.value);
        }

        formControlElement.value = localStorage.getItem(storageKeyPrefix + formControlElement.id);
    }

    document.getElementById("submit").onclick = function () {
        for (const formOutlineElement of document.getElementsByClassName("form-outline")) {
            const formControlElement = formOutlineElement.getElementsByClassName("form-control")[0];

            if (!formControlElement.checkValidity()) {
                alert("danger", formOutlineElement.getElementsByClassName("invalid-feedback")[0].innerHTML);
                return;
            }

            if (formControlElement.value.length > 255) {
                alert("danger", "A field can't be longer than 255 symbols");
                return;
            }
        }

        const body = {};
        body[userUuidDtoKey] = null;

        const roleValue = document.getElementById("roleValue").value;

        for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
            if (value === roleValue) {
                body[userRoleUuidDtoKey] = key;
            }
        }

        body[userLoginDtoKey] = document.getElementById("login").value;
        body[userPasswordDtoKey] = document.getElementById("password").value;
        body[userEmailDtoKey] = document.getElementById("email").value;
        body[userFirstNameDtoKey] = document.getElementById("firstName").value;
        body[userLastNameDtoKey] = document.getElementById("secondName").value;
        body[userBirthdayDtoKey] = document.getElementById("birthday").value;

        xmlHttpRequest.open(httpMethod, url);
        xmlHttpRequest.setRequestHeader("Content-Type", "application/vnd.rozetka.user+json");
        xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
        xmlHttpRequest.send(JSON.stringify(body));
    }
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