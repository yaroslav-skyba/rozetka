//noinspection DuplicatedCode

const productImgUploadFailMessage = "Please, upload a .png image";

const productContentType = contentTypeRoot + "product" + contentTypeSuffix;

const productImgStorageKeyPart = "Img";

let productName;
let productQuantity;
let productPrice;
let productDiscount;
let productDescription;
let productImg;

let productModificationSuccessMessage;

let productImgApiUrl;

function setProductImg(img) {
    document.getElementById("productImg").src = img;
    document.getElementById("productImgCard").hidden = false;
}

function sendProductModificationHttpRequest(headlineInnerHtml, submitInnerHtml, productStorageKey, uuid, productImgStorageKey,
                                            modificationHttpMethod, modificationUrl) {
    redirectUnauthorized(adminRoleName);

    setNavigation("../../../", "../../", "../");
    setContainer(`
        <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
        
        <div id="form">
            <div class="form-outline mb-4">
                <input id="name" class="form-control form-control-lg" required/>
                <label for="name">A name</label>
                <div class="invalid-feedback">Please, type a name</div>
            </div>
            
            <div class="form-outline mb-4">
                <input id="quantity" class="form-control form-control-lg" required/>
                <label for="quantity">A quantity</label>
                <div class="invalid-feedback">Please, type a quantity</div>
            </div>
            
            <div class="form-outline mb-4">
                <input id="price" class="form-control form-control-lg" required/>
                <label for="price">A price</label>
                <div class="invalid-feedback">Please, type a price</div>
            </div>
            
            <div class="form-outline mb-4">
                <input id="discount" class="form-control form-control-lg" required/>
                <label for="discount">A discount</label>
                <div class="invalid-feedback">Please, type a discount</div>
            </div>
            
            <div class="mb-4">
                <input id="description" class="form-control form-control-lg"/>
                <label for="description">A description</label>
            </div>
            
            <div id="productImgCard" class="card mb-4" style="border-radius: 15px" hidden>
                <img src="" alt="product" id="productImg" class="card-img" style="border-radius: 15px">
            </div>
            
            <div class="mb-4">
                <input type="file" accept=".png" id="img" class="form-control form-control-lg"/>
                <label for="img">A .png image</label>
            </div>
            
            <div class="d-flex justify-content-center">
                <button class="btn btn-dark btn-outline-success" id="submit"></button>
            </div>
        </div>
        
        <div id="alert" class="mt-3"></div>
    `);

    productName = document.getElementById("productName");
    productQuantity = document.getElementById("productQuantity");
    productPrice = document.getElementById("productPrice");
    productDiscount = document.getElementById("productDiscount");
    productDescription = document.getElementById("productDescription");
    productImg = document.getElementById("productImg");

    configModificationPage(headlineInnerHtml, submitInnerHtml);

    const product = JSON.parse(localStorage.getItem(productStorageKey));

    if (product) {
        productName.value = product[productNameDtoKey];
        productQuantity.value = product[productQuantityDtoKey];
        productPrice.value = product[productPriceDtoKey];
        productDiscount.value = product[productDiscountDtoKey];
        productDescription.value = product[productDescriptionDtoKey];
    }

    setFormControlElementOnchange(productStorageKey, function () {
        const product = {};
        product[productUuidDtoKey] = uuid;
        product[productNameDtoKey] = productName.value;
        product[productQuantityDtoKey] = productQuantity.value;
        product[productPriceDtoKey] = productPrice.value;
        product[productDiscountDtoKey] = productDiscount.value;
        product[productDescriptionDtoKey] = productDescription.value;

        return product;
    });

    const productImgValue = localStorage.getItem(productImgStorageKey);

    if (productImgValue) {
        setProductImg(productImgValue);
    }

    productImg.onchange = function () {
        if (productImg.value.split(".").pop() !== "png") {
            alert("danger", productImgUploadFailMessage);
            return;
        }

        const fileReader = new FileReader();
        fileReader.readAsDataURL(productImg.files[0]);
        fileReader.onload = function() {
            const img = fileReader.result;

            localStorage.setItem(productImgStorageKey, img.toString());
            setProductImg(img);
        };
    }

    submit.onclick = function () {
        for (const formOutlineElement of document.getElementsByClassName("form-outline")) {
            const formControlElement = formOutlineElement.getElementsByClassName("form-control")[0];

            if (!areFormInputsValid(formControlElement, formOutlineElement)) {
                return null;
            }

            const minFieldValue = 0;

            if (Number(formControlElement.value) < 0) {
                alert("danger", "A field value should be " + minFieldValue + " or positive");
                return null;
            }
        }

        if (!localStorage.getItem(productImgStorageKey)) {
            alert("danger", productImgUploadFailMessage);
            return null;
        }

        if (!Number.isInteger(Number(productQuantity.value))) {
            alert("danger", "A productQuantity value should be an integer");
            return null;
        }

        const maxDiscountValue = 100;

        if (Number(productDiscount.value) > maxDiscountValue) {
            alert("danger", "A productDiscount value should be " + maxDiscountValue + " or less");
            return null;
        }

        sendModificationHttpRequest(JSON.parse(localStorage.getItem(productStorageKey)), modificationHttpMethod, modificationUrl,
                                    productContentType);
    }
}

function receiveProductModificationHttpRequest(productImgStorageKey, productStorageKey) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 201) {
            if (xmlHttpRequest.responseURL === productsApiUrl) {
                productModificationSuccessMessage = xmlHttpRequest.responseText;

                const cookies = xmlHttpRequest.getResponseHeader("Cookie").split(";");

                for (let i = 0; i < cookies.length; i++) {
                    const cookiePair = cookies[i].split("=");

                    if("uuid" === cookiePair[0].trim()) {
                        productImgApiUrl = productsApiUrl + "/" + cookiePair[1] + "/productImg";
                        break;
                    }
                }

                sendModificationHttpRequest(localStorage.getItem(productImgStorageKey),"POST", productImgApiUrl, "image/png");
            } else if (xmlHttpRequest.responseURL === productImgApiUrl) {
                localStorage.removeItem(productStorageKey);
                localStorage.removeItem(productImgStorageKey);

                alert("success", productModificationSuccessMessage);
            }
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}
