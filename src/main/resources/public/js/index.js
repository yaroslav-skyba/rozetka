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
            const rowCols = document.createElement("div");

            rowCols.className = "row row-cols-6";

            for (let i = 0; i < products.length; i++) {
                products[i][productPriceDtoKey] = getProductPrice(products[i]);
                delete products[i][productDiscountDtoKey];

                rowCols.innerHTML += `
                    <div class="card bg-dark text-white text-break" style="border-radius: 15px; border-color: #0d6dfcff">
                        <img src="` + products[i][productImgDtoKey] + `" style="border-radius: 15px"
                             alt="` + products[i][productNameDtoKey] + `">
                         
                        <div class="card-body">
                            <a href="product.html?` + productUuidDtoKey + `=` + products[i][productUuidDtoKey] + `">
                                <h3 class="card-title">` + products[i][productNameDtoKey] + `</h3>
                            </a>
                            
                            <p class="card-text">` + getProductDescription(products[i]) + `</p>
                            <span>Price: ` + products[i][productPriceDtoKey] + ` $</span><br/>
                            
                            <button class="btn btn-dark btn-outline-primary mt-4 productAdding">Add to your cart</button>
                        </div>
                    </div>
                `;
            }

            const container = document.createElement("div");

            container.className = "container";

            container.append(rowCols);

            document.getElementById("main").append(container);

            const productAddingButtons = document.getElementsByClassName("productAdding");

            for (let i = 0; i < productAddingButtons.length; i++) {
                setProductAddingButtonOnclick(productAddingButtons[i], products[i], i);
            }
        } else if (xmlHttpRequest.status === 404) {
            setContainer(`<h1 class="text-uppercase text-center">there are no products</h1>`);
        }
    }
}
