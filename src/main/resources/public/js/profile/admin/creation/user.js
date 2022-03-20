onload = function () {
    redirectUnauthorizedUser();
    redirectUnauthorizedUser();
    setUserModificationForm("create a user", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        const password = document.getElementById("password");

        password.required = true;
        document.getElementById("passwordConformation").required = true;

        sendModificationRequestIfBodyNotNull("POST", appendRoleToBody(createUserModificationRequestBody(null, password.value)),
                                             usersApiUrl, userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, userStorageKeyPrefix + creationStorageKeyPrefix);
}
