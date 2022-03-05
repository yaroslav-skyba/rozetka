onload = function () {
    setAdminModification("Create an account", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        const password = document.getElementById("password");

        password.required = true;
        document.getElementById("passwordConformation").required = true;

        if (!areInputsValid()) {
            return;
        }

        const body = createBody();
        body[userPasswordDtoKey] = password.value;

        xmlHttpRequest.open("POST", usersApiUrl);
        xmlHttpRequest.setRequestHeader("Content-Type", userMediaType);
        xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
        xmlHttpRequest.send(JSON.stringify(body));
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(201, creationStorageKeyPrefix, "A user has been successfully created",
        "Some errors occurred while creating a user");
}