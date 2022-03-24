const productStorageKey = productStorageKeyPrefix + creationStorageKeySuffix;

onload = function () {
    redirectUnauthorized(adminRoleName);
    configProductModificationPage("create a product", innerHtmlCreationSubmit, productStorageKey, null);

    submit.onclick = function () {
        sendModificationHttpRequest(getProduct(productStorageKey),"POST", productsApiUrl, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(201, productStorageKey);
}
