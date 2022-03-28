onload = function () {
    redirectUnauthorized(adminRoleName);
    redirectUnready(roleEditStorageKey);

    const uuid = JSON.parse(localStorage.getItem(roleEditStorageKey))[roleUuidDtoKey];
    configRoleModificationPage("edit a role", innerHtmlEditSubmit, roleEditStorageKey, uuid);

    submit.onclick = function () {
        sendModificationHttpRequest(getRole(roleEditStorageKey),"PUT", rolesApiUrl + "/" + uuid, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, roleEditStorageKey);
}
