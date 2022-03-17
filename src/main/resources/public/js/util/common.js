const jwtStorageKey = "jwt";
const currentUserRoleNameStorageKey = "roleName";
const currentUserStorageKey = "currentUser";
const userToEditStorageKey = "userToEdit";
const rolesStorageKey = "roles";

const creationStorageKeyPrefix = "creation_";
const editStorageKeyPrefix = "edit_";

const roleUuidDtoKey = "uuid";
const roleNameDtoKey = "name";
const userUuidDtoKey = "uuid";
const userRoleUuidDtoKey = "uuidRole";
const userLoginDtoKey = "login";
const userPasswordDtoKey = "password";
const userEmailDtoKey = "email";
const userFirstNameDtoKey = "firstName";
const userLastNameDtoKey = "lastName";
const userBirthdayDtoKey = "birthday";

const authorityApi = "http://localhost:8080/api/v1/";
const usersApiUrl = authorityApi + "users";
const rolesApiUrl = authorityApi + "roles";
const jwtsApiUrl = authorityApi + "jwts";

const userMediaType = "application/vnd.rozetka.user+json";

const adminRoleName = "admin";
const userRoleName = "user";

const tableClasses = "w-100 table-dark";
const buttonClasses = "btn btn-dark btn-outline-success";

function setNavigation(brandHref, brandImgSrc, cartHref, aboutHref, loginHref, registrationHref, adminHref, userHref) {
    new Promise(resolve => setTimeout(resolve, 100)).then(() => {
        document.getElementById("header").innerHTML =
            `<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="` + brandHref +`">
                        <img src="` + brandImgSrc +`" alt="logo" width="50" height="50">
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
                                <a class="nav-link active" href="` + cartHref +`">Cart</a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link active" href="` + aboutHref +`">About</a>
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
            `<footer class="bg-dark text-center text-white">
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

        if (localStorage.getItem(jwtStorageKey) == null) {
            navItem1.innerHTML = "Login";
            navItem1.href = loginHref;

            navItem2.innerHTML = "Registration";
            navItem2.href = registrationHref;
        } else {
            navItem1.innerHTML = "Profile";

            const roleName = localStorage.getItem(currentUserRoleNameStorageKey);

            if (roleName === "admin") {
                navItem1.href = adminHref;
            } else if (roleName === "user") {
                navItem1.href = userHref;
            } else {
                alert("Some errors occurred");
            }

            navItem2.innerHTML = "Logout";
            navItem2.href = loginHref;
            navItem2.onclick = function () {
                localStorage.removeItem(jwtStorageKey);
                localStorage.removeItem(currentUserRoleNameStorageKey);
            }
        }
    });
}

function setMainAttributes() {
    const main = document.getElementById("main");
    main.className = "min-vh-100";
    main.style.backgroundColor = "#201c24";
}

function setContainer(content) {
    document.getElementById("main").innerHTML +=
        `<div class="container pt-3">
            <div class="row justify-content-center">
                <div class="col">
                    <div class="card bg-dark" style="border-radius: 15px">
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
