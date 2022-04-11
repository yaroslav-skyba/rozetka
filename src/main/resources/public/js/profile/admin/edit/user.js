onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, userEditStorageKey);
    setUserAdminPage("edit a user", innerHtmlEditSubmit, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200);
}
