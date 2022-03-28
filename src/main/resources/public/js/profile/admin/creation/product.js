const productCreationStorageKey = productStorageKeyPart + creationStorageKeyPart;
const productImgCreationStorageKey = productStorageKeyPart + productImgStorageKeyPart + creationStorageKeyPart;

onload = function () {
    sendProductModificationHttpRequest("create a product", innerHtmlCreationSubmit, productCreationStorageKey, null,
                                       productImgCreationStorageKey, "POST", productsApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveProductModificationHttpRequest(201, productImgCreationStorageKey, productCreationStorageKey);
}
