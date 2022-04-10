onload = function () {
    localStorage.setItem(modificationStorageKeyKey, roleStorageKeyPart + creationStorageKeyPart);
    setRolePage("create a role", innerHtmlCreationSubmit, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201);
}
