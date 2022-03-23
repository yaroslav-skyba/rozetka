let role;

function configUserAdminModificationPage(userStorageKey, headlineInnerHtml, submitInnerHtml, uuid) {
    configUserModificationPage("../../../", "../../", "../");

    document.getElementById("role").innerHTML =
        `<div class="form-outline mb-4">
            <select id="roleValue" class="form-control form-control-lg" required></select> 
            <label for="roleValue">A role</label>
            <div class="invalid-feedback">Please select a role</div>
        </div>`;

    role = document.getElementById("roleValue");

    const roleValues = Object.values(JSON.parse(localStorage.getItem(rolesStorageKey)));

    for (const roleValue of roleValues) {
        role.innerHTML +=
            `<option value="` + roleValue[roleNameDtoKey] + `">`
                + roleValue[roleNameDtoKey].charAt(0).toUpperCase() + roleValue[roleNameDtoKey].slice(1) +
            `</option`;
    }

    const user = JSON.parse(localStorage.getItem(userStorageKey));

    if (user) {
        role.value = roleValues.find(value => value[roleUuidDtoKey] === user[userRoleUuidDtoKey])[roleNameDtoKey];
    }

    setUserFormInputs(headlineInnerHtml, submitInnerHtml, userStorageKey);
    setFormControlElementOnchange(userStorageKey, function () {
        const user = createUser(uuid);
        user[userRoleUuidDtoKey] = roleValues.find(value => value[roleNameDtoKey] === role.value)[roleUuidDtoKey];

        return user;
    });
}
