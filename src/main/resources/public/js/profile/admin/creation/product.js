onload = function () {
    redirectUnauthorizedModification();
    setProductModificationForm("create a product", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        const body = createProductModificationRequestBody(null);

        if (body) {
            sendModificationRequest("POST", productsApiUrl, body, productContentType);
        }
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(201, productStorageKeyPrefix + creationStorageKeyPrefix);
}
