const xmlHttpRequest = new XMLHttpRequest();

const main = document.getElementById("main");

const userStorageKeyPart = "user";
const roleStorageKeyPart = "role";
const productStorageKeyPart = "product";
const creationStorageKeyPart = "Creation";
const editStorageKeyPart = "Edit";

const userEditStorageKey = userStorageKeyPart + editStorageKeyPart;
const roleEditStorageKey = roleStorageKeyPart + editStorageKeyPart;
const productEditStorageKey = productStorageKeyPart + editStorageKeyPart;
const jwtStorageKey = "jwt";
const currentUserStorageKey = "currentUser";
const currentUserLoginStorageKey = "currentUserLogin";
const currentUserRoleNameStorageKey = "currentUserRoleName";
const rolesStorageKey = "roles";
const modificationStorageKeyStorageKey = "modificationStorageKey";
const successModificationHttpResponseStorageKey = "successModificationHttpResponse";
const successUserCreationHttpResponseStorageKey = "successUserCreationHttpResponse";
const cartProductsStorageKey = "cartProducts";
const searchedProductNameStorageKey = "searchedProductName";

const authorityApi = "http://localhost:8080/api/v1/";
const usersApiUrl = authorityApi + "users";
const rolesApiUrl = authorityApi + "roles";
const productsApiUrl = authorityApi + "products";
const jwtsApiUrl = authorityApi + "jwts";

const contentTypePrefix = "application/vnd.rozetka.";
const contentTypeSuffix = "+json";

const userUuidDtoKey = "uuid";
const userRoleUuidDtoKey = "uuidRole";
const userLoginDtoKey = "login";
const userPasswordDtoKey = "password";
const userEmailDtoKey = "email";
const userFirstNameDtoKey = "firstName";
const userLastNameDtoKey = "lastName";
const userBirthdayDtoKey = "birthday";
const roleUuidDtoKey = "uuid";
const roleNameDtoKey = "name";
const productUuidDtoKey = "uuid";
const productNameDtoKey = "name";
const productQuantityDtoKey = "quantity";
const productPriceDtoKey = "price";
const productDiscountDtoKey = "discount";
const productDescriptionDtoKey = "description";
const productImgDtoKey = "img";

const adminRoleName = "admin";
const userRoleName = "user";

const innerHtmlEditSubmit = "Edit";

function redirectWithoutSpecificRole(roleName) {
    if (localStorage.getItem(currentUserRoleNameStorageKey) !== roleName) {
        location.href = "/";
    }
}

function setNavigation(rootFolderDestination, userFolderDestination, adminFolderDestination) {
    document.getElementById("header").innerHTML =
        `<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="/` + rootFolderDestination + `">
                    <img src="` + rootFolderDestination + `img/logo.png" alt="logo" width="50" height="50">
                </a>
    
                <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
    
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a id="navItem1" class="nav-link active"></a>
                        </li>
    
                        <li class="nav-item">
                            <a id="navItem2" class="nav-link active"></a>
                        </li>
                        
                        <li class="nav-item">
                            <a class="nav-link active" href="` + rootFolderDestination +`cart.html">Cart</a>
                        </li>
                        
                        <li class="nav-item">
                            <a class="nav-link active" href="` + rootFolderDestination + `about.html">About</a>
                        </li>
                    </ul>
    
                    <div class="d-flex">
                        <input id="searchInput" class="form-control me-2" type="search" placeholder="Search products" aria-label="Search">
                        <button id="searchButton" class="btn btn-outline-success" type="button">Search</button>
                    </div>
                </div>
            </div>
        </nav>`;

    document.getElementById("footer").innerHTML =
        `<footer class="bg-dark text-white text-center">
                <div class="container p-4 pb-0">
                    <section class="mb-4">
                        <a class="btn btn-outline-light btn-floating m-1" href="https://www.linkedin.com/in/yaroslavskyba" role="button">
                            <em class="fab fa-linkedin-in"></em>
                        </a>
            
                        <a class="btn btn-outline-light btn-floating m-1" href="https://github.com/yaroslavskybadev" role="button">
                            <em class="fab fa-github"></em>
                        </a>
            
                        <a class="btn btn-outline-light btn-floating m-1" href="https://gitlab.com/yaroslavskyba" role="button">
                            <em class="fab fa-gitlab"></em>
                        </a>
                    </section>
                </div>
                
                <div class="text-center p-3" style="background-color: #00000033">
                    © 2022 Copyright:
                    <a href="https://thispersondoesnotexist.com/">Rozetka</a>
                </div>
            </footer>`;

    const navItem1 = document.getElementById("navItem1");
    const navItem2 = document.getElementById("navItem2");
    const loginPath = rootFolderDestination + "login.html";

    if (!localStorage.getItem(jwtStorageKey)) {
        navItem1.innerHTML = "Login";
        navItem1.href = loginPath;

        navItem2.innerHTML = "Registration";
        navItem2.href = rootFolderDestination + "registration.html";
    } else {
        navItem1.innerHTML = "Profile";

        const roleName = localStorage.getItem(currentUserRoleNameStorageKey);

        if (roleName === adminRoleName) {
            navItem1.href = adminFolderDestination + "admin.html";
        } else if (roleName === userRoleName) {
            navItem1.href = userFolderDestination + "user.html";
        } else {
            alertMessage("Some errors occurred");
        }

        navItem2.innerHTML = "Logout";
        navItem2.href = loginPath;
        navItem2.onclick = function () {
            if (confirm("Do you want to log out?")) {
                xmlHttpRequest.open("DELETE", jwtsApiUrl);
                xmlHttpRequest.send(localStorage.getItem(jwtStorageKey));

                localStorage.clear();
                location.href = "login.html";
            }
        }
    }

    document.getElementById("searchButton").onclick = function() {
        localStorage.setItem(searchedProductNameStorageKey, document.getElementById("searchInput").value);
        location.href = "/";
    }
}

function setMainAttributes() {
    main.style.backgroundColor = "#201c24";
    main.className = "min-vh-100";
}

function setContainer(content) {
    const container = document.createElement("div");
    container.className = "container";
    container.innerHTML +=
        `<div class="row">
            <div class="col">
                <div class="card bg-dark text-white text-break" style="border-radius: 15px; border-color: #198754">` + content + `</div>
            </div>
        </div>`;

    main.append(container);
}

function alertMessage(type, message) {
    document.getElementById("alert").innerHTML =
        `<div class="alert alert-` + type + ` alert-dismissible" role="alert">
            ` + message +
            `<button class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="location.reload()"></button>
        </div>`;
}

function setModificationPage(headlineInnerHtml, submitInnerHtml) {
    setMainAttributes();

    document.getElementById("headline").innerHTML = headlineInnerHtml;
    document.getElementById("submit").innerHTML = submitInnerHtml;
}

function setFormControlOnchange(create) {
    for (const formControl of document.getElementsByClassName("form-control")) {
        formControl.onchange = function () {
            localStorage.setItem(localStorage.getItem(modificationStorageKeyStorageKey), JSON.stringify(create()));
        }
    }
}

function sendHttpRequest(method, url, headerName, headerValue, body) {
    xmlHttpRequest.open(method, url);
    xmlHttpRequest.setRequestHeader(headerName, headerValue);
    xmlHttpRequest.send(body);
}

function sendModificationHttpRequest(method, url, contentType, body) {
    xmlHttpRequest.open(method, url);
    xmlHttpRequest.setRequestHeader("Content-Type", contentType);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
    xmlHttpRequest.send(JSON.stringify(body));
}

function getProductDescription(product) {
    let description = "";

    if (product[productDescriptionDtoKey]) {
        description = product[productDescriptionDtoKey];
    }

    return description;
}

function getProductPrice(product) {
    let discountRatio = 1;

    if (product[productDiscountDtoKey]) {
        discountRatio = product[productDiscountDtoKey] / 100;
    }

    const power = 100;

    return Math.round(product[productPriceDtoKey] * discountRatio * product[productQuantityDtoKey] * power) / power;
}

function setProductAddingButtonOnclick(button, product, i) {
    button.onclick = function () {
        const cartProducts = localStorage.getItem(cartProductsStorageKey);

        let cartParsedProducts = [];

        if (cartProducts) {
            cartParsedProducts = JSON.parse(cartProducts);
        }

        if (cartParsedProducts.find(value => value[productUuidDtoKey] === product[productUuidDtoKey])) {
            location.href = "cart.html";
        } else {
            product[productQuantityDtoKey] = 1;
            cartParsedProducts.push(product);
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
