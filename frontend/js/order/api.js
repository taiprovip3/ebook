async function getUserOrders(orderStatus) {
    const accessToken = window.sessionStorage.getItem('user_access_token');
    if(!orderStatus) {
        return $.ajax({
            url: 'http://localhost:8080/api/v1/order/getUserOrders',
            type: 'GET',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
            },
            contentType: 'application/json',
            credentials: 'include', // Để gửi cookie trong request
        }).then((data) => data);
    }
    return $.ajax({
        url: `http://localhost:8080/api/v1/order/getUserOrders/${orderStatus}`,
        type: 'GET',
        beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
		},
        contentType: 'application/json',
        credentials: 'include', // Để gửi cookie trong request
    }).then((data) => data);
}