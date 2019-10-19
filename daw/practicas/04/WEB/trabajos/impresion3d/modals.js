function pauseVideos() {
    var x = document.getElementsByTagName("video");
    var i;
    for (i = 0; i < x.length; i++) {
        x[i].pause();
    }
}

function modalFunctionImg(element) {
    pauseVideos();

    document.getElementById("modalImg").src = element.src;

    document.getElementById("modalVideo").style.display = "none";
    document.getElementById("modalImg").style.display = "block";
    document.getElementById("modal01").style.display = "block";
}

function modalFunctionVideo(element) {
    pauseVideos();

    var videoModal = document.getElementById("modalVideo");

    videoModal.src = element.src;
    videoModal.autoplay = true;
    videoModal.muted = true;
    videoModal.loop = true;

    document.getElementById("modalImg").style.display = "none";
    videoModal.style.display = "block";
    document.getElementById("modal01").style.display = "block";
}

function closeModal() {
    document.getElementById("modal01").style.display = "none";
    document.getElementById("modalVideo").style.display = "none";
    document.getElementById("modalImg").style.display = "none";

    var x = document.getElementsByTagName("video");
    var i;
    for (i = 0; i < x.length; i++) {
        x[i].play();
    }

}