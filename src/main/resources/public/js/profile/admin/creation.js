onload = function () {
    setUserModification();
    setRole();
    setAdminModification("Create an account", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        const password = document.getElementById("password");

        password.required = true;
        document.getElementById("passwordConformation").required = true;

        sendModificationRequest(null, password.value, "POST", usersApiUrl);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(201, creationStorageKeyPrefix, "A user has been successfully created");
}