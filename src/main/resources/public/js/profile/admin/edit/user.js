onload = function () {
    redirectUnauthorized(adminRoleName);
    redirectUnready(userEditStorageKey);
    redirectWithoutRoles();

    const uuid = JSON.parse(localStorage.getItem(userEditStorageKey))[userUuidDtoKey];
    configUserAdminModificationPage(userEditStorageKey, "edit a user", innerHtmlEditSubmit, uuid);

    submit.onclick = function () {
        sendModificationHttpRequest(getUser(userEditStorageKey),"PUT", usersApiUrl + "/" + uuid, userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, userEditStorageKey);
}
