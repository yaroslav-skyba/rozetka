let role;

function setUserModificationForm(headlineInnerHtml, submitInnerHtml, storageKey, uuid) {
    setNavigation("../../../", "../../", "../");
    setUserForm();

    document.getElementById("role").innerHTML =
        `<div class="form-outline mb-4">
            <select id="roleValue" class="form-control form-control-lg" required>
                <option value="` + userRoleName + `">User</option>
                <option value="` + adminRoleName + `">Admin</option>
            </select>
    
            <label for="roleValue">A role</label>
            <div class="invalid-feedback">Please select a role</div>
        </div>`;

    role = document.getElementById("roleValue");
    role.value = setUserInputs(headlineInnerHtml, submitInnerHtml, storageKey)[userRoleUuidDtoKey];
}

function appendRoleToBody(body) {
    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (value === document.getElementById("roleValue").value) {
            body[userRoleUuidDtoKey] = key;
        }
    }

    return body;
}
