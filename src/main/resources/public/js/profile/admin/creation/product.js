const storageKey = productStorageKeyPrefix + creationStorageKeyPrefix;

onload = function () {
    redirectUnauthorizedUser();
    setProductModificationForm("create a product", "Create", storageKey, null);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("POST", createProductModificationRequestBody(null), productsApiUrl, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, storageKey);
}
