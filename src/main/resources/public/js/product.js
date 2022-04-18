let productApiUrl;

onload = function() {
    const queryParams = Object.fromEntries(new URLSearchParams(location.search).entries());

    if (!queryParams[productUuidDtoKey]) {
        location.href = "/";
    }

    setNavigation("", "profile/", "profile/admin/");
    setMainAttributes();

    productApiUrl = productsApiUrl + "/" + queryParams[productUuidDtoKey];

    xmlHttpRequest.open("GET", productApiUrl);
    xmlHttpRequest.send();
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            const reviewsApiUrl = productApiUrl + "/reviews";

            if (xmlHttpRequest.responseURL === productApiUrl) {
                const product = JSON.parse(xmlHttpRequest.responseText);

                setContainer(`
                    <img src="` + product[productImgDtoKey] + `" style="border-radius: 15px" alt="` + product[productNameDtoKey] + `">
                     
                    <div class="card-body">
                        <h3 class="card-title">` + product[productNameDtoKey] + `</h3>
    
                        <p class="card-text">` + getProductDescription(product) + `</p>
                        <span>The price: ` + getProductPrice(product) + `</span><br/>
                        
                        <button class="btn btn-dark btn-outline-success mt-4 productAdding" type="button">Add to your cart</button>
                    </div>
                `);

                setProductAddingButtonOnclick(product, 0);

                xmlHttpRequest.open("GET", reviewsApiUrl);
                xmlHttpRequest.send();
            } else if (xmlHttpRequest.responseURL === reviewsApiUrl) {
                const reviews = JSON.parse(xmlHttpRequest.responseText);
            }
        } else if (xmlHttpRequest.status === 404) {
            location.href = "/";
        }
    }
}
