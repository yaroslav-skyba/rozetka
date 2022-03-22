onload = function () {
    redirectUnauthorized();
    redirectUnauthorized();
    setUserAdminModificationForm("Edit", editStorageKeyPrefix, "edit a user");

    const userToEditParsed = JSON.parse(localStorage.getItem(userToEditStorageKey));

    setUserEditStorageItems(userToEditParsed);
    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (key === userToEditParsed[userRoleUuidDtoKey]) {
            localStorage.setItem(editStorageKeyPrefix + document.getElementById("roleValue").id, value.toString());
            break;
        }
    }

    document.getElementById("submit").onclick = function () {
        localStorage.removeItem(userToEditStorageKey);
        sendModificationRequestIfBodyNotNull("PUT", appendRoleUuidToUser(createUserToEdit(userToEditParsed)),
                                             usersApiUrl + "/" + userToEditParsed[userUuidDtoKey], userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(200, editStorageKeyPrefix);
}
