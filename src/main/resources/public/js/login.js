let userApiUrl;
let roleApiUrl;

onload = function() {
    if (localStorage.getItem(jwtStorageKey)) {
        location.href = "/";
    }

    setNavigation("", "profile/", "profile/admin/");
    setMainAttributes();
    setContainer(`
        <div class="card-body p-5">
            <h2 class="text-uppercase text-center mb-5">login</h2>

            <div class="mb-4">
                <input id="login" class="form-control form-control-lg"/>
                <label for="login">Your login</label>
                
            </div>

            <div class="mb-4">
                <input id="password" class="form-control form-control-lg" type="password"/>
                <label for="password">Your password</label>
                <div id="password" class="invalid-feedback">Please, type your password</div>
            </div>

            <div class="d-flex justify-content-center">
                <button id="submit" class="btn btn-dark btn-outline-success">Login</button>
            </div>
            
            <div id="alert" class="mt-3"></div>
        </div>
    `);

    document.getElementById("submit").onclick = function() {
        const login = document.getElementById("login").value;
        localStorage.setItem(currentUserLoginStorageKey, login);

        sendHttpRequest(
            "POST", authorityApi + "logins", "Content-Type", contentTypePrefix + "login" + contentTypeSuffix,
            JSON.stringify({username:login, password:document.getElementById("password").value})
        );
    }
}

xmlHttpRequest.onreadystatechange = function() {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            if (xmlHttpRequest.responseURL === userApiUrl) {
                roleApiUrl = rolesApiUrl + "/" + JSON.parse(xmlHttpRequest.responseText)[userRoleUuidDtoKey];

                localStorage.setItem(currentUserStorageKey, xmlHttpRequest.responseText);
                sendHttpRequest("GET", roleApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null);
            } else if (xmlHttpRequest.responseURL === roleApiUrl) {
                localStorage.setItem(currentUserRoleNameStorageKey, JSON.parse(xmlHttpRequest.responseText)[roleNameDtoKey]);
                location.href = "/";
            }
        } else if (xmlHttpRequest.status === 201) {
            userApiUrl = usersApiUrl + "/" + localStorage.getItem(currentUserLoginStorageKey);

            localStorage.setItem(jwtStorageKey, xmlHttpRequest.responseText);
            sendHttpRequest("GET", userApiUrl, "Authorization", localStorage.getItem(jwtStorageKey), null);
        } else if (xmlHttpRequest.status === 401) {
            localStorage.removeItem(currentUserLoginStorageKey);
            alertMessage("danger", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 403) {
            alertMessage("danger", "You are not authorized");
        } else if (xmlHttpRequest.status === 404) {
            localStorage.removeItem(currentUserLoginStorageKey);
            localStorage.removeItem(jwtStorageKey);
            localStorage.removeItem(currentUserStorageKey);

            alertMessage("danger", "No user was found with this login");
        }
    }
}
