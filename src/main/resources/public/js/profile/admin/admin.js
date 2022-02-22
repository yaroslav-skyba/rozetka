const xmlHttpRequest = new XMLHttpRequest();

let users = null;

onload = function () {
    setNavigation("../../index.html", "../../img/logo.png", "../../cart.html", "../../about.html",
        "../../login.html", "../../registration.html", "admin.html", "../user.html");

    xmlHttpRequest.open("GET", usersApiUrl);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtKey));
    xmlHttpRequest.send();
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            if (xmlHttpRequest.responseURL === usersApiUrl) {
                users = JSON.parse(xmlHttpRequest.responseText);

                const currentUser = JSON.parse(localStorage.getItem(currentUserKey));
                document.getElementById("userName").innerHTML = currentUser["firstName"] + " " + currentUser["lastName"];

                xmlHttpRequest.open("GET", rolesApiUrl);
                xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtKey));
                xmlHttpRequest.send();
            } else if (xmlHttpRequest.responseURL === rolesApiUrl) {
                const roles = JSON.parse(xmlHttpRequest.responseText);
                const roleNameMap = new Map();

                for (let i = 0; i < roles.length; i++) {
                    roleNameMap.set(roles[i]["uuid"], roles[i]["nameRole"]);
                }

                localStorage.setItem(rolesKey, JSON.stringify([...roleNameMap].reduce((acc, val) => {
                    acc[val[0]] = val[1];
                    return acc;
                }, {})));

                const tableContent = document.getElementById("tableContent");

                for (let i = 0; i < users.length; i++) {
                    const tr = document.createElement("tr");

                    insertTd(i + 1, tr);
                    insertTd(users[i]["login"], tr);
                    insertTd(users[i]["email"], tr);
                    insertTd(users[i]["firstName"], tr);
                    insertTd(users[i]["lastName"], tr);
                    insertTd(users[i]["birthday"], tr);
                    insertTd(roleNameMap.get(users[i]["roleUuid"]), tr);

                    const buttonClasses = "btn btn-dark btn-outline-success";

                    const editButton = document.createElement("button");
                    editButton.textContent = "Edit";
                    editButton.className = buttonClasses;
                    editButton.onclick = function () {
                        location.href = "/profile/admin/edit.html?" + userToEditQueryParamKey + "=" + users[i]["login"];
                    }

                    const deleteButton = document.createElement("button");
                    deleteButton.textContent = "Delete";
                    deleteButton.className = buttonClasses;
                    deleteButton.onclick = function () {
                        if (confirm("Are you sure you want to delete this user?")) {
                            xmlHttpRequest.open("DELETE", usersApiUrl + "/" + users[i]["uuid"]);
                            xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtKey));
                            xmlHttpRequest.send();
                        }
                    };

                    const actionsTd = document.createElement("td");
                    actionsTd.append(editButton, " ", deleteButton);
                    tr.append(actionsTd);

                    tableContent.append(tr);
                }
            }
        } else if (xmlHttpRequest.status === 204) {
            alert("success", "A user has been successfully deleted");
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", "Some errors occurred while deleting a user");
        }
    }
}

function insertTd(fieldValue, tr) {
    const td = document.createElement("td");
    td.textContent = fieldValue;
    tr.append(td);
}