onload = function () {
    const userToEdit = localStorage.getItem(userToEditStorageKey);

    if (!userToEdit) {
        location.href = "/profile/admin/admin.html";
    }

    const userToEditParsed = JSON.parse(userToEdit);

    localStorage.setItem(editStorageKeyPrefix + document.getElementById("login").id, userToEditParsed[userLoginDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("email").id, userToEditParsed[userEmailDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("firstName").id, userToEditParsed[userFirstNameDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("secondName").id, userToEditParsed[userLastNameDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("birthday").id, userToEditParsed[userBirthdayDtoKey]);

    setAdminModification("Edit an account", "Edit", editStorageKeyPrefix);

    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (key === userToEditParsed[userRoleUuidDtoKey]) {
            localStorage.setItem(editStorageKeyPrefix + document.getElementById("roleValue").id, value);
        }
    }

    document.getElementById("submit").onclick = function () {
        if (!areInputsValid()) {
            return;
        }

        const body = createBody();
        const password = document.getElementById("password");

        if (password) {
            body[userPasswordDtoKey] = password.value;
        } else {
            body[userPasswordDtoKey] = null;
        }

        xmlHttpRequest.open("PUT", usersApiUrl + "/" + userToEditParsed[userUuidDtoKey]);
        xmlHttpRequest.setRequestHeader("Content-Type", userMediaType);
        xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
        xmlHttpRequest.send(JSON.stringify(body));
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(201, creationStorageKeyPrefix, "A user has been successfully created",
        "Some errors occurred while creating a user");
}