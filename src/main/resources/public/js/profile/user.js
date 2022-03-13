onload = function () {
    setNavigation("../index.html", "../img/logo.png", "../cart.html", "../about.html", "../login.html",
        "../registration.html", "admin/admin.html", "user.html");
    setUserForm();
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(200, editStorageKeyPrefix);
}

document.getElementById("headline").innerHTML = "Edit your profile";
document.getElementById("submit").innerHTML = "Save";
