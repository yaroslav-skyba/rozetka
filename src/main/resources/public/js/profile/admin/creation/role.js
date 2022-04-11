onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, roleStorageKeyPart + creationStorageKeyPart);
    setRolePage("create a role", innerHtmlCreationSubmit, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201);
}
