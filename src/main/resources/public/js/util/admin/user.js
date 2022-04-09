let userRole;

function setUserAdminPage(storageKey, headlineInnerHtml, submitInnerHtml, httpMethod) {
    setUserPage("../../../", "../../", "../", headlineInnerHtml, submitInnerHtml, storageKey, httpMethod);

    document.getElementById("role").innerHTML =
        `<div class="form-outline mb-4">
            <select id="roleValue" class="form-control form-control-lg" required></select> 
            <label for="roleValue">A role</label>
            <div class="invalid-feedback">Please, select a role</div>
        </div>`;

    userRole = document.getElementById("roleValue");

    const roles = Object.values(JSON.parse(localStorage.getItem(rolesStorageKey)));

    for (const role of roles) {
        userRole.innerHTML += `<option value="` + role[roleNameDtoKey] + `">` + role[roleNameDtoKey] + `</option>`;
    }

    userRole.value = roles.reduce((role, value) =>
        value[roleUuidDtoKey] === JSON.parse(localStorage.getItem(storageKey))[userRoleUuidDtoKey] ? value[roleNameDtoKey] : role,
        userRoleName);

    userRole.onchange = function() {
        const user = JSON.parse(localStorage.getItem(storageKey));
        user[userRoleUuidDtoKey] = roles.find(value => value[roleNameDtoKey] === userRole.value)[roleUuidDtoKey];

        localStorage.setItem(storageKey, JSON.stringify(user));
    }
}
