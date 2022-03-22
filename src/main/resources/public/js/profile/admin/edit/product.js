const storageKey = productStorageKeyPrefix + editStorageKeyPrefix;

onload = function () {
    redirectUnauthorized();
    redirectUnready(storageKey);

    const uuid = JSON.parse(localStorage.getItem(storageKey))[productUuidDtoKey];
    configProductModificationPage("edit a product", submitInnerHtmlEdit, storageKey, uuid);

    submit.onclick = function () {
        sendModificationRequestIfBodyNotNull("PUT", createNullableProduct(uuid), productsApiUrl + "/" + uuid, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationResponse(200, storageKey);
}
