onload = function () {
    localStorage.setItem(modificationStorageKeyKey, roleEditStorageKey);
    setRolePage("edit a role", innerHtmlEditSubmit, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200);
}
