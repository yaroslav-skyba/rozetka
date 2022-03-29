const productCreationStorageKey = productStorageKeyPart + creationStorageKeyPart;
const productImgCreationStorageKey = productStorageKeyPart + productImgStorageKeyPart + creationStorageKeyPart;

let productCreationSuccessMessage;

onload = function () {
    sendProductModificationHttpRequest("create a product", innerHtmlCreationSubmit, productCreationStorageKey, null,
                                       productImgCreationStorageKey, "POST", productsApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 201) {
            if (xmlHttpRequest.responseURL === productsApiUrl) {
                productCreationSuccessMessage = xmlHttpRequest.responseText;

                const cookies = xmlHttpRequest.getResponseHeader("Cookie").split(";");

                let productImgApiUrl;

                for (let i = 0; i < cookies.length; i++) {
                    const cookiePair = cookies[i].split("=");

                    if("uuid" === cookiePair[0].trim()) {
                        productImgApiUrl = productsApiUrl + "/" + cookiePair[1] + productImgApiUrlPart;
                        break;
                    }
                }

                sendModificationHttpRequest(localStorage.getItem(productImgCreationStorageKey), "POST", productImgApiUrl, "image/png");
            } else {
                succeedModificationProductImg(productCreationStorageKey, productImgCreationStorageKey, productCreationSuccessMessage);
            }
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}
