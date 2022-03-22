const xmlHttpRequest = new XMLHttpRequest();

const userStorageKeyPrefix = "user_";
const creationStorageKeyPrefix = "creation";
const editStorageKeyPrefix = "edit";

const jwtStorageKey = "jwt";
const currentUserRoleNameStorageKey = "roleName";
const currentUserStorageKey = "currentUser";
const rolesStorageKey = "roles";

const authorityApi = "http://localhost:8080/api/v1/";
const rolesApiUrl = authorityApi + "roles";

const contentTypeRoot = "application/vnd.rozetka.";
const contentTypeSuffix = "+json";
const userContentType = contentTypeRoot + "user" + contentTypeSuffix;

const adminRoleName = "admin";
const userRoleName = "user";

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
    document.getElementById("main").style.backgroundColor = "#201c24";
}

function setContainer(content) {
    document.getElementById("main").innerHTML +=
        `<div class="container">
            <div class="row min-vh-100 align-items-center">
                <div class="col">
                    <div class="card bg-dark text-white" style="border-radius: 15px">
                        <div class="card-body">` + content + `</div>
                    </div>
                </div>
            </div>
        </div>`;
}

function alert(type, message) {
    document.getElementById("alert").innerHTML =
        `<div class="alert alert-` + type + ` alert-dismissible" role="alert">
            ` + message +
            `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="location.reload()"></button>
        </div>`;
}

function redirectWithoutRoles() {
    if (!localStorage.getItem(rolesStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }
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
