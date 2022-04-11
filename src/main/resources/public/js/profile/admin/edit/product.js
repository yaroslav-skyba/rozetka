onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, productEditStorageKey);
    setProductModificationPage("edit a product", innerHtmlEditSubmit, "PUT");
}

xmlHttpRequest.onreadystatechange = function () {
    receiveModificationHttpResponse(200);
}
