let role;

function configUserAdminModificationPage(headlineInnerHtml, submitInnerHtml, storageKey, uuid) {
    setNavigation("../../../", "../../", "../");
    configUserModificationPage();

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

    const user = setUserFormInputs(headlineInnerHtml, submitInnerHtml, storageKey);
    role.value = user[userRoleUuidDtoKey];

    setFormControlElementOnchange(storageKey, function () {
        return createUser(uuid, user[userPasswordDtoKey]);
    });
}

function appendRoleUuidToUser(user) {
    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (value === document.getElementById("roleValue").value) {
            user[userRoleUuidDtoKey] = key;
        }
    }

    return user;
}
