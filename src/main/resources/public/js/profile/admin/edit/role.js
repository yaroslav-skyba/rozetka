const storageKey = roleStorageKeyPrefix + editStorageKeyPrefix;

onload = function () {
    redirectUnauthorized();
    redirectUnready(storageKey);

    const uuid = JSON.parse(localStorage.getItem(storageKey))[roleUuidDtoKey];
    configRoleModificationPage("edit a role", "Edit", storageKey, uuid);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("PUT", createNullableRole(uuid), rolesApiUrl + "/" + uuid, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(200, storageKey);
}
