onload = function () {
    redirectUnauthorizedModification();
    setRoleModificationForm("create a role", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        sendCreationRequest(createRoleModificationRequestBody(null), rolesApiUrl, roleContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, roleStorageKeyPrefix + creationStorageKeyPrefix);
}
