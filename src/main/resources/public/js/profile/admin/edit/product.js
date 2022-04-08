onload = function () {
    redirectUnready(productEditStorageKey);
    setProductModificationPage("edit a product", innerHtmlEditSubmit, productEditStorageKey, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200, productEditStorageKey);
}
