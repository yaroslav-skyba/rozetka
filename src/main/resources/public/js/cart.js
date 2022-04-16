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
            const productPrice = getProductPrice(parsedProducts, i);
            productOverallPrice += productPrice;

            setContainer(`
                <img src="` + parsedProducts[i][productImgDtoKey] + `" style="border-radius: 15px"
                     alt="` + parsedProducts[i][productNameDtoKey] + `">
                 
                <div class="card-body">
                    <a href="product.html?uuid=` + products[i][productUuidDtoKey] + `">
                        <h3 class="card-title">` + parsedProducts[i][productNameDtoKey] + `</h3>    
                    </a>
                    
                    <p class="card-text">` + getProductDescription(parsedProducts, i) + `</p>
                    
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

        const productDeletionButtons = document.getElementsByClassName("productDeletion");

        for (let i = 0; i < productDeletionButtons.length; i++) {
            productDeletionButtons[i].onclick = function () {
                const products = [];

                for (let j = 0; j < parsedProducts.length; j++) {
                    if (i !== j) {
                        products.push(parsedProducts[j]);
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
        main.innerHTML = '<h1 class="text-center text-white">YOUR CART IS EMPTY</h1>';
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
