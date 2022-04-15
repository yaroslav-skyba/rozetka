onload = function () {
    setNavigation("", "profile/", "profile/admin/");
    setMainAttributes();

    const products = localStorage.getItem(cartProductsStorageKey);

    if (products) {
        let productOverallPrice = 0;

        const parsedProducts = JSON.parse(products);

        for (let i = 0; i < parsedProducts.length; i++) {
            let productDescription = "";

            if (parsedProducts[i][productDescriptionDtoKey]) {
                productDescription = parsedProducts[i][productDescriptionDtoKey];
            }

            let productDiscountRatio = 1;

            if (parsedProducts[i][productDiscountDtoKey]) {
                productDiscountRatio = parsedProducts[i][productDiscountDtoKey] / 100;
            }

            const productPrice = parsedProducts[i][productPriceDtoKey] * productDiscountRatio * parsedProducts[i][productQuantityDtoKey];
            productOverallPrice += productPrice;

            setContainer(`
                <img src="` + parsedProducts[i][productImgDtoKey] + `" class="card-img-top" style="border-radius: 15px"
                     alt="` + parsedProducts[i][productNameDtoKey] + `">
                 
                <div class="card-body">
                    <h3 class="card-title">` + parsedProducts[i][productNameDtoKey] + `</h3>
                    <p class="card-text">` + productDescription + `</p>
                    
                    <input id="productCounter_` + parsedProducts[i][productUuidDtoKey] + `" class="productCounter" type="number" min="1"
                           value="` + parsedProducts[i][productQuantityDtoKey] + `">
                    <label id="productCounter_` + parsedProducts[i][productUuidDtoKey] + `"></label>
                    
                    <span>` + productPrice + `</span><br/>
                    <button id="productDeletion" class="btn btn-dark btn-outline-success mt-4" type="button">Delete</button>
                </div>
            `);
        }

        setContainer(`
            <div class="card-body">
                Overall:` + productOverallPrice + `<br/>
                <button id="submit" class="btn btn-dark btn-outline-success">Place an order</button>
            </div>
        `);

        const productCounters = document.getElementsByClassName("productCounter");

        for (let i = 0; i < productCounters.length; i++) {
            productCounters[i].onchange = function () {
                parsedProducts[i][productQuantityDtoKey] = productCounters[i].value;
                localStorage.setItem(cartProductsStorageKey, JSON.stringify(parsedProducts));

                location.reload();
            }
        }
    } else {
        main.innerHTML = `EMPTY`;
    }
}

