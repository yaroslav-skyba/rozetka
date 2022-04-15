onload = function () {
    setNavigation("", "profile/", "profile/admin/");

    const products = localStorage.getItem(cartProductsStorageKey);

    if (products) {
        let overallPrice = 0;

        for (const product in JSON.parse(products)) {
            main.innerHTML +=
                `<div class="card bg-dark text-white text-break" style="border-radius: 15px; border-color: #198754">
                     <img src="` + product[productImgDtoKey] + `" class="img-fluid rounded-start" alt="` + product[productNameDtoKey] + `">
                     
                     <div class="card-body">
                         <h5 class="card-title">` + product[productNameDtoKey] + `</h5>
                         <p class="card-text">` + product[productDescriptionDtoKey] + `</p>
                         
                         <input id="productCounter_` + product[productUuidDtoKey] + `" type="number" min="1" value="1">
                         <label id="productCounter_` + product[productUuidDtoKey] + `"></label>
                         
                         <span id="productPrice_` + product[productUuidDtoKey] + `"></span>
                         <button id="productDeletion" class="btn btn-dark btn-outline-success" type="button">Delete</button>
                    </div>
                </div>`;

            const price =
                product[productPriceDtoKey] * product[productDiscountDtoKey] / 100
                * document.getElementById("productCounter_" + product[productUuidDtoKey]);

            overallPrice += price;
            document.getElementById("productPrice_" + product[productUuidDtoKey]).value = price;
        }

        main.innerHTML +=
            `Overall: ` + overallPrice +
            `<button id="submit" class="btn btn-dark btn-outline-success">Place an order</button>`;
    } else {
        main.innerHTML = `EMPTY`;
    }

    setMainAttributes();
}
