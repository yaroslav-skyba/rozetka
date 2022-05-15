const productReviewStorageKey = "productReview";

const productReviewUuidDtoKey = "uuid";
const productReviewUserUuidDtoKey = "userUuid";
const productReviewUserNameDtoKey = "userName";
const productReviewContentDtoKey = "content";
const productReviewRatingDtoKey = "rating";

const productReviewContentType = contentTypePrefix + "review" + contentTypeSuffix;

let productReviewContent;
let productReviewRating;

let productApiUrl;
let productReviewsApiUrl;
let productReviewApiUrl;

let product;

function setProductReviewSubmitOnclick(uuid, httpMethod) {
    document.getElementById("submit").onclick = function () {
        const review = JSON.parse(localStorage.getItem(productReviewStorageKey));
        review[productReviewUuidDtoKey] = uuid;

        if (!review[productReviewRatingDtoKey]) {
            review[productReviewRatingDtoKey] = null;
        }

        sendModificationHttpRequest(httpMethod, productReviewsApiUrl, productReviewContentType, review);
    }
}

function createProductReview(uuid, userUuid, productReviewContent, productReviewRating) {
    const review = {};
    review[productReviewUuidDtoKey] = uuid;
    review[productReviewUserUuidDtoKey] = userUuid;
    review[productReviewUserNameDtoKey] = null;
    review[productReviewContentDtoKey] = productReviewContent;
    review[productReviewRatingDtoKey] = productReviewRating;

    return review;
}

function setProductReviewModificationForm(userUuid) {
    const review = JSON.parse(localStorage.getItem(productReviewStorageKey));
    if (review) {
        productReviewContent.value = review[productReviewContentDtoKey];
        productReviewRating.value = review[productReviewRatingDtoKey];
    } else {
        localStorage.setItem(
            productReviewStorageKey,
            JSON.stringify(createProductReview(null, userUuid, productReviewContent.value, productReviewRating.value))
        );
    }

    for (const formControl of document.getElementsByClassName("form-control")) {
        formControl.onchange = function () {
            localStorage.setItem(
                productReviewStorageKey,
                JSON.stringify(createProductReview(
                    JSON.parse(localStorage.getItem(productReviewStorageKey))[productReviewUuidDtoKey], userUuid,
                    productReviewContent.value, productReviewRating.value
                ))
            );
        }
    }

    setProductAddingButtonOnclick(document.getElementById("productAdding"), product, 0);
    setProductReviewSubmitOnclick(null, "POST");
}

onload = function() {
    setNavigation("", "profile/", "profile/admin/");
    setMainAttributes();

    productApiUrl = productsApiUrl + "/" + Object.fromEntries(new URLSearchParams(location.search).entries())[productUuidDtoKey];
    productReviewsApiUrl = productApiUrl + "/reviews";

    xmlHttpRequest.open("GET", productApiUrl);
    xmlHttpRequest.send();
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            if (xmlHttpRequest.responseURL === productApiUrl) {
                product = JSON.parse(xmlHttpRequest.responseText);
                product[productPriceDtoKey] = getProductPrice(product);
                delete product[productDiscountDtoKey];

                setContainer(`
                    <img src="` + product[productImgDtoKey] + `" style="border-radius: 15px" alt="` + product[productNameDtoKey] + `">
                     
                    <div class="card-body">
                        <h3 class="card-title">` + product[productNameDtoKey] + `</h3>
    
                        <p class="card-text">` + getProductDescription(product) + `</p>
                        Price: ` + product[productPriceDtoKey] + `<br/>
                        
                        <button id="productAdding" class="btn btn-dark btn-outline-success mt-4">Add to your cart</button>
                    </div>
                `);
                setContainer(`
                    <div class="card-body">
                        <h2 class="text-uppercase text-center mb-5">Post your review</h2>
                        
                        <div id="currentReview">
                            <div class="mb-4">
                                <input id="reviewContent" class="form-control form-control-lg"/>
                                <label for="reviewContent">Your review content</label>
                            </div>
                            
                            <div class="mb-4">
                                <select id="reviewRating" class="form-control form-control-lg">
                                    <option value=""></option>
                                    <option value="5">5</option>
                                    <option value="4">4</option>
                                    <option value="3">3</option>
                                    <option value="2">2</option>
                                    <option value="1">1</option>
                                </select>
                                 
                                <label for="reviewRating">Your review rating</label>
                            </div>
                            
                            <div class="d-flex justify-content-center">
                                <button class="btn btn-dark btn-outline-success" id="submit">Post</button>
                            </div>
                        </div>
                        
                        <div id="alert" class="mt-3"></div>
                    </div>
                `);

                productReviewContent = document.getElementById("reviewContent");
                productReviewRating = document.getElementById("reviewRating");

                xmlHttpRequest.open("GET", productReviewsApiUrl);
                xmlHttpRequest.send();
            } else if (xmlHttpRequest.responseURL === productReviewsApiUrl) {
                const contentType = xmlHttpRequest.getResponseHeader("Content-Type");

                if (contentType === contentTypePrefix + "reviewlist" + contentTypeSuffix) {
                    const reviewsH2 = document.createElement("h2");
                    reviewsH2.className = "text-white text-uppercase text-center mt-5";
                    reviewsH2.innerHTML = "reviews";
                    main.append(reviewsH2);

                    const userUuid = JSON.parse(localStorage.getItem(currentUserStorageKey))[userUuidDtoKey];

                    setProductReviewModificationForm(userUuid);

                    let isAdmin;

                    if (localStorage.getItem(currentUserRoleNameStorageKey) === adminRoleName) {
                        isAdmin = true;
                    }

                    const reviews = JSON.parse(xmlHttpRequest.responseText);

                    for (let i = 0; i < reviews.length; i++) {
                        let reviewRating = "No rating";

                        if (reviews[i][productReviewRatingDtoKey]) {
                            reviewRating = reviews[i][productReviewRatingDtoKey];
                        }

                        let reviewButtons = ``;

                        if (isAdmin || reviews[i][productReviewUserUuidDtoKey] === userUuid) {
                            reviewButtons = `
                                <button class="btn btn-dark btn-outline-success mt-4 reviewEdit">Edit</button>
                                <button class="btn btn-dark btn-outline-success mt-4 reviewDeletion">Delete</button>
                            `;
                        }

                        setContainer(`
                            <div class="card-body">
                                <h3 class="card-title">` + reviews[i][productReviewUserNameDtoKey] + `</h3>
            
                                <p class="card-text">` + reviews[i][productReviewContentDtoKey] + `</p>
                                Rating: ` + reviewRating + `<br/>`

                                + reviewButtons +
                            `</div>
                        `);
                    }

                    const reviewEditButtons = document.getElementsByClassName("reviewEdit");
                    for (let i = 0; i < reviewEditButtons.length; i++) {
                        reviewEditButtons[i].onclick = function() {
                            for (let j = 0; j < reviews.length; j++) {
                                if (reviews[j][productReviewUserUuidDtoKey] === userUuid) {
                                    productReviewContent.value = reviews[j][productReviewContentDtoKey];
                                    productReviewRating.value = reviews[j][productReviewRatingDtoKey];

                                    setProductReviewSubmitOnclick(reviews[j][productReviewUuidDtoKey], "PUT");

                                    if (!location.href.includes("#")) {
                                        location.href += "#currentReview";
                                    }

                                    break;
                                }
                            }
                        }
                    }

                    const reviewDeletionButtons = document.getElementsByClassName("reviewDeletion");
                    for (let i = 0; i < reviewDeletionButtons.length; i++) {
                        reviewDeletionButtons[i].onclick = function() {
                            if (confirm("Are you sure to delete this review?")) {
                                productReviewApiUrl = productReviewsApiUrl + "/" + reviews[i][productReviewUuidDtoKey];

                                sendHttpRequest(
                                    "DELETE", productReviewApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null
                                );
                            }
                        }
                    }
                } else if (contentType === "text/plain;charset=UTF-8") {
                    localStorage.removeItem(productReviewStorageKey);
                    alertMessage("success", xmlHttpRequest.responseText);
                }
            } else if (xmlHttpRequest.responseURL === productReviewApiUrl) {
                localStorage.removeItem(productReviewStorageKey);
                alertMessage("success", xmlHttpRequest.responseText);
            }
        } else if (xmlHttpRequest.status === 201) {
            localStorage.removeItem(productReviewStorageKey);
            alertMessage("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 204) {
            localStorage.removeItem(productReviewStorageKey);
            location.reload();
        } else if (xmlHttpRequest.status === 404) {
            if (xmlHttpRequest.responseURL === productApiUrl) {
                location.href = "/";
            } else if (xmlHttpRequest.responseURL === productReviewsApiUrl) {
                setProductReviewModificationForm(JSON.parse(localStorage.getItem(currentUserStorageKey))[userUuidDtoKey]);
            }
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}
