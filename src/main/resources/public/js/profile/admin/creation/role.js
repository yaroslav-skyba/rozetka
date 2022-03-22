const storageKey = roleStorageKeyPrefix + creationStorageKeyPrefix;

onload = function () {
    redirectUnauthorized();
    configRoleModificationPage("create a role", "Create", storageKey, null);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("POST", createNullableRole(null), rolesApiUrl, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(201, storageKey);
}
