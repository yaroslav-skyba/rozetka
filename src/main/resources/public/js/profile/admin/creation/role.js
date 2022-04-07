const roleCreationStorageKey = roleStorageKeyPart + creationStorageKeyPart;

onload = function () {
    setRoleModificationPage("create a role", innerHtmlCreationSubmit, roleCreationStorageKey, "POST", rolesApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, roleCreationStorageKey);
}
