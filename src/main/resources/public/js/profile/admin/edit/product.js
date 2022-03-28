onload = function () {
    redirectUnready(productEditStorageKey);

    const uuid = JSON.parse(localStorage.getItem(productEditStorageKey))[productUuidDtoKey];
    sendProductModificationHttpRequest("edit a product", innerHtmlEditSubmit, productEditStorageKey, uuid, productImgEditStorageKey,
                                       "PUT", productsApiUrl + "/" + uuid);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveProductModificationHttpRequest(200, productImgEditStorageKey, productEditStorageKey);
}
