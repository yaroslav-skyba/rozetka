onload = function () {
    redirectUnauthorizedModification();
    setUserModificationForm("create a user", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        const password = document.getElementById("password");

        password.required = true;
        document.getElementById("passwordConformation").required = true;

        sendCreationRequest(appendRoleToBody(createUserModificationRequestBody(null, password.value)), usersApiUrl, userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, userStorageKeyPrefix + creationStorageKeyPrefix);
}
