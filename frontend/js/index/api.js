/**
 * 
 * Chứa các API riêng mà index.html gọi
 */
const accessToken = window.sessionStorage.getItem('user_access_token');
async function getTopSoldBooks() {// ko cần token
    return $.ajax({
        url: 'http://localhost:8080/api/v1/book/topSoldBooks',
        type: 'GET',
        contentType: 'application/json',
        credentials: 'include', // Để gửi cookie trong request
    }).then((data) => data);
}
async function getFlashSaleBooks() {
    return $.ajax({
        url: 'http://localhost:8080/api/v1/book/flashSaleBooks',
        type: 'GET',
        contentType: 'application/json',
        credentials: 'include', // Để gửi cookie trong request
    }).then((data) => data);
}
async function getBooks() {
    return $.ajax({
        url: 'http://localhost:8080/api/v1/book/list',
        type: 'GET',
        contentType: 'application/json',
        credentials: 'include', // Để gửi cookie trong request
    }).then((data) => data);
}