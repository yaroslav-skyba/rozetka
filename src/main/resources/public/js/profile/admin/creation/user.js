const storageKey = userStorageKeyPrefix + creationStorageKeyPrefix;

onload = function () {
    redirectUnauthorized();
    redirectWithoutRoles();

    configUserAdminModificationPage("create a user", submitInnerHtmlCreation, storageKey, null);

    document.getElementById("submit").onclick = function () {
        password.required = true;
        passwordConformation.required = true;

        sendModificationRequestIfBodyNotNull("POST", appendRoleUuidToUser(createNullableUser(null, password.value)), usersApiUrl,
                                             userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(201, storageKey);
}
