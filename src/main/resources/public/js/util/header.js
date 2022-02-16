function setHeader(brandHref, brandImgSrc, cartHref, aboutHref) {
    document.getElementById("header").innerHTML =
        `<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="` + brandHref +`">
                    <img src="` + brandImgSrc +`" alt="logo" width="50" height="50">
                </a>
    
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
    
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a id="navItem1" class="nav-link active"></a>
                        </li>
    
                        <li class="nav-item">
                            <a id="navItem2" class="nav-link active"></a>
                        </li>
                        
                        <li class="nav-item">
                            <a class="nav-link active" href="` + cartHref +`">Cart</a>
                        </li>
                        
                        <li class="nav-item">
                            <a class="nav-link active" href="` + aboutHref +`">About</a>
                        </li>
                    </ul>
    
                    <form class="d-flex">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-outline-success" type="button">Search</button>
                    </form>
                </div>
            </div>
        </nav>`;
}