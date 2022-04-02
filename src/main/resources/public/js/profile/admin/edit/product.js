onload = function () {
    redirectUnready(productEditStorageKey);
    sendProductModificationHttpRequest(
        "edit a product", innerHtmlEditSubmit, productEditStorageKey,
        JSON.parse(localStorage.getItem(productEditStorageKey))[productUuidDtoKey], "PUT", productsApiUrl
    );
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, productEditStorageKey);
}
