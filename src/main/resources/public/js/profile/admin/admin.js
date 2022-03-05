const xmlHttpRequest = new XMLHttpRequest();

let users = null;

onload = function () {
    setNavigation("../../index.html", "../../img/logo.png", "../../cart.html", "../../about.html",
        "../../login.html", "../../registration.html", "admin.html", "../user.html");
    sendHttpRequest("GET", usersApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            if (xmlHttpRequest.responseURL === usersApiUrl) {
                users = JSON.parse(xmlHttpRequest.responseText);

                const currentUser = JSON.parse(localStorage.getItem(currentUserStorageKey));
                document.getElementById("userName").innerHTML = currentUser[userFirstNameDtoKey] + " " + currentUser[userLastNameDtoKey];

                sendHttpRequest("GET", rolesApiUrl);
            } else if (xmlHttpRequest.responseURL === rolesApiUrl) {
                const roles = JSON.parse(xmlHttpRequest.responseText);
                const roleNameMap = new Map();

                for (let i = 0; i < roles.length; i++) {
                    roleNameMap.set(roles[i][roleUuidDtoKey], roles[i][roleNameDtoKey]);
                }

                localStorage.setItem(rolesStorageKey, JSON.stringify([...roleNameMap].reduce((acc, val) => {
                    acc[val[0]] = val[1];
                    return acc;
                }, {})));

                for (let i = 0; i < users.length; i++) {
                    const tr = document.createElement("tr");

                    insertTd(i + 1, tr);
                    insertTd(users[i][userLoginDtoKey], tr);
                    insertTd(users[i][userEmailDtoKey], tr);
                    insertTd(users[i][userFirstNameDtoKey], tr);
                    insertTd(users[i][userLastNameDtoKey], tr);
                    insertTd(users[i][userBirthdayDtoKey], tr);
                    insertTd(roleNameMap.get(users[i][userRoleUuidDtoKey]), tr);

                    const actionsTd = document.createElement("td");

                    createButton("Edit", function () {
                        localStorage.setItem(userToEditStorageKey, JSON.stringify(users[i]));
                        location.href = "/profile/admin/edit.html";
                    }, actionsTd);
                    actionsTd.append(" ");
                    createButton("Delete", function () {
                        if (confirm("Are you sure you want to delete this user?")) {
                            sendHttpRequest("DELETE", usersApiUrl + "/" + users[i][userUuidDtoKey]);
                        }
                    }, actionsTd);

                    tr.append(actionsTd);

                    document.getElementById("tableContent").append(tr);
                }
            }
        } else if (xmlHttpRequest.status === 204) {
            alert("success", "A user has been successfully deleted");
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", "Some errors occurred while deleting a user");
        }
    }
}

function sendHttpRequest(method, url) {
    xmlHttpRequest.open(method, url);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
    xmlHttpRequest.send();
}

function insertTd(fieldValue, tr) {
    const td = document.createElement("td");
    td.textContent = fieldValue;
    tr.append(td);
}

function createButton(textContent, onclick, actionsTd) {
    const button = document.createElement("button");
    button.textContent = textContent;
    button.className = buttonClasses;
    button.onclick = onclick;

    actionsTd.append(button);
}