let productEditSuccessMessage;

onload = function () {
    redirectUnready(productEditStorageKey);

    if (!localStorage.getItem(productImgEditStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }

    sendProductModificationHttpRequest(
        "edit a product", innerHtmlEditSubmit, productEditStorageKey,
        JSON.parse(localStorage.getItem(productEditStorageKey))[productUuidDtoKey], productImgEditStorageKey, "PUT", productsApiUrl
    );
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            productEditSuccessMessage = xmlHttpRequest.responseText;
            sendModificationHttpRequest(
                localStorage.getItem(productImgEditStorageKey), "POST",
                productsApiUrl + "/" + JSON.parse(localStorage.getItem(productEditStorageKey))[productUuidDtoKey] + productImgApiUrlPart,
                "image/png"
            );
        } else if (xmlHttpRequest.status === 201) {
            succeedModificationProductImg(productEditStorageKey, productImgEditStorageKey, productEditSuccessMessage);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}
