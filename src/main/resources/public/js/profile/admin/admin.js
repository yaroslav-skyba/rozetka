const xmlHttpRequest = new XMLHttpRequest();
const authority = "http://localhost:8080/api/v1/";
const usersGETUrl = authority + "users";
const rolesGETUrl = authority + "roles";

let users = null;

onload = function () {
    setNavigation("../../index.html", "../../img/logo.png", "../../cart.html", "../../about.html",
        "../../login.html", "../../registration.html", "admin.html", "../user.html");

    xmlHttpRequest.open("GET", usersGETUrl);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtKey));
    xmlHttpRequest.send();
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            if (xmlHttpRequest.responseURL === usersGETUrl) {
                users = JSON.parse(xmlHttpRequest.responseText);

                const currentUser = JSON.parse(localStorage.getItem("user"));
                document.getElementById("userName").innerHTML = currentUser["firstName"] + " " + currentUser["lastName"];

                xmlHttpRequest.open("GET", rolesGETUrl);
                xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtKey));
                xmlHttpRequest.send();
            } else if (xmlHttpRequest.responseURL === rolesGETUrl) {
                const roles = JSON.parse(xmlHttpRequest.responseText);
                const roleNameMap = new Map();

                for (let i = 0; i < roles.length; i++) {
                    roleNameMap.set(roles[i]["uuid"], roles[i]["nameRole"]);
                }

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
                        location.href = "/profile/admin/edit.html?login=" + users[i]["login"];
                    }

                    const deleteButton = document.createElement("button");
                    deleteButton.textContent = "Delete";
                    deleteButton.className = buttonClasses;
                    deleteButton.onclick = function () {
                        if (confirm("Are you sure you want to delete this user?")) {
                            xmlHttpRequest.open("DELETE", usersGETUrl + "/" + users[i]["uuid"]);
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

function alert(type, message) {
    const div = document.createElement('div');
    div.innerHTML =
        `<div class="alert alert-` + type + ` alert-dismissible" role="alert">
            ` + message +
            `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="location.reload()"></button>
        </div>`;

    document.getElementById("alert").append(div);
}