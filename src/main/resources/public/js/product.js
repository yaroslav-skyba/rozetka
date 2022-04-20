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
                setContainer(`
                    <div class="card-body">
                        <h2 class="text-uppercase text-center mb-5">type a review</h2>
                        
                        <div id="form">
                            <div class="form-outline mb-4">
                                <input id="content" class="form-control form-control-lg"/>
                                <label for="content">Content</label>
                            </div>
                            
                            <div class="form-outline mb-4">
                                <select id="rating" class="form-control form-control-lg">
                                    <option value=""></option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                                 
                                <label for="rating">Rating</label>
                            </div>
                            
                            <div class="d-flex justify-content-center">
                                <button class="btn btn-dark btn-outline-success" id="submit">Post</button>
                            </div>
                        </div>
                        
                        <div id="alert" class="mt-3"></div>
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
