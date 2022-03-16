function setAdminForm() {
    setUserForm();

    if (localStorage.getItem(currentUserRoleNameStorageKey) !== adminRoleName) {
        location.href = "/";
    }

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

function setAdminConfig(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    if (!localStorage.getItem(rolesStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }

    setNavigation("../../index.html", "../../img/logo.png", "../../cart.html", "../../about.html",
        "../../login.html", "../../registration.html", "admin.html", "../user.html");

    for (const formControlElement of formControlElements) {
        formControlElement.onchange = function () {
            localStorage.setItem(storageKeyPrefix + formControlElement.id, formControlElement.value);
        }
    }

    const role = document.getElementById("roleValue");
    role.value = localStorage.getItem(storageKeyPrefix + role.id);

    setUserInputs(headlineInnerHtml, submitInnerHtml, storageKeyPrefix);
}

function appendRoleToBody(body) {
    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (value === document.getElementById("roleValue").value) {
            body[userRoleUuidDtoKey] = key;
        }
    }

    return body;
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
