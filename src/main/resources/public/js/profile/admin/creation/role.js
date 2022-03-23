const roleStorageKey = roleStorageKeyPrefix + creationStorageKeySuffix;

onload = function () {
    redirectUnauthorized();
    configRoleModificationPage("create a role", innerHtmlCreationSubmit, roleStorageKey, null);

    submit.onclick = function () {
        sendModificationHttpRequest(getRole(roleStorageKey),"POST", rolesApiUrl, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, roleStorageKey);
}
