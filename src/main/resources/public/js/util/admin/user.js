let userRole;

function redirectWithoutRoles() {
    if (!localStorage.getItem(rolesStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }
}

function configUserAdminModificationPage(userStorageKey, headlineInnerHtml, submitInnerHtml, uuid) {
    setUserModificationPage("../../../", "../../", "../");

    document.getElementById("role").innerHTML =
        `<div class="form-outline mb-4">
            <select id="roleValue" class="form-control form-control-lg" required></select> 
            <label for="roleValue">A role</label>
            <div class="invalid-feedback">Please, select a role</div>
        </div>`;

    userRole = document.getElementById("roleValue");

    const roleValues = Object.values(JSON.parse(localStorage.getItem(rolesStorageKey)));

    for (const roleValue of roleValues) {
        userRole.innerHTML +=
            `<option value="` + roleValue[roleNameDtoKey] + `">`
                + roleValue[roleNameDtoKey].charAt(0).toUpperCase() + roleValue[roleNameDtoKey].slice(1) +
            `</option`;
    }

    const user = JSON.parse(localStorage.getItem(userStorageKey));

    if (user) {
        userRole.value = roleValues.find(value => value[roleUuidDtoKey] === user[userRoleUuidDtoKey])[roleNameDtoKey];
    }

    setUserModificationPageInputs(headlineInnerHtml, submitInnerHtml, userStorageKey);
    setFormControlElementOnchange(userStorageKey, function () {
        const user = createUser(uuid);
        user[userRoleUuidDtoKey] = roleValues.find(value => value[roleNameDtoKey] === userRole.value)[roleUuidDtoKey];

        return user;
    });
}
