onload = function () {
    redirectUnauthorized(adminRoleName);
    redirectUnready(roleEditStorageKey);

    configRoleModificationPage(
        "edit a role", innerHtmlEditSubmit, roleEditStorageKey, JSON.parse(localStorage.getItem(roleEditStorageKey))[roleUuidDtoKey]
    );

    submit.onclick = function () {
        sendModificationHttpRequest(getRole(roleEditStorageKey),"PUT", rolesApiUrl, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, roleEditStorageKey);
}
