let productName;
let productQuantity;
let productPrice;
let productDiscount;
let productDescription;

let productImgCard
let productImgValue;
let productImgUploader;

function createProduct(uuid, img) {
    const product = {};
    product[productUuidDtoKey] = uuid;
    product[productNameDtoKey] = productName.value;
    product[productQuantityDtoKey] = productQuantity.value;
    product[productPriceDtoKey] = productPrice.value;
    product[productDiscountDtoKey] = productDiscount.value;
    product[productDescriptionDtoKey] = productDescription.value;
    product[productImgDtoKey] = img;

    return product;
}

function setProductModificationPage(headlineInnerHtml, submitInnerHtml, httpMethod) {
    redirectWithoutSpecificRole(adminRoleName);

    setNavigation("../../../", "../../", "../");
    setContainer(`
        <div class="card-body">
            <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
            
            <div>
                <div class="mb-4">
                    <input id="name" class="form-control form-control-lg"/>
                    <label for="name">Name</label>
                    
                </div>
                
                <div class="mb-4">
                    <input id="quantity" class="form-control form-control-lg"/>
                    <label for="quantity">Quantity</label>
                    
                </div>
                
                <div class="mb-4">
                    <input id="price" class="form-control form-control-lg"/>
                    <label for="price">Price</label>
                    
                </div>
                
                <div class="mb-4">
                    <input id="discount" class="form-control form-control-lg"/>
                    <label for="discount">Discount</label>
                    
                </div>
                
                <div class="mb-4">
                    <input id="description" class="form-control form-control-lg"/>
                    <label for="description">Description</label>
                </div>
                
                <div id="productImgCard" class="card mb-4" style="border-radius: 15px" hidden>
                    <img src="" alt="product" id="productImgValue" class="card-img" style="border-radius: 15px">
                </div>
                
                <div class="mb-4">
                    <input type="file" accept=".png" id="productImgUploader" class="form-control form-control-lg"/>
                    <label for="productImgUploader">.png image</label>
                </div>
                
                <div class="d-flex justify-content-center">
                    <button class="btn btn-dark btn-outline-success" id="submit"></button>
                </div>
            </div>
            
            <div id="alert" class="mt-3"></div>
        </div>
    `);
    setModificationPage(headlineInnerHtml, submitInnerHtml);

    productName = document.getElementById("name");
    productQuantity = document.getElementById("quantity");
    productPrice = document.getElementById("price");
    productDiscount = document.getElementById("discount");
    productDescription = document.getElementById("description");

    productImgCard = document.getElementById("productImgCard");
    productImgValue = document.getElementById("productImgValue");
    productImgUploader = document.getElementById("productImgUploader");

    submit = document.getElementById("submit");

    const modificationStorageKey = localStorage.getItem(modificationStorageKeyStorageKey);

    const product = JSON.parse(localStorage.getItem(modificationStorageKey));
    if (product) {
        productName.value = product[productNameDtoKey];
        productQuantity.value = product[productQuantityDtoKey];
        productPrice.value = product[productPriceDtoKey];
        productDiscount.value = product[productDiscountDtoKey];
        productDescription.value = product[productDescriptionDtoKey];
        productImgValue.src = product[productImgDtoKey];

        if (product[productImgDtoKey]) {
            productImgCard.hidden = false;
        }
    } else {
        localStorage.setItem(modificationStorageKey, JSON.stringify(createProduct(null, null)));
    }

    setFormControlOnchange(function () {
        const product = JSON.parse(localStorage.getItem(modificationStorageKey));
        return createProduct(product[productUuidDtoKey], product[productImgDtoKey]);
    });
    productImgUploader.onchange = function () {
        if (productImgUploader.value.split(".").pop() !== "png") {
            alertMessage("danger", "Please, upload a .png image");
            return;
        }

        const fileReader = new FileReader();
        fileReader.readAsDataURL(productImgUploader.files[0]);
        fileReader.onload = function() {
            productImgValue.src = fileReader.result;
            productImgCard.hidden = false;

            localStorage.setItem(
                modificationStorageKey,
                JSON.stringify(createProduct(
                    JSON.parse(localStorage.getItem(modificationStorageKey))[productUuidDtoKey], fileReader.result
                ))
            );
        };
    }

    setSubmitOnclick(function () {
        return JSON.parse(localStorage.getItem(modificationStorageKey));
    }, httpMethod, productsApiUrl, contentTypePrefix + "product" + contentTypeSuffix);
}
