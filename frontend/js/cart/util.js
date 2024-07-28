function renderCartItems() {
    $('#cartItems').empty();
    cartItems.forEach(item => {
        const itemTotalPrice = formatVND(item.book.price * item.cartItem.quantity);
        $('#cartItems').append(`
            <tr class="crs">
                <td><input type="checkbox" class="item-check" data-id="${item.cartItem.id}" data-book-id="${item.book.id}" data-price="${item.book.price}" data-quantity="${item.cartItem.quantity}"></td>
                <td><img src="${item.book.coverImageUrl}" alt="${item.book.title}"></td>
                <td>${item.book.title}</td>
                <td>${formatVND(item.book.price)}</td>
                <td>
                    <input type="number" class="quantity-input" value="${item.cartItem.quantity}" data-id="${item.cartItem.id}">
                </td>
                <td>${itemTotalPrice}</td>
                <td>
                    <button class="btn btn-danger remove-item" data-id="${item.cartItem.id}"><i class="fas fa-trash-alt"></i></button>
                </td>
            </tr>
        `);
    });
    $("#selectAll"). prop('checked', false);
}
function calculateTotalAmount() {
    let totalAmount = 0;
    $('.item-check:checked').each(function () {
        const price = parseFloat($(this).data('price'));
        const quantity = parseInt($(this).data('quantity'));
        totalAmount += price * quantity;
    });
    $('#totalAmount').text(formatVND(totalAmount));
}