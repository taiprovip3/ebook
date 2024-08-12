/**
 * 
 * Chứa các hàm xử lý của index.html
 */
/* FlashSale Countdown */
const interval = setInterval(updateCountdown, 1000);
updateCountdown(); // Cập nhật ngay lập tức khi trang được load

function updateCountdown() {
    const now = new Date();
    const saleEndTime = new Date('2024-07-28T23:59:59'); // Thời gian kết thúc flashsale
    const timeDifference = saleEndTime - now;

    if (timeDifference > 0) {
        const hours = Math.floor(timeDifference / (1000 * 60 * 60));
        const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);

        // document.getElementById('flashSaleExpired').textContent = `${hours} giờ ${minutes} phút ${seconds} giây`;
        document.getElementById('flashSaleExpired').innerHTML = `
            <span class="badge bg-dark p-2 mx-1">${hours} Giờ</span>:<span class="badge bg-dark p-2 mx-1">${minutes} Phút</span>:<span class="badge bg-dark p-2 mx-1">${seconds} Giây</span>
        `;

    } else {
        document.getElementById('flashSaleExpired').textContent = 'Flash sale đã kết thúc';
        clearInterval(interval);
    }
}

async function loadBooks(page, size) {
    const data = await getBooks(page, size);
    console.log('data=', data);
    $('#pagination').empty();

    const bookContainer = $("#bookContainer");
    bookContainer.empty();
    
    const books = data.content

    books.forEach(book => {
        const bookHtml = `
            <div class="col-1 text-center border me-2 p-1 small align-self-baseline crs book-card" data-id="${book.id}" data-bs-toggle="tooltip" title="${book.title}">
                <img src="${book.coverImageUrl}" alt="" width="110" height="130">
                <p class="d-block text-truncate">${book.title}</p>
                <span class="flex-fill text-danger fw-bold">${formatVND(book.price)}</span>
            </div>
        `;
        bookContainer.append(bookHtml);
    });

    // Add pagination links
    for (let i = 0; i < data.totalPages; i++) {
        $('#pagination').append(`
            <li class="page-item ${i === page ? 'active' : ''}">
                <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
            </li>
        `);
    }
}