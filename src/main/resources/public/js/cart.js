onload = function () {
    setNavigation("", "profile/", "profile/admin/");
    setMainAttributes();

    const products = localStorage.getItem(cartProductsStorageKey);

    if (products) {
        let productOverallPrice = 0;

        const parsedProducts = JSON.parse(products);

        if (!parsedProducts.length) {
            localStorage.removeItem(cartProductsStorageKey);
            location.reload();
        }

        for (let i = 0; i < parsedProducts.length; i++) {
            let productDescription = "";

            if (parsedProducts[i][productDescriptionDtoKey]) {
                productDescription = parsedProducts[i][productDescriptionDtoKey];
            }

            let productDiscountRatio = 1;

            if (parsedProducts[i][productDiscountDtoKey]) {
                productDiscountRatio = parsedProducts[i][productDiscountDtoKey] / 100;
            }

            const productPricePower = 100;
            const productPrice = Math.round(
                parsedProducts[i][productPriceDtoKey] * productDiscountRatio * parsedProducts[i][productQuantityDtoKey] * productPricePower
            ) / productPricePower;

            productOverallPrice += productPrice;

            setContainer(`
                <img src="` + parsedProducts[i][productImgDtoKey] + `" class="card-img-top" style="border-radius: 15px"
                     alt="` + parsedProducts[i][productNameDtoKey] + `">
                 
                <div class="card-body">
                    <h3 class="card-title">` + parsedProducts[i][productNameDtoKey] + `</h3>
                    <p class="card-text">` + productDescription + `</p>
                    
                    <input id="productCounter_` + parsedProducts[i][productUuidDtoKey] + `" class="productCounter" type="number" min="1" 
                    max="100" value="` + parsedProducts[i][productQuantityDtoKey] + `">
                    <label id="productCounter_` + parsedProducts[i][productUuidDtoKey] + `"></label><br/>
                    
                    <span>The price: ` + productPrice + `</span><br/>
                    <button class="btn btn-dark btn-outline-success mt-4 productDeletion" type="button">Delete</button>
                </div>
            `);
        }

        setContainer(`
            <div class="card-body">
                Overall: ` + productOverallPrice + `<br/>
                <button id="submit" class="btn btn-dark btn-outline-success">Place an order</button>
                
                <div id="alert" class="mt-3"></div>
            </div>
        `);

        const productCounters = document.getElementsByClassName("productCounter");

        for (let i = 0; i < productCounters.length; i++) {
            productCounters[i].onchange = function () {
                if (!productCounters[i].checkValidity()) {
                    productCounters[i].value = productCounters[i].min;
                    return;
                }

                parsedProducts[i][productQuantityDtoKey] = productCounters[i].value;
                localStorage.setItem(cartProductsStorageKey, JSON.stringify(parsedProducts));

                location.reload();
            }
        }

        const productDeletions = document.getElementsByClassName("productDeletion");

        for (let i = 0; i < productDeletions.length; i++) {
            productDeletions[i].onclick = function () {
                const products = [];

                for (let j = 0; j < parsedProducts.length; j++) {
                    if (i !== j) {
                        products[j] = parsedProducts[j]
                    }
                }

                localStorage.setItem(cartProductsStorageKey, JSON.stringify(products));
                location.reload();
            }
        }

        document.getElementById("submit").onclick = function() {
            const userUuid = JSON.parse(localStorage.getItem(currentUserStorageKey))[userUuidDtoKey];
            const orderItems = [];

            for (let i = 0; i < parsedProducts.length; i++) {
                for (let j = 0; j < parsedProducts[i][productQuantityDtoKey]; j++) {
                    orderItems.push({uuidProduct:parsedProducts[i][productUuidDtoKey], uuidUser:userUuid})
                }
            }

            sendModificationHttpRequest(orderItems, "POST", ordersApiUrl, contentTypePrefix + "orderItemList" + contentTypeSuffix);
        }
    } else {
        main.innerHTML = `EMPTY`;
    }
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 201) {
            localStorage.removeItem(cartProductsStorageKey);
            alertMessage("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}
