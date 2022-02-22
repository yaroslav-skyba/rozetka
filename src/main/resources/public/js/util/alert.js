function alert(type, message) {
    const div = document.createElement('div');
    div.innerHTML =
        `<div class="alert alert-` + type + ` alert-dismissible" role="alert">
            ` + message +
        `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="location.reload()"></button>
        </div>`;

    document.getElementById("alert").append(div);
}