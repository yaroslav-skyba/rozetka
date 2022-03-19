onload = function () {
    redirectUnauthorizedModification();
    setUserModificationForm();

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
        sendModificationRequest("PUT", usersApiUrl + "/" + userToEditParsed[userUuidDtoKey],
                                appendRoleToBody(createUserEditRequestBody(userToEditParsed)), userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(200, editStorageKeyPrefix);
}
