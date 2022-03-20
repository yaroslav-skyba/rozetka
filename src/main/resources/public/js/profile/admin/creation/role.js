const storageKey = roleStorageKeyPrefix + creationStorageKeyPrefix;

onload = function () {
    redirectUnauthorizedUser();
    setRoleModificationForm("create a role", "Create", storageKey, null);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("POST", createRoleModificationRequestBody(null), rolesApiUrl, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, storageKey);
}
