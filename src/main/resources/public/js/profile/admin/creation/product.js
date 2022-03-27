const productStorageKey = productStorageKeyPart + creationStorageKeyPart;

onload = function () {
    sendProductModificationHttpRequest("create a product", innerHtmlCreationSubmit, productStorageKey, null,
                                       productStorageKeyPart + productImgStorageKeyPart + creationStorageKeyPart,
                                       "POST", productsApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveProductModificationHttpRequest(productStorageKey);
}
