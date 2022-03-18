//noinspection DuplicatedCode

let name;
let quantity;
let price;
let discount;
let description;

function setProductModificationForm(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    redirectUnauthorized();
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
                <input id="discount" class="form-control form-control-lg"/>
                <label for="discount">A discount</label>
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

    storageKeyPrefix = "product_" + storageKeyPrefix;
    configAdminModificationPage(headlineInnerHtml, submitInnerHtml, storageKeyPrefix);

    name.value = localStorage.getItem(storageKeyPrefix + name.id);
    quantity.value = localStorage.getItem(storageKeyPrefix + quantity.id);
    price.value = localStorage.getItem(storageKeyPrefix + price.id);
    discount.value = localStorage.getItem(storageKeyPrefix + discount.id);
    description.value = localStorage.getItem(storageKeyPrefix + description.id);
}
