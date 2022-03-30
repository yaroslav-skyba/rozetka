onload = function () {
    redirectUnauthorized(adminRoleName);
    redirectUnready(userEditStorageKey);
    redirectWithoutRoles();

    configUserAdminModificationPage(
        userEditStorageKey, "edit a user", innerHtmlEditSubmit, JSON.parse(localStorage.getItem(userEditStorageKey))[userUuidDtoKey]
    );

    submit.onclick = function () {
        sendModificationHttpRequest(getUser(userEditStorageKey),"PUT", usersApiUrl, userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, userEditStorageKey);
}
