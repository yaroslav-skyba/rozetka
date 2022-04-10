onload = function () {
    localStorage.setItem(modificationStorageKeyKey, userEditStorageKey);
    setUserAdminPage("edit a user", innerHtmlEditSubmit, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200);
}
