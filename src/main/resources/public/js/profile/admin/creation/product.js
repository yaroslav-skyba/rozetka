onload = function () {
    localStorage.setItem(modificationStorageKeyKey, productStorageKeyPart + creationStorageKeyPart);
    setProductModificationPage("create a product", innerHtmlCreationSubmit, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201);
}
