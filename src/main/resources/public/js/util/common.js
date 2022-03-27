const xmlHttpRequest = new XMLHttpRequest();

const main = document.getElementById("main");

const userStorageKeyPrefix = "user";
const roleStorageKeyPrefix = "role";
const productStorageKeyPart = "product";

const creationStorageKeyPart = "Creation";
const editStorageKeySuffix = "Edit";

const jwtStorageKey = "jwt";
const currentUserRoleNameStorageKey = "roleName";
const currentUserStorageKey = "currentUser";
const rolesStorageKey = "roles";

const authorityApi = "http://localhost:8080/api/v1/";
const usersApiUrl = authorityApi + "users";
const rolesApiUrl = authorityApi + "roles";
const productsApiUrl = authorityApi + "products";

const contentTypeRoot = "application/vnd.rozetka.";
const contentTypeSuffix = "+json";
const userContentType = contentTypeRoot + "user" + contentTypeSuffix;

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

const adminRoleName = "admin";
const userRoleName = "user";

function redirectUnauthorized(roleName) {
    if (localStorage.getItem(currentUserRoleNameStorageKey) !== roleName) {
        location.href = "/";
    }
}

function setNavigation(rootDestination, userDestination, adminDestination) {
    new Promise(resolve => setTimeout(resolve, 100)).then(() => {
        document.getElementById("header").innerHTML =
            `<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="` + rootDestination + `index.html">
                        <img src="` + rootDestination + `img/logo.png" alt="logo" width="50" height="50">
                    </a>
        
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
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
                                <a class="nav-link active" href="` + rootDestination +`cart.html">Cart</a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link active" href="` + rootDestination + `about.html">About</a>
                            </li>
                        </ul>
        
                        <form class="d-flex">
                            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                            <button class="btn btn-outline-success" type="button">Search</button>
                        </form>
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
                    <a class="text-white" href="https://thispersondoesnotexist.com/">Rozetka</a>
                </div>
            </footer>`;

        const navItem1 = document.getElementById("navItem1");
        const navItem2 = document.getElementById("navItem2");
        const loginPath = rootDestination + "login.html";

        if (localStorage.getItem(jwtStorageKey) == null) {
            navItem1.innerHTML = "Login";
            navItem1.href = loginPath;

            navItem2.innerHTML = "Registration";
            navItem2.href = rootDestination + "registration.html";
        } else {
            navItem1.innerHTML = "Profile";

            const roleName = localStorage.getItem(currentUserRoleNameStorageKey);

            if (roleName === adminRoleName) {
                navItem1.href = adminDestination + "admin.html";
            } else if (roleName === userRoleName) {
                navItem1.href = userDestination + "user.html";
            } else {
                alert("Some errors occurred");
            }

            navItem2.innerHTML = "Logout";
            navItem2.href = loginPath;
            navItem2.onclick = function () {
                localStorage.removeItem(jwtStorageKey);
                localStorage.removeItem(currentUserRoleNameStorageKey);
            }
        }
    });
}

function setMainAttributes() {
    main.style.backgroundColor = "#201c24";
    main.className = "min-vh-100";
}

function configModificationPage(headlineInnerHtml, submitInnerHtml) {
    setMainAttributes();
    document.getElementById("headline").innerHTML = headlineInnerHtml;

    submit = document.getElementById("submit");
    submit.innerHTML = submitInnerHtml;
}

function setFormControlElementOnchange(storageKey, create) {
    for (const formControlElement of document.getElementsByClassName("form-control")) {
        formControlElement.onchange = function () {
            localStorage.setItem(storageKey, JSON.stringify(create()));
        }
    }
}

function setContainer(content) {
    main.innerHTML +=
        `<div class="container">
            <div class="row">
                <div class="col">
                    <div class="card bg-dark text-white" style="border-radius: 15px; border-color: #198754">
                        <div class="card-body">` + content + `</div>
                    </div>
                </div>
            </div>
        </div>`;
}

function sendModificationHttpRequest(body, httpMethod, url, contentType) {
    if (body) {
        xmlHttpRequest.open(httpMethod, url);
        xmlHttpRequest.setRequestHeader("Content-Type", contentType);
        xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
        xmlHttpRequest.send(JSON.stringify(body));
    }
}

function alert(type, message) {
    document.getElementById("alert").innerHTML =
        `<div class="alert alert-` + type + ` alert-dismissible" role="alert">
            ` + message +
            `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="location.reload()"></button>
        </div>`;
}

function areFormInputsValid(formControlElement, formOutlineElement) {
    if (!formControlElement.checkValidity()) {
        alert("danger", formOutlineElement.getElementsByClassName("invalid-feedback")[0].innerHTML);
        return false;
    }

    const maxElementLength = 255;

    if (formControlElement.value.length > maxElementLength) {
        alert("danger", "A field length should be equal or less than " + maxElementLength + " symbols");
        return false;
    }

    return true;
}
