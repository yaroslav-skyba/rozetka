const storageKey = productStorageKeyPrefix + editStorageKeySuffix;

onload = function () {
    redirectUnauthorized();
    redirectUnready(storageKey);

    const uuid = JSON.parse(localStorage.getItem(storageKey))[productUuidDtoKey];
    configProductModificationPage("edit a product", innerHtmlEditSubmit, storageKey, uuid);

    submit.onclick = function () {
        sendModificationRequestIfBodyNotNull("PUT", getProduct(), productsApiUrl + "/" + uuid, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, storageKey);
}
