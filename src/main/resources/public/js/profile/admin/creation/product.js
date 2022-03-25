const productStorageKey = productStorageKeyPrefix + creationStorageKeySuffix;

onload = function () {
    sendProductModificationHttpRequest("create a product", "Create", productStorageKey, null, "POST",
                                       productsApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveProductModificationHttpRequest(productStorageKey);
}
