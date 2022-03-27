const productStorageKey = productStorageKeyPart + editStorageKeySuffix;

onload = function () {
    redirectUnready(productStorageKey);

    const uuid = JSON.parse(localStorage.getItem(productStorageKey))[productUuidDtoKey];
    sendProductModificationHttpRequest("edit a product", innerHtmlEditSubmit, productStorageKey, uuid,
                                       productStorageKeyPart + productImgStorageKeyPart + editStorageKeySuffix,
                                       "PUT", productsApiUrl + "/" + uuid);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveProductModificationHttpRequest(productStorageKey);
}
