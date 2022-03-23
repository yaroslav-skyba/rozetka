const storageKey = productStorageKeyPrefix + creationStorageKeySuffix;

onload = function () {
    redirectUnauthorized();
    configProductModificationPage("create a product", innerHtmlCreationSubmit, storageKey, null);

    submit.onclick = function () {
        sendModificationRequestIfBodyNotNull("POST", createNullableProduct(null), productsApiUrl, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, storageKey);
}
