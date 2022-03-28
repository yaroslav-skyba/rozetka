onload = function () {
    redirectUnready(productEditStorageKey);

    const uuid = JSON.parse(localStorage.getItem(productEditStorageKey))[productUuidDtoKey];
    sendProductModificationHttpRequest("edit a product", innerHtmlEditSubmit, productEditStorageKey, uuid, productImgEditStorageKey,
                                       "PUT", productsApiUrl + "/" + uuid);
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            productModificationSuccessMessage = xmlHttpRequest.responseText;
            sendModificationHttpRequest(
                localStorage.getItem(productImgEditStorageKey), "POST",
                productsApiUrl + "/" + JSON.parse(localStorage.getItem(productEditStorageKey))[productUuidDtoKey] + "/img", "image/png"
            );
        } else if (xmlHttpRequest.status === 201) {
            localStorage.removeItem(productEditStorageKey);
            localStorage.removeItem(productImgEditStorageKey);

            alert("success", productModificationSuccessMessage);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}
