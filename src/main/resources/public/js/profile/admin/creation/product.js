onload = function () {
    redirectUnauthorizedModification();
    setProductModificationForm("create a product", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("POST", createProductModificationRequestBody(null), productsApiUrl, productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, productStorageKeyPrefix + creationStorageKeyPrefix);
}
