const productCreationStorageKey = productStorageKeyPart + creationStorageKeyPart;

onload = function () {
    setProductModificationHttpRequest("create a product", innerHtmlCreationSubmit, productCreationStorageKey, function () {
        return createProduct(null, null);
    }, null, "POST", productsApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, productCreationStorageKey);
}
