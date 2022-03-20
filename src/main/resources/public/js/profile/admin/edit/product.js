// noinspection DuplicatedCode

const storageKey = productStorageKeyPrefix + editStorageKeyPrefix;

onload = function () {
    redirectUnauthorizedUser();
    redirectUnreadyUser(storageKey);

    const uuid = JSON.parse(localStorage.getItem(storageKey))[productUuidDtoKey];
    setProductModificationForm("edit a product", "Edit", storageKey, uuid);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("PUT", createProductModificationRequestBody(uuid), productsApiUrl + "/" + uuid,
                                             productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setModificationXmlHttpRequest(200, storageKey);
}
