async function getUserCartItems() {
    const accessToken = sessionStorage.getItem('user_access_token');
    return $.ajax({
        url: `http://localhost:8080/api/v1/cart/getUserCartItems`,
        type: 'GET',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
        },
        credentials: 'include', // Để gửi cookie trong request
    }).then((data) => data);
}