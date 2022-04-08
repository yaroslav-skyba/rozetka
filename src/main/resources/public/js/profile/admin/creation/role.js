const roleCreationStorageKey = roleStorageKeyPart + creationStorageKeyPart;

onload = function () {
    setRolePage("create a role", innerHtmlCreationSubmit, roleCreationStorageKey, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, roleCreationStorageKey);
}
