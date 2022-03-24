const userStorageKey = userStorageKeyPrefix + editStorageKeySuffix;

onload = function () {
    redirectUnauthorized(adminRoleName);
    redirectUnready(userStorageKey);
    redirectWithoutRoles();

    const uuid = JSON.parse(localStorage.getItem(userStorageKey))[userUuidDtoKey];
    configUserAdminModificationPage(userStorageKey, "edit a user", innerHtmlEditSubmit, uuid);

    submit.onclick = function () {
        sendModificationHttpRequest(getUser(userStorageKey),"PUT", usersApiUrl + "/" + uuid, userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, userStorageKey);
}
