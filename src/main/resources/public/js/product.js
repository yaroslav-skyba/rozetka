const productReviewStorageKey = "productReview";

const productReviewUuidDtoKey = "uuid";
const productReviewUserUuidDtoKey = "userUuid";
const productReviewContentDtoKey = "content";
const productReviewRatingDtoKey = "rating";

const productReviewContentType = contentTypePrefix + "review" + contentTypeSuffix;

let productReviewContent;
let productReviewRating;

let productApiUrl;
let productReviewsApiUrl;
let productReviewApiUrl;

function setProductReviewSubmitOnclick(httpMethod) {
    const review = JSON.parse(localStorage.getItem(productReviewStorageKey));

    if (!review[productReviewRatingDtoKey]) {
        review[productReviewRatingDtoKey] = null;
    }

    sendModificationHttpRequest(httpMethod, productReviewsApiUrl, productReviewContentType, review);
}

function createProductReview(uuid, userUuid) {
    const review = {};
    review[productReviewUuidDtoKey] = uuid;
    review[productReviewUserUuidDtoKey] = userUuid;
    review[productReviewContentDtoKey] = productReviewContent.value;
    review[productReviewRatingDtoKey] = productReviewRating.value;

    return review;
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
            const userUuid = JSON.parse(localStorage.getItem(currentUserStorageKey))[userUuidDtoKey];

            if (xmlHttpRequest.responseURL === productApiUrl) {
                const product = JSON.parse(xmlHttpRequest.responseText);

                setContainer(`
                    <img src="` + product[productImgDtoKey] + `" style="border-radius: 15px" alt="` + product[productNameDtoKey] + `">
                     
                    <div class="card-body">
                        <h3 class="card-title">` + product[productNameDtoKey] + `</h3>
    
                        <p class="card-text">` + getProductDescription(product) + `</p>
                        The price: ` + getProductPrice(product) + `<br/>
                        
                        <button id="productAdding" class="btn btn-dark btn-outline-success mt-4" type="button">Add to your cart</button>
                    </div>
                `);

                setContainer(`
                    <div class="card-body">
                        <h2 class="text-uppercase text-center mb-5">type a review</h2>
                        
                        <div id="currentReview">
                            <div class="form-outline mb-4">
                                <input id="reviewContent" class="form-control form-control-lg"/>
                                <label for="reviewContent">Review content</label>
                            </div>
                            
                            <div class="form-outline mb-4">
                                <select id="reviewRating" class="form-control form-control-lg">
                                    <option value=""></option>
                                    <option value="1">Terrible</option>
                                    <option value="2">Bad</option>
                                    <option value="3">Normal</option>
                                    <option value="4">Good</option>
                                    <option value="5">Awesome</option>
                                </select>
                                 
                                <label for="reviewRating">Review rating</label>
                            </div>
                            
                            <div class="d-flex justify-content-center">
                                <button class="btn btn-dark btn-outline-success" id="submit">Post</button>
                            </div>
                        </div>
                        
                        <div id="alert" class="mt-3"></div>
                    </div>
                `);

                setProductAddingButtonOnclick(document.getElementById("productAdding"), product, 0);

                productReviewContent = document.getElementById("reviewContent");
                productReviewRating = document.getElementById("reviewRating");

                const review = JSON.parse(localStorage.getItem(productReviewStorageKey));
                if (review) {
                    productReviewContent.value = review[productReviewContentDtoKey];
                    productReviewRating.value = review[productReviewRatingDtoKey];
                } else {
                    localStorage.setItem(productReviewStorageKey, JSON.stringify(createProductReview(null, userUuid)));
                }

                for (const formControl of document.getElementsByClassName("form-control")) {
                    formControl.onchange = function () {
                        localStorage.setItem(
                            productReviewStorageKey,
                            JSON.stringify(createProductReview(
                                JSON.parse(localStorage.getItem(productReviewStorageKey)[productReviewUuidDtoKey]), userUuid)
                            )
                        );
                    }
                }

                submit.onclick = function () {
                    setProductReviewSubmitOnclick("POST");
                }

                xmlHttpRequest.open("GET", productReviewsApiUrl);
                xmlHttpRequest.send();
            } else if (xmlHttpRequest.responseURL === productReviewsApiUrl) {
                const contentType = xmlHttpRequest.getResponseHeader("Content-Type");

                if (contentType === contentTypePrefix + "reviewList" + contentTypeSuffix) {
                    const reviews = JSON.parse(xmlHttpRequest.responseText);

                    let isAdmin = false;

                    if (localStorage.getItem(currentUserRoleNameStorageKey) === adminRoleName) {
                        isAdmin = true;
                    }

                    for (let i = 0; i < reviews.length; i++) {
                        let reviewRating = "No rating";

                        if (reviews[i][productReviewRatingDtoKey]) {
                            reviewRating = reviews[i][productReviewRatingDtoKey];
                        }

                        let reviewButtons = ``;

                        if (isAdmin || reviews[i][productReviewUserUuidDtoKey] === userUuid) {
                            reviewButtons = `
                                <button class="btn btn-dark btn-outline-success mt-4 reviewEdit" type="button" href="#currentReview">
                                    Edit
                                </button>
                                
                                <button class="btn btn-dark btn-outline-success mt-4 reviewDeletion" type="button">Delete</button>
                            `;
                        }

                        setContainer(`
                            <div class="card-body">
                                <h3 class="card-title">` + reviews[i]["userName"] + `</h3>
            
                                <p class="card-text">` + reviews[i][productReviewContentDtoKey] + `</p>
                                Rating: ` + reviewRating + `<br/>`

                                + reviewButtons +
                            `</div>
                        `);
                    }

                    const reviewEditButtons = document.getElementsByClassName("reviewEdit");

                    for (let i = 0; i < reviewEditButtons.length; i++) {
                        reviewEditButtons[i].onclick = function() {
                            submit.onclick = function () {
                                setProductReviewSubmitOnclick("PUT");
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
                } else if (contentType === productReviewContentType) {
                    alertMessage("success", xmlHttpRequest.responseText);
                    location.reload();
                }
            } else if (xmlHttpRequest.responseURL === productReviewApiUrl) {
                location.reload();
            }
        } else if (xmlHttpRequest.status === 201) {
            alertMessage("success", xmlHttpRequest.responseText);
            location.reload();
        } else if (xmlHttpRequest.status === 404) {
            if (xmlHttpRequest.responseURL === productApiUrl) {
                location.href = "/";
            } else if (xmlHttpRequest.responseURL === productReviewsApiUrl) {
                /*document.getElementById("container").innerHTML += `
                    <h1 class="text-uppercase text-center text-white py-5">there are no reviews</h1>
                `;*/
            }
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}
