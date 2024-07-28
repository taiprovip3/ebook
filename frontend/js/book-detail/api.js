async function getBookById(bookId) {
    return fetch(`http://localhost:8080/api/v1/book/${bookId}`)
        .then(response => response.json());
}
async function getRatingsOfBook(bookId) {
    return fetch(`http://localhost:8080/api/v1/book/getRatingsOfBook/${bookId}`)
        .then(response => response.json());
}
async function getTotalSoldOfBook(bookId) {
    return fetch(`http://localhost:8080/api/v1/book/getTotalSoldOfBook/${bookId}`)
        .then(response => response.json());
}
async function addToCart(bookId) {
    const quantity = $('#quantity').val();
    const accessToken = sessionStorage.getItem('user_access_token');
    $.ajax({
        url: 'http://localhost:8080/api/v1/cart/cartItem/addToCart',
        method: 'PUT',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
        },
        credentials: 'include', // Để gửi cookie trong request
        contentType: 'application/json',
        data: JSON.stringify({ bookId: Number(bookId), quantity: Number(quantity) }),
        success: function(response) {
            toastr.success(response, 'Thành công');
        }, error: function(error) {
            console.error(error);
            toastr.error(error.responseText, 'Error!')
        }
    });
}