//noinspection DuplicatedCode

const productUuidDtoKey = "uuid";
const productNameDtoKey = "name";
const productQuantityDtoKey = "quantity";
const productPriceDtoKey = "price";
const productDiscountDtoKey = "discount";
const productDescriptionDtoKey = "description";

const productStorageKeyPrefix = "product_";

const productsApiUrl = authorityApi + "products";

const productContentType = contentTypeRoot + "product" + contentTypeSuffix;

const productToEditStorageKey = "productToEdit";

let name;
let quantity;
let price;
let discount;
let description;

function setProductModificationForm(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    redirectUnauthorizedModification();
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

    storageKeyPrefix = productStorageKeyPrefix + storageKeyPrefix;
    configModificationPage(headlineInnerHtml, submitInnerHtml, storageKeyPrefix);

    name.value = localStorage.getItem(storageKeyPrefix + name.id);
    quantity.value = localStorage.getItem(storageKeyPrefix + quantity.id);
    price.value = localStorage.getItem(storageKeyPrefix + price.id);
    discount.value = localStorage.getItem(storageKeyPrefix + discount.id);
    description.value = localStorage.getItem(storageKeyPrefix + description.id);
}

function createProductModificationRequestBody(uuid) {
    for (const formOutlineElement of document.getElementsByClassName("form-outline")) {
        const formControlElement = formOutlineElement.getElementsByClassName("form-control")[0];

        if (!areInputsValid(formControlElement, formOutlineElement)) {
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

    const body = {};
    body[productUuidDtoKey] = uuid;
    body[productNameDtoKey] = name.value;
    body[productQuantityDtoKey] = quantity.value;
    body[productPriceDtoKey] = price.value;
    body[productDiscountDtoKey] = discount.value;
    body[productDescriptionDtoKey] = description.value;

    return body;
}
