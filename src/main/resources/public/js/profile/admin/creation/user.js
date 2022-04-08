const userCreationStorageKey = userStorageKeyPart + creationStorageKeyPart;

onload = function () {
    setUserAdminPage(userCreationStorageKey, "create a user", innerHtmlCreationSubmit, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, userCreationStorageKey);
}
