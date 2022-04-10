onload = function () {
    localStorage.setItem(modificationStorageKeyKey, productEditStorageKey);
    setProductModificationPage("edit a product", innerHtmlEditSubmit, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200);
}
