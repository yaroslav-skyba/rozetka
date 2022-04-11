onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, productStorageKeyPart + creationStorageKeyPart);
    setProductModificationPage("create a product", innerHtmlCreationSubmit, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201);
}
