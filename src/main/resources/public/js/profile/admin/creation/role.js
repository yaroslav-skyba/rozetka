const storageKey = roleStorageKeyPrefix + creationStorageKeySuffix;

onload = function () {
    redirectUnauthorized();
    configRoleModificationPage("create a role", innerHtmlCreationSubmit, storageKey, null);

    submit.onclick = function () {
        sendModificationRequestIfBodyNotNull("POST", createNullableRole(null), rolesApiUrl, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, storageKey);
}
