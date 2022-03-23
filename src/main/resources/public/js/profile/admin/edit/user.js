const userStorageKey = userStorageKeyPrefix + editStorageKeySuffix;

onload = function () {
    redirectUnauthorized();
    redirectUnready(userStorageKey);
    redirectWithoutRoles();

    configUserAdminModificationPage(userStorageKey, "edit a user", innerHtmlEditSubmit, null);

    submit.onclick = function () {
        sendModificationHttpRequest(getUser(userStorageKey),"PUT",
                                    usersApiUrl + "/" + JSON.parse(localStorage.getItem(userStorageKey))[userUuidDtoKey], userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, userStorageKey);
}
