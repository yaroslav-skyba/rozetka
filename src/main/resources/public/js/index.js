onload = function() {
    setNavigation("", "profile/", "profile/admin/");
    setMainAttributes();

    xmlHttpRequest.open("GET", productsApiUrl);
    xmlHttpRequest.send();
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            const products = JSON.parse(xmlHttpRequest.responseText);

            for (let i = 0; i < products.length; i++) {
                setContainer(`
                    <img src="` + products[i][productImgDtoKey] + `" style="border-radius: 15px"
                         alt="` + products[i][productNameDtoKey] + `">
                     
                    <div class="card-body">
                        <a href="product.html?uuid=` + products[i][productUuidDtoKey] + `">
                            <h3 class="card-title">` + products[i][productNameDtoKey] + `</h3>
                        </a>
                        
                        <p class="card-text">` + getProductDescription(products, i) + `</p>
                        <span>The price: ` + getProductPrice(products, i) + `</span><br/>
                        
                        <button class="btn btn-dark btn-outline-success mt-4 productAdding" type="button">Add to your cart</button>
                    </div>
                `);
            }

            const productAddingButtons = document.getElementsByClassName("productAdding");

            for (let i = 0; i < productAddingButtons.length; i++) {
                productAddingButtons[i].onclick = function () {
                    const cartProducts = localStorage.getItem(cartProductsStorageKey);

                    let cartParsedProducts = [];

                    if (cartProducts) {
                        cartParsedProducts = JSON.parse(cartProducts);
                    }

                    if (cartParsedProducts.find(value => value[productUuidDtoKey] === products[i][productUuidDtoKey])) {
                        location.href = "cart.html";
                    } else {
                        products[i][productQuantityDtoKey] = 1;
                        cartParsedProducts.push(products[i]);
                        localStorage.setItem(cartProductsStorageKey, JSON.stringify(cartParsedProducts));

                        const alert = document.getElementById("alert");

                        if (alert) {
                            alert.remove();
                        }

                        document.getElementsByClassName("card-body")[i].innerHTML += '<div id="alert" class="mt-3"></div>';
                        alertMessage("success", "The product was successfully added to your cart!");
                    }
                }
            }
        } else if (xmlHttpRequest.status === 404) {
            main.innerHTML = '<h1 class="text-center text-white">THERE ARE NO PRODUCTS</h1>';
        }
    }
}
