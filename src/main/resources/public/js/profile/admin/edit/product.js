const productStorageKey = productStorageKeyPrefix + editStorageKeySuffix;

onload = function () {
    redirectUnauthorized(adminRoleName);;
    redirectUnready(productStorageKey);

    const uuid = JSON.parse(localStorage.getItem(productStorageKey))[productUuidDtoKey];
    configProductModificationPage("edit a product", innerHtmlEditSubmit, productStorageKey, uuid);

    submit.onclick = function () {
        sendModificationHttpRequest(getProduct(productStorageKey),"PUT", productsApiUrl + "/" + uuid, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, productStorageKey);
}
