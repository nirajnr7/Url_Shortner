function shortenUrl() {
    var originalUrl = document.getElementById("originalUrl").value;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/miniUrl", true);
    xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                document.getElementById("shortenedUrlDiv").innerHTML = "<p>Shortened URL: <a href='" + response.shortenedUrl + "'>" + response.shortenedUrl + "</a></p>";
            } else {
                console.error("Error:", xhr.statusText);
            }
        }
    };
    xhr.send(originalUrl);
}
