/**
 * Chứa API chung mà các file html gọi
 */
async function getGenres() {
    return $.ajax({
        url: 'http://localhost:8080/api/v1/genre/grouped',
        type: 'GET',
        contentType: 'application/json',
        credentials: 'include', // Để gửi cookie trong request
    }).then((data) => data);
}