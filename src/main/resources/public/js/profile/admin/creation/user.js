onload = function () {
    redirectUnauthorized();
    redirectUnauthorized();
    setUserAdminModificationForm("Create", creationStorageKeyPrefix, "create a user");

    document.getElementById("submit").onclick = function () {
        const password = document.getElementById("password");

        password.required = true;
        document.getElementById("passwordConformation").required = true;

        sendModificationRequestIfBodyNotNull("POST", appendRoleUuidToUser(createNullableUser(null, password.value)),
                                             usersApiUrl, userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, userStorageKeyPrefix + creationStorageKeyPrefix);
}
