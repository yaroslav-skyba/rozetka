const productCreationStorageKey = productStorageKeyPart + creationStorageKeyPart;

onload = function () {
    setProductModificationPage("create a product", innerHtmlCreationSubmit, productCreationStorageKey, "POST");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, productCreationStorageKey);
}
