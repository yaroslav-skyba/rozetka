const productStorageKey = productStorageKeyPrefix + creationStorageKeySuffix;

onload = function () {
    redirectUnauthorized();
    configProductModificationPage("create a product", innerHtmlCreationSubmit, productStorageKey, null);

    submit.onclick = function () {
        sendModificationHttpRequest(getProduct(productStorageKey),"POST", productsApiUrl, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, productStorageKey);
}
