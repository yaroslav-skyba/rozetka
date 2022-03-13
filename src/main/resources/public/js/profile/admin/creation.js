onload = function () {
    setUserForm();
    setRole();
    setAdminForm("Create an account", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        const password = document.getElementById("password");

        password.required = true;
        document.getElementById("passwordConformation").required = true;

        const body = createModificationRequestBody(null, password.value);

        if (!body) {
            return;
        }

        for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
            if (value === document.getElementById("roleValue").value) {
                body[userRoleUuidDtoKey] = key;
            }
        }

        sendModificationRequest("POST", usersApiUrl, body);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(201, creationStorageKeyPrefix);
}
