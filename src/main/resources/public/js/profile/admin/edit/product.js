onload = function () {
    redirectUnready(productEditStorageKey);
    setProductModificationHttpRequest("edit a product", innerHtmlEditSubmit, productEditStorageKey, function () {
        const product = JSON.parse(localStorage.getItem(productEditStorageKey));
        return createProduct(product[productUuidDtoKey], product[productImgDtoKey]);
    }, JSON.parse(localStorage.getItem(productEditStorageKey))[productUuidDtoKey], "PUT", productsApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, productEditStorageKey);
}
