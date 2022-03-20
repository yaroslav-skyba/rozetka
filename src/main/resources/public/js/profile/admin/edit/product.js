// noinspection DuplicatedCode

const storageKeyPrefix = productStorageKeyPrefix + editStorageKeyPrefix;

onload = function () {
    redirectUnauthorizedModification();
    setProductModificationForm("edit a product", "Edit", editStorageKeyPrefix);

    const productToEditParsed = JSON.parse(localStorage.getItem(productToEditStorageKey));

    if (!productToEditParsed[productDescriptionDtoKey]) {
        productToEditParsed[productDescriptionDtoKey] = "";
    }

    localStorage.setItem(storageKeyPrefix + name.id, productToEditParsed[productNameDtoKey]);
    localStorage.setItem(storageKeyPrefix + quantity.id, productToEditParsed[productQuantityDtoKey]);
    localStorage.setItem(storageKeyPrefix + price.id, productToEditParsed[productPriceDtoKey]);
    localStorage.setItem(storageKeyPrefix + discount.id, productToEditParsed[productDiscountDtoKey]);
    localStorage.setItem(storageKeyPrefix + description.id, productToEditParsed[productDescriptionDtoKey]);

    document.getElementById("submit").onclick = function () {
        sendModificationRequestIfBodyNotNull("PUT", createProductModificationRequestBody(productToEditParsed[productUuidDtoKey]),
                                             productsApiUrl + "/" + productToEditParsed[productUuidDtoKey], productContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setCreationXmlHttpRequest(200, storageKeyPrefix, productToEditStorageKey);
}
