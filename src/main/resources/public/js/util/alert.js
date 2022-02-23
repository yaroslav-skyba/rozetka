function alert(type, message) {
    document.getElementById("alert").innerHTML =
        `<div class="alert alert-` + type + ` alert-dismissible" role="alert">
            ` + message +
            `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="location.reload()"></button>
        </div>`;
}