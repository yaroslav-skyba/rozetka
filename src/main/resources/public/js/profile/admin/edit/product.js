// noinspection DuplicatedCode

const storageKey = productStorageKeyPrefix + editStorageKeyPrefix;

onload = function () {
    redirectUnauthorizedModification(storageKey);

    const uuid = JSON.parse(localStorage.getItem(storageKey))[productUuidDtoKey];
    setProductModificationForm("edit a product", "Edit", uuid, editStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("PUT", createProductModificationRequestBody(uuid), productsApiUrl + "/" + uuid,
                                             productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(200, storageKey);
}
