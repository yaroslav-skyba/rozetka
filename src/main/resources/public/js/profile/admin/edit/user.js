const storageKey = userStorageKeyPrefix + editStorageKeyPrefix;

onload = function () {
    redirectUnauthorized();
    redirectUnready(storageKey);
    redirectWithoutRoles();

    configUserAdminModificationPage("edit a user", innerHtmlEditSubmit, storageKey, null);

    submit.onclick = function () {
        sendModificationRequest("PUT", createUser(storageKey),
                                usersApiUrl + "/" + JSON.parse(localStorage.getItem(storageKey))[userUuidDtoKey], userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(200, storageKey);
}
