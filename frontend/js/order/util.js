// var orders; // đã khai báo ở trang order.html
async function reAddToCart(bookId, quantity) {
    console.log('bookId=', bookId);
    console.log('quantity=', quantity);
    const accessToken = sessionStorage.getItem('user_access_token');
    if(!accessToken) {
        toastr.error('Bạn chưa đăng nhập. Vui lòng đăng nhập', 'Tài khoản')
        return;
    }
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
            setTimeout(() => {
                window.location.href = './cart.html';
            }, 800);
        }, error: function(error) {
            console.error(error);
            toastr.error(error.responseText, 'Error!')
        }
    });
}
function cancelOrder(orderId) {
    if (confirm('Hủy đơn hàng đồng nghĩa với việc các sản phẩm khác trong cùng một đơn cũng sẽ bị hủy, bạn chắc chứ?') == true) {
        const accessToken = window.sessionStorage.getItem('user_access_token');
        $.ajax({
            url: `http://localhost:8080/api/v1/order/cancelOrder/${orderId}`,
            type: 'DELETE',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
            },
            credentials: 'include', // Để gửi cookie trong request,
            success: function(response) {
                toastr.success(response, 'Destroy Order');
            }, error: function(error) {
                toastr.error(error.responseText, 'Error');
                console.error(error);
            }, complete: function() {
                renderOrders();
            }
        });      
    }
}
async function orderDetail(orderIndex, orderItemBookIndex) {
    $("#orderDetailModal").modal('toggle');
    const order = orders[orderIndex];
    const orderItemBook = order.orderItemBooks[orderItemBookIndex];

    // Tạo phần HTML cho roadmap
    let roadMapHTML = '';
    order.orderStatuses.forEach(status => {
        roadMapHTML += `
            <div class="text-center">
                <i class="${status.classIcon} fa-2x text-success"></i><br>
                <span>${status.description}</span><br>
                <span class="text-secondary small">${new Date(status.time).toLocaleString()}</span>
            </div>
            ${status !== order.orderStatuses[order.orderStatuses.length - 1] ? 
                '<div class="position-relative mb-2" style="height: 50px;"><div class="vl" style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);"></div></div>' : ''
            }
        `;
    });
    // Tạo phần HTML cho thông tin sản phẩm
    const book = orderItemBook.book;
    const orderItem = orderItemBook.orderItem;
    const productInfoHTML = `
        <div class="ps-2">
            <h5>Thông Tin Sản Phẩm</h5>
            <span class="d-block text-bg-danger">Mã đơn hàng: <span class="fw-bold">#${order.order.id}</span></span>
            <span class="d-block">Đặt vào lúc: <span class="fw-bold">${formatDate(order.order.orderDate)}</span></span>
            <span class="d-block">Ghi chú: <span class="fst-italic">${order.order.note}</span></span>
            <span class="d-block">Phương thức thanh toán: <span class="fst-italic">${order.order.paymentMethod}</span></span>
            <span class="d-block">Địa chỉ nhận hàng: <span class="fst-italic">${order.order.deliveryAddress}</span></span>
            <hr>
            <span class="d-block text-bg-danger">Mã sách: <span class="fw-bold">#${book.id}</span></span>
            <span class="d-block">Số lượng đặt: <span class="fw-bold">x${orderItem.quantity} cuốn</span></span>
            <span class="d-block">CBM: <span class="fw-bold">${orderItem.totalWeightCBM} kg</span> (D x R x C) / 6000</span>
            <hr>
            <h5>Thông Tin Vận Chuyển (nếu có):</h5>
            <span class="d-block text-bg-danger">Mã vận chuyển: <span class="fw-bold">#${order.shipOrder ? order.shipOrder.id : 'ĐH chưa xác nhận'}</span></span>
            <span class="d-block">Billing Address: <span class="fw-bold">${order.shipOrder ? order.shipOrder.billingAddress : 'ĐH chưa xác nhận'}</span></span>
            <span class="d-block">Delivery Address: <span class="fw-bold">${order.shipOrder ? order.shipOrder.deliveryAddress : 'ĐH chưa xác nhận'}</span></span>
            <span class="d-block">Trọng lượng tối đa: <span class="fw-bold">${order.shipOrder ? formatNumber(order.shipOrder.maxWeight) : 'ĐH chưa xác nhận'} gram</span></span>
            <span class="d-block">Tiền thu (COD): <span class="fw-bold">${order.shipOrder ? order.shipOrder.codPrice.toLocaleString() : 'ĐH chưa xác nhận'} đ</span></span>
            <span class="d-block text-truncate">Chữ ký nhận hàng: <span class="fw-bold">${order.shipOrder ? order.shipOrder.signature : 'ĐH chưa xác nhận'}</span></span>
        </div>
    `;

    // Render dữ liệu vào DOM
    $('#roadMap').html(roadMapHTML);
    $('#info').html(productInfoHTML);
}
async function renderOrders(orderStatus) {
    orders = await getUserOrders(orderStatus);
    console.log('orders=', orders);
    const orderContainer = $("#orderContainer");
    orderContainer.empty();
    // Continue below..
    if(orders.length <= 0) {
        orderContainer.html(`
            <div class="text-center" style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">
                <img src="./images/empty-box.png" alt="empty-box" width="64" height="64" /><br>
                <span class="text-danger fwl">Không có đơn hàng nào!</span>
            </div>
        `);
    } else {
        orders.forEach((order, index) => {
            const latestOrderStatusMessage = order.orderStatuses[0].description;
            const orderItemBooks = order.orderItemBooks;
            orderItemBooks.forEach((orderItemBook, index2) => {
                const totalProductPrice = orderItemBook.book.price * orderItemBook.orderItem.quantity; // Số tiền đáng lẻ ra phải trả
                const totalProductPriceDiscount = totalProductPrice - (orderItemBook.book.discount * orderItemBook.orderItem.quantity);
                let buttonAction = "";
                if(order.order.orderStatus == 'AWAIT_ACCEPT' || order.order.orderStatus == 'PREPARING') {
                    buttonAction = `<button type="button" class="btn btn-danger p-2 px-4" onclick="cancelOrder(${order.order.id});">Hủy đơn</button>`;
                }
                if(order.order.orderStatus == 'CANCELED') {
                    buttonAction = `<button type="button" class="btn btn-danger p-2 px-4" onclick="reAddToCart(${orderItemBook.book.id}, ${orderItemBook.orderItem.quantity});">Mua lại</button>`;
                }
                orderContainer.append(`
                    <div class="p-3 border mb-3 bg-light" style="box-shadow: rgb(204, 219, 232) 3px 3px 6px 0px inset, rgba(255, 255, 255, 0.5) -3px -3px 6px 1px inset;">
                        <div class="d-flex">
                            <div class="flex-fill">
                                <span class="text-bg-danger p-1"><i class="fas fa-bullhorn">:</i> ${latestOrderStatusMessage}</span>
                            </div>
                            <div class="text-end text-danger border-start">&emsp;${orderStatusTranslations[order.order.orderStatus]}</div>
                        </div>
                        <hr>
                        <div class="d-flex">
                            <div class="flex-fill d-flex">
                                <img src="${orderItemBook.book.coverImageUrl}" alt="book-img" width="85" height="80" class="rounded">
                                <div class="ps-2">
                                    <span class="d-block">${orderItemBook.book.title}</span>
                                    <span>x${orderItemBook.orderItem.quantity}</span>
                                </div>
                            </div>
                            <div class="text-end">
                                <span class="text-decoration-line-through">${formatVND(totalProductPrice)} đ</span>
                                <span>${formatVND(totalProductPriceDiscount)} đ</span><br><br>
                                <div>
                                    <button type="button" class="btn btn-outline-danger p-2 px-4" onclick="orderDetail(${index}, ${index2});">Xem chi tiết</button>
                                    ${buttonAction}
                                </div>
                            </div>
                        </div>
                    </div>
                `); 
            });
        });
    }
    renderNavTab();
}
async function renderNavTab() {
    orders = await getUserOrders();
    const orderStatuses = {
        'AWAIT_PAYMENT': 0,
        'AWAIT_ACCEPT': 0,
        'PREPARING': 0,
        'TRANSPORTING': 0,
        'AWAIT_RATING': 0,
        'COMPLETE': 0,
        'CANCELED': 0,
    };
    orders.forEach(order => {
        orderStatuses[order.order.orderStatus] += 1;
    });
    for (const orderStatus in orderStatuses) {
        if(orderStatuses[orderStatus] > 0) {
            $(`#${orderStatus}`).text(`(${orderStatuses[orderStatus]})`);
        }
    }
    const total = Object.values(orderStatuses).reduce((sum, value) => sum + value, 0);
    $("#ALL").text(`(${total})`);
}