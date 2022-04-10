onload = function () {
    localStorage.setItem(modificationStorageKeyKey, userStorageKeyPart + creationStorageKeyPart);
    setUserAdminPage("create a user", innerHtmlCreationSubmit, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201);
}
