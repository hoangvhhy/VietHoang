/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function paginateItems(itemSelector, itemsPerPage, paginationContainerSelector) {
    const items = document.querySelectorAll(itemSelector);
    const paginationContainer = document.querySelector(paginationContainerSelector);

    if (!items.length || !paginationContainer) return;

    let currentPage = 1;
    const totalPages = Math.ceil(items.length / itemsPerPage);

    function showPage(page) {
        // Giữ trang trong giới hạn hợp lệ
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        items.forEach((item, index) => {
            item.style.display = (index >= (page - 1) * itemsPerPage && index < page * itemsPerPage) ? 'block' : 'none';
        });

        renderPagination(page);
    }

    function renderPagination(activePage) {
        paginationContainer.innerHTML = "";

        // Nút "Previous"
        if (activePage > 1) {
            const prevButton = document.createElement("button");
            prevButton.innerText = "« Prev";
            prevButton.classList.add("pagination-btn");
            prevButton.addEventListener("click", () => showPage(activePage - 1));
            paginationContainer.appendChild(prevButton);
        }

        // Hiển thị 5 số trang gần vị trí hiện tại
        let startPage = Math.max(1, activePage - 2);
        let endPage = Math.min(totalPages, startPage + 4);

        for (let i = startPage; i <= endPage; i++) {
            const pageButton = document.createElement("button");
            pageButton.innerText = i;
            pageButton.classList.add("pagination-btn");
            if (i === activePage) {
                pageButton.classList.add("active");
            }
            pageButton.addEventListener("click", () => showPage(i));
            paginationContainer.appendChild(pageButton);
        }

        // Nút "Next"
        if (activePage < totalPages) {
            const nextButton = document.createElement("button");
            nextButton.innerText = "Next »";
            nextButton.classList.add("pagination-btn");
            nextButton.addEventListener("click", () => showPage(activePage + 1));
            paginationContainer.appendChild(nextButton);
        }
    }

    showPage(currentPage);
}
