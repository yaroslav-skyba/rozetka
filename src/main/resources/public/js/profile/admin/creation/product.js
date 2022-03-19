onload = function () {
    redirectUnauthorizedModification();
    setProductModificationForm("create a product", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
    }
}
