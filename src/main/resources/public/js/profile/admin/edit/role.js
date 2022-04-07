onload = function () {
    redirectUnready(roleEditStorageKey);
    setRoleModificationPage(
        "edit a role", innerHtmlEditSubmit, roleEditStorageKey, JSON.parse(localStorage.getItem(roleEditStorageKey))[roleUuidDtoKey]
    );
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, roleEditStorageKey);
}
