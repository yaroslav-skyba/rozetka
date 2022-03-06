onload = function () {
    const userToEdit = localStorage.getItem(userToEditStorageKey);

    if (!userToEdit) {
        location.href = "/profile/admin/admin.html";
    }

    setUserModification();
    setRole();

    const userToEditParsed = JSON.parse(userToEdit);

    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (key === userToEditParsed[userRoleUuidDtoKey]) {
            localStorage.setItem(editStorageKeyPrefix + document.getElementById("roleValue").id, value.toString());
        }
    }

    localStorage.setItem(editStorageKeyPrefix + document.getElementById("login").id, userToEditParsed[userLoginDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("email").id, userToEditParsed[userEmailDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("firstName").id, userToEditParsed[userFirstNameDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("secondName").id, userToEditParsed[userLastNameDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("birthday").id, userToEditParsed[userBirthdayDtoKey]);

    setAdminModification("Edit an account", "Save", editStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        let passwordValue = document.getElementById("password").value;

        if (!passwordValue) {
            passwordValue = null;
        }

        localStorage.removeItem(userToEditStorageKey);
        sendModificationRequest(passwordValue, "PUT", usersApiUrl + "/" + userToEditParsed[userUuidDtoKey]);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(200, editStorageKeyPrefix, "A user has been successfully edited");
}