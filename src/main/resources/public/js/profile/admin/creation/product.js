const productCreationStorageKey = productStorageKeyPart + creationStorageKeyPart;

onload = function () {
    sendProductModificationHttpRequest(
        "create a product", innerHtmlCreationSubmit, productCreationStorageKey, null, "POST", productsApiUrl
    );
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, productCreationStorageKey);
}
