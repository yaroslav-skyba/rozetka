function setUserAdminPage(storageKey, headlineInnerHtml, submitInnerHtml, httpMethod) {
    redirectWithoutSpecificRole(adminRoleName);

    localStorage.setItem(modificationStorageKeyStorageKey, storageKey);

    setUserPage(adminRoleName, "../../../", "../../", "../", headlineInnerHtml, submitInnerHtml, httpMethod);
    document.getElementById("roleDiv").hidden = false;
}
