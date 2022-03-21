const storageKey = roleStorageKeyPrefix + creationStorageKeyPrefix;

onload = function () {
    redirectUnauthorized();
    setRoleModificationForm("create a role", "Create", storageKey, null);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("POST", createNullableRole(null), rolesApiUrl, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, storageKey);
}
