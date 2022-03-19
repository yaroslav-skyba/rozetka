onload = function () {
    redirectUnauthorizedModification();
    setRoleModificationForm("create a role", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        const body = createRoleModificationRequestBody(null);

        if (body) {
            sendModificationRequest("POST", rolesApiUrl, body, roleContentType);
        }
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, roleStorageKeyPrefix + creationStorageKeyPrefix);
}
