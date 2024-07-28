function minusQuantity() {
    const quantityInput = $('#quantity');
    const currentQuantity = parseInt(quantityInput.val());
    if (currentQuantity > 1) {
        quantityInput.val(currentQuantity - 1);
    }
}
function plusQuantity() {
    const quantityInput = $('#quantity');
    const currentQuantity = parseInt(quantityInput.val());
    quantityInput.val(currentQuantity + 1);
}
async function addToCart(bookId) {
    const quantity = $('#quantity').val();
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
        }, error: function(error) {
            console.error(error);
            toastr.error(error.responseText, 'Error!')
        }
    });
}
function buyNow(bookId) {
    const quantity = Number($('#quantity').val());
    window.location.href = `./payment.html?bookIds=${bookId}&quantities=${quantity}`;
}
async function renderBookDetail(book) {
    const originalPrice = book.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    const finalPrice = (book.price - book.discount - book.flashSale).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    const discountPercent = Math.round(((book.discount + book.flashSale) / book.price) * 100);
    const isOutOfStock = book.stockQuantity <= 0;
    const totalSold = await getTotalSoldOfBook(book.id);
    const reviews = await getRatingsOfBook(book.id);

    $('#bookDetail').html(`
        <div class="row p-3">
            <div class="col-4">
                <img src="${book.coverImageUrl}" alt="${book.title}" class="book-cover" width="100%" height="100%" />
            </div>
            <div class="col-8 p-3 rounded-2">
                <div class="p-3 rounded-2 mb-2" style="background-color: #f7f7f7;">
                    <h1>${book.title}</h1>
                    <p><strong><i class="fas fa-at"></i> Author:</strong> ${book.author}</p>
                    <p><strong><i class="fab fa-fort-awesome-alt"></i> Publishers:</strong> ${book.publisher}</p>
                    <p><strong><i class="fas fa-clock"></i> Publication Date:</strong> ${formatDate(book.publicationDate)}</p>
                    <p><strong><i class="fas fa-cookie"></i> Genre:</strong> ${book.genre}</p>
                    <p><strong><i class="fas fa-globe-asia"></i> Book Type:</strong> ${book.bookType}</p>
                    <p><strong><i class="fas fa-boxes"></i> Stocks:</strong> ${book.stockQuantity}</p>
                    <p><strong><i class="fas fa-dolly"></i> Sold:</strong> ${totalSold}</p>
                </div>
                <div class="p-3 rounded-2" style="background-color: #f7f7f7;">
                    <div class="mb-3 text-danger">${book.rating}/5 ${renderStars(book.rating)} <span>(${formatNumber(book.reviewCount)} đánh giá)</span></div>
                    <div>
                        <span class="text-decoration-line-through">${formatVND(book.price)};</span>&emsp13;
                        <span class="badge bg-danger">-${discountPercent}%</span>&emsp14;
                        <br>
                        <span class="fwl text-danger fw-bold">${formatVND(finalPrice)}</span><br>
                    </div>
                    <div class="d-flex col-2 mb-2">
                        <button class="btn btn-outline-light" onclick="minusQuantity();"><i class="fas fa-minus text-dark"></i></button>&emsp13;
                        <input class="form-control text-danger" type="number" id="quantity" value="1" min="1">&emsp13;
                        <button class="btn btn-outline-light" onclick="plusQuantity();"><i class="fas fa-plus text-dark"></i></button>
                    </div>
                    <div>
                        <button class="btn btn-outline-danger p-3" onclick="addToCart(${book.id});">Thêm vào giỏ hàng</button>
                        <button class="btn btn-danger p-3 px-5" onclick="buyNow(${book.id});">Mua ngay</button>
                    </div>    
                </div>
            </div>
        </div>
        <div class="p-3 mb-2">
            <div class="p-3 mb-2" style="background-color: #f7f7f7;">
                <h4 class="text-danger fw-bold"><i class="fas fa-pen-square"></i> Mô Tả:</h4>
                <p>${book.description}</p>
            </div>
            <div class="p-3 mb-3" style="background-color: #f7f7f7;">
                <h4 class="text-danger fw-bold"><i class="fas fa-pencil-ruler"></i> Kích Thước và Trọng Lượng:</h4>
                <ul>
                    <li>Chiều dài: ${book.length} cm</li>
                    <li>Chiều rộng: ${book.width} cm</li>
                    <li>Chiều cao: ${book.height} cm</li>
                    <li>Cân nặng: ${book.weight} gram</li>
                </ul>
            </div>
            <div class="p-3" style="background-color: #f7f7f7;">
                <h4 class="text-danger fw-bold"><i class="fas fa-pen-square"></i> Đánh Giá:</h4>
                <div id="reviewsContainer">
                    ${renderReviews(reviews)}
                </div>
            </div>
        </div>
    `);
}
function renderStars(rating) {
    const stars = Math.round(rating);
    return '<span class="rating-stars">' +
        '⭐'.repeat(stars) +
        '✩'.repeat(5 - stars) +
        '</span>';
}
function renderReviews(reviews) {
    if(reviews.length <= 0) {
        return '<i><< Chưa có đánh giá nào >></i>';
    }
    let appendReviews = '';
    reviews.forEach((review, index) => {
        appendReviews += `
            <div class="review-container bg-white border ps-3 pt-3 rounded-2 mb-1">
                <div class="rating">${renderStars(review.ratingStars)} (${review.ratingStars}/5)</div>
                <p>${review.comment}</p>
                <p class="small text-secondary">${formatDate(review.createAt)} (${timeAgo(review.createAt)})</p>
            </div>
        `;
    });
    return appendReviews;
}