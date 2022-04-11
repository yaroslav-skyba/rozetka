onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, userStorageKeyPart + creationStorageKeyPart);
    setUserAdminPage("create a user", innerHtmlCreationSubmit, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201);
}
