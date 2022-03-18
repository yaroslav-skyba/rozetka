onload = function () {
    redirectUnauthorized();
    setAdminModificationForm();

    document.getElementById("submit").onclick = function () {
        const password = document.getElementById("password");

        password.required = true;
        document.getElementById("passwordConformation").required = true;

        const body = createModificationRequestBody(null, password.value);

        if (!body) {
            return;
        }

        sendModificationRequest("POST", usersApiUrl, appendRoleToBody(body));
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(201, creationStorageKeyPrefix);
}
