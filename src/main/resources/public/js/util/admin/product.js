//noinspection DuplicatedCode

const productImgUploaderFailMessage = "Please, upload a .png image";

const productContentType = contentTypePrefix + "product" + contentTypeSuffix;

let productName;
let productQuantity;
let productPrice;
let productDiscount;
let productDescription;
let productImgUploader;

function setProductImg(img) {
    document.getElementById("productImgValue").src = img;
    document.getElementById("productImgCard").hidden = false;
}

function sendProductModificationHttpRequest(headlineInnerHtml, submitInnerHtml, storageKey, uuid, imgStorageKey, method, url) {
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
            
            <div class="form-outline mb-4">
                <input id="description" class="form-control form-control-lg"/>
                <label for="description">A description</label>
            </div>
            
            <div id="productImgCard" class="card mb-4" style="border-radius: 15px" hidden>
                <img src="" alt="product" id="productImgValue" class="card-img" style="border-radius: 15px">
            </div>
            
            <div class="mb-4">
                <input type="file" accept=".png" id="productImgUploader" class="form-control form-control-lg"/>
                <label for="productImgUploader">A .png image</label>
            </div>
            
            <div class="d-flex justify-content-center">
                <button class="btn btn-dark btn-outline-success" id="submit"></button>
            </div>
        </div>
        
        <div id="alert" class="mt-3"></div>
    `);

    productName = document.getElementById("name");
    productQuantity = document.getElementById("quantity");
    productPrice = document.getElementById("price");
    productDiscount = document.getElementById("discount");
    productDescription = document.getElementById("description");
    productImgUploader = document.getElementById("productImgUploader");

    configModificationPage(headlineInnerHtml, submitInnerHtml);

    const product = JSON.parse(localStorage.getItem(storageKey));

    if (product) {
        productName.value = product[productNameDtoKey];
        productQuantity.value = product[productQuantityDtoKey];
        productPrice.value = product[productPriceDtoKey];
        productDiscount.value = product[productDiscountDtoKey];
        productDescription.value = product[productDescriptionDtoKey];
    }

    setFormControlElementOnchange(storageKey, function () {
        const product = {};
        product[productUuidDtoKey] = uuid;
        product[productNameDtoKey] = productName.value;
        product[productQuantityDtoKey] = productQuantity.value;
        product[productPriceDtoKey] = productPrice.value;
        product[productDiscountDtoKey] = productDiscount.value;
        product[productDescriptionDtoKey] = productDescription.value;

        return product;
    });

    const productImgValue = localStorage.getItem(imgStorageKey);

    if (productImgValue) {
        setProductImg(productImgValue);
    }

    productImgUploader.onchange = function () {
        if (productImgUploader.value.split(".").pop() !== "png") {
            alert("danger", productImgUploaderFailMessage);
            return;
        }

        const fileReader = new FileReader();
        fileReader.readAsDataURL(productImgUploader.files[0]);
        fileReader.onload = function() {
            const img = fileReader.result;

            localStorage.setItem(imgStorageKey, img.toString());
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

        const maxDiscountValue = 100;

        if (Number(productDiscount.value) > maxDiscountValue) {
            alert("danger", "A discount value should be " + maxDiscountValue + " or less");
            return null;
        }

        if (!localStorage.getItem(imgStorageKey)) {
            alert("danger", productImgUploaderFailMessage);
            return null;
        }

        sendModificationHttpRequest(JSON.parse(localStorage.getItem(storageKey)), method, url, productContentType);
    }
}

function succeedModificationProductImg(storageKey, imgStorageKey, successMessage) {
    localStorage.removeItem(storageKey);
    localStorage.removeItem(imgStorageKey);

    alert("success", successMessage);
}
