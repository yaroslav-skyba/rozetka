const roleStorageKey = roleStorageKeyPrefix + editStorageKeySuffix;

onload = function () {
    redirectUnauthorized(adminRoleName);;
    redirectUnready(roleStorageKey);

    const uuid = JSON.parse(localStorage.getItem(roleStorageKey))[roleUuidDtoKey];
    configRoleModificationPage("edit a role", innerHtmlEditSubmit, roleStorageKey, uuid);

    submit.onclick = function () {
        sendModificationHttpRequest(getRole(roleStorageKey),"PUT", rolesApiUrl + "/" + uuid, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, roleStorageKey);
}
