let role;

function configUserAdminModificationPage(headlineInnerHtml, submitInnerHtml, storageKey, uuid) {
    setNavigation("../../../", "../../", "../");
    configUserModificationPage();

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

    const user = setUserFormInputs(headlineInnerHtml, submitInnerHtml, storageKey);

    let roleUuid = null;
    let password = null;

    if (user) {
        for (const roleValue of roleValues) {
            if (roleValue[roleNameDtoKey] === role.value) {
                role.value = roleValue[roleNameDtoKey];
                roleUuid = roleValue[roleUuidDtoKey];
                break;
            }
        }

        password = user[userPasswordDtoKey];
    }

    setFormControlElementOnchange(storageKey, function () {
        return createUser(uuid, roleUuid, password);
    });
}
