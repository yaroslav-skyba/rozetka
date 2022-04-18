onload = function() {
    setNavigation("", "profile/", "profile/admin/");
    setMainAttributes();

    const searchedProductName = localStorage.getItem(searchedProductNameStorageKey);

    if (searchedProductName) {
        xmlHttpRequest.open("GET", productsApiUrl + "?" + productNameDtoKey + "=" + searchedProductName);
    } else {
        xmlHttpRequest.open("GET", productsApiUrl);
    }

    localStorage.removeItem(searchedProductNameStorageKey);
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
                        <a href="product.html?` + productUuidDtoKey + `=` + products[i][productUuidDtoKey] + `">
                            <h3 class="card-title">` + products[i][productNameDtoKey] + `</h3>
                        </a>
                        
                        <p class="card-text">` + getProductDescription(products[i]) + `</p>
                        <span>The price: ` + getProductPrice(products[i]) + `</span><br/>
                        
                        <button class="btn btn-dark btn-outline-success mt-4 productAdding" type="button">Add to your cart</button>
                    </div>
                `);
            }

            const productAddingButtons = document.getElementsByClassName("productAdding");

            for (let i = 0; i < productAddingButtons.length; i++) {
                productAddingButtons[i].onclick = function () {
                    setProductAddingButtonOnclick(products[i], i);
                }
            }
        } else if (xmlHttpRequest.status === 404) {
            main.innerHTML = '<h1 class="text-center text-white">THERE ARE NO PRODUCTS</h1>';
        }
    }
}
