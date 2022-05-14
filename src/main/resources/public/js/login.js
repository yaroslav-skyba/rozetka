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
                <button id="submit" class="btn btn-dark btn-outline-success">Log in</button>
            </div>
            
            <div id="alert" class="mt-3"></div>
        </div>
    `);

    const successUserCreationHttpResponse = localStorage.getItem(successUserCreationHttpResponseStorageKey);

    if (successUserCreationHttpResponse) {
        alertMessage("success", successUserCreationHttpResponse);
        localStorage.removeItem(successUserCreationHttpResponseStorageKey);
    }

    document.getElementById("submit").onclick = function() {
        const login = document.getElementById("login").value;
        localStorage.setItem(currentUserLoginStorageKey, login);

        sendHttpRequest(
            "POST", jwtsApiUrl, "Content-Type", contentTypePrefix + "login" + contentTypeSuffix,
            JSON.stringify({username:login, password:document.getElementById("password").value})
        );
    }
}

xmlHttpRequest.onreadystatechange = function() {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            if (xmlHttpRequest.responseURL === userApiUrl) {
                const user = JSON.parse(xmlHttpRequest.responseText);
                const dateFillingZero = "0";

                if (user[userBirthdayDtoKey][1].toString().length === 1) {
                    user[userBirthdayDtoKey][1] = dateFillingZero + user[userBirthdayDtoKey][1];
                }

                if (user[userBirthdayDtoKey][2].toString().length === 1) {
                    user[userBirthdayDtoKey][2] = dateFillingZero + user[userBirthdayDtoKey][2];
                }

                user[userBirthdayDtoKey] = user[userBirthdayDtoKey].join("-");

                roleApiUrl = rolesApiUrl + "/" + user[userRoleUuidDtoKey];

                localStorage.setItem(currentUserStorageKey, JSON.stringify(user));
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
