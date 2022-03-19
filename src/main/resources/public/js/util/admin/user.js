const userStorageKeyPrefix = "user_";

let role;

function setUserModificationForm(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    redirectUnauthorizedModification();
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

    storageKeyPrefix = userStorageKeyPrefix + storageKeyPrefix;
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
