onload = function () {
    redirectUnready(productEditStorageKey);
    setProductModificationPage("edit a product", innerHtmlEditSubmit, productEditStorageKey, productsApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, productEditStorageKey);
}
