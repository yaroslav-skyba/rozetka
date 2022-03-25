//noinspection DuplicatedCode

const productContentType = contentTypeRoot + "product" + contentTypeSuffix;

let name;
let quantity;
let price;
let discount;
let description;
let img;

let successResponseText;

function createProduct(uuid) {
    const product = {};
    product[productUuidDtoKey] = uuid;
    product[productNameDtoKey] = name.value;
    product[productQuantityDtoKey] = quantity.value;
    product[productPriceDtoKey] = price.value;
    product[productDiscountDtoKey] = discount.value;
    product[productDescriptionDtoKey] = description.value;
    product[productImgDtoKey] = img.value;

    return product;
}

function getProduct(productStorageKey) {
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

    if (!Number.isInteger(Number(quantity.value))) {
        alert("danger", "A quantity value should be an integer");
        return null;
    }

    const maxDiscountValue = 100;

    if (Number(discount.value) > maxDiscountValue) {
        alert("danger", "A discount value should be " + maxDiscountValue + " or less");
        return null;
    }

    return JSON.parse(localStorage.getItem(productStorageKey));
}

function configProductModificationPage(headlineInnerHtml, submitInnerHtml, productStorageKey, uuid) {
    setNavigation("../../../", "../../", "../");
    setContainer(`
        <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
        
        <div id="form">
            <div class="form-outline mb-4">
                <input id="name" class="form-control form-control-lg" required/>
                <label for="name">A name</label>
                <div class="invalid-feedback">Please type a name</div>
            </div>
            
            <div class="form-outline mb-4">
                <input id="quantity" class="form-control form-control-lg" required/>
                <label for="quantity">A quantity</label>
                <div class="invalid-feedback">Please type a quantity</div>
            </div>
            
            <div class="form-outline mb-4">
                <input id="price" class="form-control form-control-lg" required/>
                <label for="price">A price</label>
                <div class="invalid-feedback">Please type a price</div>
            </div>
            
            <div class="form-outline mb-4">
                <input id="discount" class="form-control form-control-lg" required/>
                <label for="discount">A discount</label>
                <div class="invalid-feedback">Please type a discount</div>
            </div>
            
            <div class="form-outline mb-4">
                <input id="description" class="form-control form-control-lg"/>
                <label for="description">A description</label>
            </div>
            
            <div class="form-outline mb-4">
                <input type="file" accept=".png" id="img" class="form-control form-control-lg" required/>
                <label for="img">An image</label>
                <div class="invalid-feedback">Please upload un image</div>
            </div>
            
            <div class="d-flex justify-content-center">
                <button class="btn btn-dark btn-outline-success" id="submit"></button>
            </div>
        </div>
        
        <div id="alert" class="mt-3"></div>
    `);

    name = document.getElementById("name");
    quantity = document.getElementById("quantity");
    price = document.getElementById("price");
    discount = document.getElementById("discount");
    description = document.getElementById("description");
    img = document.getElementById("img");

    configModificationPage(headlineInnerHtml, submitInnerHtml);

    const product = JSON.parse(localStorage.getItem(productStorageKey));

    if (product) {
        name.value = product[productNameDtoKey];
        quantity.value = product[productQuantityDtoKey];
        price.value = product[productPriceDtoKey];
        discount.value = product[productDiscountDtoKey];
        description.value = product[productDescriptionDtoKey];
        img.value = product[productImgDtoKey];
    }

    setFormControlElementOnchange(productStorageKey, function () {
        return createProduct(uuid);
    });
}

function sendProductModificationHttpRequest(headlineInnerHtml, submitInnerHtml, productStorageKey, uuid, httpMethod, url) {
    redirectUnauthorized(adminRoleName);
    configProductModificationPage(headlineInnerHtml, submitInnerHtml, productStorageKey, uuid);

    submit.onclick = function () {
        const product = getProduct(productStorageKey);

        delete product[productImgDtoKey];
        sendModificationHttpRequest(product, httpMethod, url, productContentType);
    }
}

function receiveProductModificationHttpRequest(productStorageKey) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 201) {
            if (xmlHttpRequest.responseURL === productApiUrl) {
                successResponseText = xmlHttpRequest.responseText;

                const product = JSON.parse(localStorage.getItem(productStorageKey));
                sendModificationHttpRequest(product[productImgDtoKey],"POST", productsApiUrl + "/" + product[productUuidDtoKey] + "/img",
                                           "image/png");
            } else {
                localStorage.removeItem(productStorageKey);
                alert("success", successResponseText);
            }
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}
