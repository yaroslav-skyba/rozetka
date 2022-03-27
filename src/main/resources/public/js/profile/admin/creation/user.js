const userStorageKey = userStorageKeyPrefix + creationStorageKeyPart;

onload = function () {
    redirectUnauthorized(adminRoleName);;
    redirectWithoutRoles();

    configUserAdminModificationPage(userStorageKey, "create a user", innerHtmlCreationSubmit, null);

    submit.onclick = function () {
        password.required = true;
        passwordConformation.required = true;

        sendModificationHttpRequest(getUser(userStorageKey),"POST",  usersApiUrl, userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, userStorageKey);
}
