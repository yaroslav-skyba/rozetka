onload = function () {
    redirectUnready(userEditStorageKey);
    setUserAdminPage(userEditStorageKey, "edit a user", innerHtmlEditSubmit, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, userEditStorageKey);
}
