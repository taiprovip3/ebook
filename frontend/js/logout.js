function logout(role) {// role expected string: user, admin
    const accessToken = window.sessionStorage.getItem(`${role}_access_token`);
    fetch('http://localhost:8080/api/v1/auth/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${accessToken}`
        },
    })
    .then((response) => {
        window.sessionStorage.removeItem(`${role}_access_token`);
        window.document.cookie = 'COOKIE_NAME=; Max-Age=0; path=/; domain=' + location.host;
        switch (role) {
            case 'user':
                toastr.info('Thành công!', 'Đăng xuất')
                setTimeout(() => {
                    window.location.reload();
                }, 500);
                break;
            default:
                window.location.href = './login.html'
                break;
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function formatDate(dateString) {
    const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', timeZoneName: 'short' };
    return new Date(dateString).toLocaleDateString('vi-VN', options);
}