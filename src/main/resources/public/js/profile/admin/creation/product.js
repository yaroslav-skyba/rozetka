onload = function () {
    redirectUnauthorizedModification();
    setProductModificationForm("create a product", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        sendCreationRequest(createProductModificationRequestBody(null), productsApiUrl, productStorageKeyPrefix);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, productStorageKeyPrefix + creationStorageKeyPrefix);
}
