const storageKey = userStorageKeyPrefix + editStorageKeyPrefix;

onload = function () {
    redirectUnauthorized();
    redirectUnready(storageKey);
    redirectWithoutRoles();

    configUserAdminModificationPage("edit a user", submitInnerHtmlEdit, storageKey, null);

    submit.onclick = function () {
        const userToEditParsed = JSON.parse(localStorage.getItem(storageKey));
        sendModificationRequestIfBodyNotNull("PUT", appendRoleUuidToUser(createUserToEdit(userToEditParsed)),
                                             usersApiUrl + "/" + userToEditParsed[userUuidDtoKey], userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(200, storageKey);
}
