let role;

function configUserAdminModificationPage(headlineInnerHtml, submitInnerHtml, storageKey, uuid) {
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

    let roleUuid = null;

    for (const roleValue of roleValues) {
        if (roleValue[roleNameDtoKey] === role.value) {
            roleUuid = roleValue[roleUuidDtoKey];
            break;
        }
    }

    setUserFormInputs(headlineInnerHtml, submitInnerHtml, storageKey, uuid, roleUuid);
}
