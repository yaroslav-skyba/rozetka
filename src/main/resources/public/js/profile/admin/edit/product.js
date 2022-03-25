const productStorageKey = productStorageKeyPrefix + editStorageKeySuffix;

onload = function () {
    redirectUnready(productStorageKey);

    const uuid = JSON.parse(localStorage.getItem(productStorageKey))[productUuidDtoKey];
    sendProductModificationHttpRequest("edit a product", "Edit", productStorageKey, uuid, "PUT",
                                       productsApiUrl + "/" + uuid);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveProductModificationHttpRequest(productStorageKey);
}
