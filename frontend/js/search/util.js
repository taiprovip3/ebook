async function searchBook(keysearch) {
    if (keysearch) {
        fetch(`http://localhost:8080/api/v1/book/search?key=${encodeURIComponent(keysearch)}`)
            .then(response => response.json())
            .then(searchedBooks => {
                console.log('searchedBooks=', searchedBooks);
                const bookContainer = $('#bookContainer');
                bookContainer.empty();
                if(searchedBooks.length <= 0) {
                    bookContainer.html(`
                        <div class="text-center" style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">
                            <img src="./images/empty-box.png" alt="empty-box" width="64" height="64" /><br>
                            <span class="text-danger fwl">Không có sách nào với keysearch!</span>
                        </div>
                    `);
                }

                searchedBooks.forEach(book => {
                    const bookHtml = `
                        <div class="col-2">
                            <div class="card book-card position-relative">
                                <div class="position-absolute end-0 vertical-text ${compareStrings(book.genre, keysearch) ? 'text-bg-danger' : ''}">${book.genre}</div>
                                <img src="${book.coverImageUrl}" class="card-img-top book-cover" alt="${book.title}" width="100%" style="max-height: 250px">
                                <div class="card-body">
                                    <h5 class="card-title text-truncate ${compareStrings(book.title, keysearch) ? 'text-bg-danger' : ''}">${book.title}</h5>
                                    <span class="card-text"><strong>Author:</strong> <span class="${compareStrings(book.author, keysearch) ? 'text-bg-danger' : ''}">${book.author}</span></span><br>
                                    <span class="card-text"><strong>Price:</strong> <span class="text-danger fw-bold">${formatVND(book.price)}</span></span><br>
                                    <span class="card-text text-truncate">${book.description}</span><br>
                                    <span class="card-text"><strong>Rating:</strong> ${book.rating}⭐ (${formatNumber(book.reviewCount)} reviews)</span><br>
                                    <div class="text-end my-1">
                                        <a href="javascript:void(0);" class="btn btn-primary">Add to cart</a>
                                        <a href="javascript:void(0);" class="btn btn-outline-primary">Buy now</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;
                    bookContainer.append(bookHtml);
                });
            })
            .catch(error => {
                console.error('Error fetching books:', error);
            });
    }
}
function compareStrings(str1, str2) {
    return str1.toLowerCase() === str2.toLowerCase();
}