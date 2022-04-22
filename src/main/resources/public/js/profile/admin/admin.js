let users;

let userToDeleteUuid;
let roleToDeleteUuid;
let productToDeleteUuid;

let userApiUrl;
let roleApiUrl;
let productApiUrl;

onload = function () {
    redirectWithoutSpecificRole(adminRoleName);

    setNavigation("../../", "../", "");
    setMainAttributes();
    setContainer(`
        <div class="card-body">
            <div id="alert"></div>
            Welcome, <span id="userName"></span>
        </div>
    `);
    setContainer(`
        <div class="card-body">
            <table class="table table-dark table-hover">
                <thead>
                    <tr>
                        <th class="col-1">#</th>
                        <th>Login</th>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th class="col-2">Birthday</th>
                        <th class="col-1">Role</th>
                        <th class="col-2">Actions</th>
                    </tr>
                </thead>
        
                <tbody id="userTableContent"></tbody>
            </table>
            
            <button class="btn btn-dark btn-outline-success" onclick="location.href = '/profile/admin/creation/user.html'">
                Create user
            </button>
        </div>
    `);
    setContainer(`
        <div class="card-body">
            <table class="table table-dark table-hover">
                <thead>
                    <tr>
                        <th class="col-1">#</th>
                        <th>Name</th>
                        <th class="col-2">Actions</th>
                    </tr>
                </thead>
        
                <tbody id="roleTableContent"></tbody>
            </table>
            
            <button class="btn btn-dark btn-outline-success" onclick="location.href = '/profile/admin/creation/role.html'">
                Create role
            </button>
        </div>
    `)
    setContainer(`
        <div class="card-body">
            <table class="table table-dark table-hover">    
                <thead>
                    <tr>
                        <th class="col-1">#</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th class="col-2">Actions</th>
                    </tr>
                </thead>
        
                <tbody id="productTableContent"></tbody>
            </table>
            
            <button class="btn btn-dark btn-outline-success" onclick="location.href = '/profile/admin/creation/product.html'">
                Create product
            </button>
        </div>
    `);

    const successModificationHttpResponse = localStorage.getItem(successModificationHttpResponseStorageKey);

    if (successModificationHttpResponse) {
        localStorage.removeItem(localStorage.getItem(modificationStorageKeyStorageKey));
        localStorage.removeItem(modificationStorageKeyStorageKey);
        localStorage.removeItem(successModificationHttpResponseStorageKey);

        alertMessage("success", successModificationHttpResponse);
    } else {
        document.getElementById("alert").innerHTML = "";
    }

    sendHttpRequest("GET", usersApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null);
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            if (xmlHttpRequest.responseURL === usersApiUrl) {
                users = JSON.parse(xmlHttpRequest.responseText);

                const currentUser = JSON.parse(localStorage.getItem(currentUserStorageKey));
                document.getElementById("userName").innerHTML = currentUser[userFirstNameDtoKey] + " " + currentUser[userLastNameDtoKey];

                sendHttpRequest("GET", rolesApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null);
            } else if (xmlHttpRequest.responseURL === rolesApiUrl) {
                localStorage.setItem(rolesStorageKey, xmlHttpRequest.responseText);

                const roles = JSON.parse(xmlHttpRequest.responseText);

                for (let i = 0; i < users.length; i++) {
                    users[i][userBirthdayDtoKey] = new Date(users[i][userBirthdayDtoKey]).toISOString().slice(0, 10);

                    const tr = document.createElement("tr");
                    appendTd(i + 1, tr);
                    appendTd(users[i][userLoginDtoKey], tr);
                    appendTd(users[i][userEmailDtoKey], tr);
                    appendTd(users[i][userFirstNameDtoKey], tr);
                    appendTd(users[i][userLastNameDtoKey], tr);
                    appendTd(users[i][userBirthdayDtoKey], tr);
                    appendTd(roles.find(role => role[roleUuidDtoKey] === users[i][userRoleUuidDtoKey])[roleNameDtoKey], tr);

                    const actionsTd = document.createElement("td");

                    appendButton("Edit", function () {
                        localStorage.setItem(userEditStorageKey, JSON.stringify(users[i]));
                        location.href = "/profile/admin/edit/user.html";
                    }, actionsTd);
                    actionsTd.append(" ");
                    appendButton("Delete", function () {
                        if (confirm("Are you sure you want to delete this user?")) {
                            userToDeleteUuid = users[i][userUuidDtoKey];
                            userApiUrl = usersApiUrl + "/" + userToDeleteUuid;

                            sendHttpRequest("DELETE", userApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null);
                        }
                    }, actionsTd);

                    tr.append(actionsTd);
                    document.getElementById("userTableContent").append(tr);
                }

                for (let i = 0; i < roles.length; i++) {
                    const tr = document.createElement("tr");
                    appendTd(i + 1, tr);
                    appendTd(roles[i][roleNameDtoKey], tr);

                    const actionsTd = document.createElement("td");

                    appendButton("Edit", function () {
                        localStorage.setItem(roleEditStorageKey, JSON.stringify(roles[i]));
                        location.href = "/profile/admin/edit/role.html";
                    }, actionsTd);
                    actionsTd.append(" ");
                    appendButton("Delete", function () {
                        if (confirm("Are you sure you want to delete this role?")) {
                            roleToDeleteUuid = roles[i][roleUuidDtoKey];
                            roleApiUrl = rolesApiUrl + "/" + roleToDeleteUuid;

                            sendHttpRequest("DELETE", roleApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null);
                        }
                    }, actionsTd);

                    tr.append(actionsTd);
                    document.getElementById("roleTableContent").append(tr);
                }

                sendHttpRequest("GET", productsApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null);
            } else if (xmlHttpRequest.responseURL === productsApiUrl) {
                const products = JSON.parse(xmlHttpRequest.responseText);

                for (let i = 0; i < products.length; i++) {
                    const productNameA = document.createElement("a");
                    productNameA.innerHTML = products[i][productNameDtoKey];
                    productNameA.href = "/product.html?" + productUuidDtoKey + "=" + products[i][productUuidDtoKey];
                    const td = document.createElement("td");
                    td.append(productNameA)

                    const tr = document.createElement("tr");
                    appendTd(i + 1, tr);
                    tr.append(td);
                    appendTd(products[i][productPriceDtoKey], tr);
                    appendTd(products[i][productQuantityDtoKey], tr);

                    const actionsTd = document.createElement("td");

                    appendButton("Edit", function () {
                        localStorage.setItem(productEditStorageKey, JSON.stringify(products[i]));
                        location.href = "/profile/admin/edit/product.html";
                    }, actionsTd);
                    actionsTd.append(" ");
                    appendButton("Delete", function () {
                        if (confirm("Are you sure you want to delete this product?")) {
                            productToDeleteUuid = products[i][productUuidDtoKey];
                            productApiUrl = productsApiUrl + "/" + productToDeleteUuid;

                            sendHttpRequest("DELETE", productApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null);
                        }
                    }, actionsTd);

                    tr.append(actionsTd);
                    document.getElementById("productTableContent").append(tr);
                }
            }
        } else if (xmlHttpRequest.status === 204) {
            if (xmlHttpRequest.responseURL === userApiUrl) {
                const userToEdit = localStorage.getItem(userEditStorageKey);

                if (userToEdit && JSON.parse(userToEdit)[userUuidDtoKey] === userToDeleteUuid) {
                    localStorage.removeItem(userEditStorageKey);
                }

                location.reload();
            } else if (xmlHttpRequest.responseURL === roleApiUrl) {
                const roleToEdit = localStorage.getItem(roleEditStorageKey);

                if (roleToEdit && JSON.parse(roleToEdit)[roleUuidDtoKey] === roleToDeleteUuid) {
                    localStorage.removeItem(roleEditStorageKey);
                }

                location.reload();
            } else if (xmlHttpRequest.responseURL === productApiUrl) {
                const productToEdit = localStorage.getItem(productEditStorageKey);

                if (productToEdit && JSON.parse(productToEdit)[productUuidDtoKey] === productToDeleteUuid) {
                    localStorage.removeItem(productEditStorageKey);
                }

                location.reload();
            }
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}

function appendTd(textContent, tr) {
    const td = document.createElement("td");
    td.textContent = textContent;
    tr.append(td);
}

function appendButton(textContent, onclick, actionsTd) {
    const button = document.createElement("button");
    button.textContent = textContent;
    button.onclick = onclick;
    button.className = "btn btn-dark btn-outline-success";

    actionsTd.append(button);
}
