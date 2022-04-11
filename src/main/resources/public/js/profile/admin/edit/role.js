onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, roleEditStorageKey);
    setRolePage("edit a role", innerHtmlEditSubmit, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200);
}
