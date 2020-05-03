var modal = document.getElementById("mySidenav");
var profile_icon = document.getElementById("profile_icon");

function openNav() {
    modal && (modal.style.width = "260px");
}

window.onclick = function (event) {
    if (event.target !== modal && event.target !== profile_icon) {
        closeNav()
    }
}

function closeNav() {
    modal && (modal.style.width = "0");
}