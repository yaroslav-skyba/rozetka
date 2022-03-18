onload = function () {
    redirectUnauthorized();
    setAdminModificationForm();

    const userToEditParsed = JSON.parse(localStorage.getItem(userToEditStorageKey));

    setEditStorageItems(userToEditParsed);
    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (key === userToEditParsed[userRoleUuidDtoKey]) {
            localStorage.setItem(editStorageKeyPrefix + document.getElementById("roleValue").id, value.toString());
            break;
        }
    }

    document.getElementById("submit").onclick = function () {
        localStorage.removeItem(userToEditStorageKey);
        sendModificationRequest("PUT", usersApiUrl + "/" + userToEditParsed[userUuidDtoKey],
                                appendRoleToBody(createEditRequestBody(userToEditParsed)));
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(200, editStorageKeyPrefix);
}
