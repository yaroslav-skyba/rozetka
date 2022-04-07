const userStorageKey = userStorageKeyPart + creationStorageKeyPart;

onload = function () {
    redirectUnauthorized(adminRoleName);
    redirectWithoutRoles();

    configUserAdminModificationPage(userStorageKey, "create a user", innerHtmlCreationSubmit, null);

    submit.onclick = function () {
        userPassword.required = true;
        userPasswordConformation.required = true;

        sendModificationHttpRequest(getUser(userStorageKey),"POST",  usersApiUrl, userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, userStorageKey);
}
