const storageKey = roleStorageKeyPrefix + editStorageKeySuffix;

onload = function () {
    redirectUnauthorized();
    redirectUnready(storageKey);

    const uuid = JSON.parse(localStorage.getItem(storageKey))[roleUuidDtoKey];
    configRoleModificationPage("edit a role", innerHtmlEditSubmit, storageKey, uuid);

    submit.onclick = function () {
        sendModificationRequestIfBodyNotNull("PUT", createNullableRole(uuid), rolesApiUrl + "/" + uuid, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, storageKey);
}
