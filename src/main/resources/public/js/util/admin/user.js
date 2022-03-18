let role;

function setAdminModificationForm(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    redirectUnauthorized();
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

    storageKeyPrefix = "user_" + storageKeyPrefix;
    setUserInputs(headlineInnerHtml, submitInnerHtml, storageKeyPrefix);
    role.value = localStorage.getItem(storageKeyPrefix + role.id);
}

function appendRoleToBody(body) {
    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (value === document.getElementById("roleValue").value) {
            body[userRoleUuidDtoKey] = key;
        }
    }

    return body;
}
