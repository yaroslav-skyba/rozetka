onload = function () {
    redirectUnauthorized();
    redirectUnauthorized();
    setUserAdminModificationForm("Create", creationStorageKeyPrefix, "create a user");

    document.getElementById("submit").onclick = function () {
        password.required = true;
        passwordConformation.required = true;

        sendModificationRequestIfBodyNotNull("POST", appendRoleUuidToUser(createNullableUser(null, password.value)), usersApiUrl,
                                             userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(201, userStorageKeyPrefix + creationStorageKeyPrefix);
}
