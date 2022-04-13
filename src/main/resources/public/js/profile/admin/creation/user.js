onload = function () {
    setUserAdminPage(userStorageKeyPart + creationStorageKeyPart, "create a user", innerHtmlCreationSubmit, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201);
}
