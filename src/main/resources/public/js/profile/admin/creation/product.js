const storageKey = productStorageKeyPrefix + creationStorageKeyPrefix;

onload = function () {
    redirectUnauthorized();
    configProductModificationPage("create a product", innerHtmlCreationSubmit, storageKey, null);

    submit.onclick = function () {
        sendModificationRequestIfBodyNotNull("POST", createNullableProduct(null), productsApiUrl, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(201, storageKey);
}
