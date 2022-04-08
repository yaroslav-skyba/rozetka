onload = function () {
    redirectUnready(roleEditStorageKey);
    setRolePage("edit a role", innerHtmlEditSubmit, roleEditStorageKey, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, roleEditStorageKey);
}
