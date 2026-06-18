// components/Pagination.jsx
const Pagination = ({ pageInfo, currentPage, onChange }) => {
    if (!pageInfo) return null;

    return (
        <div className="pagination-container">

            <button
                className="pagination-btn"
                onClick={() => currentPage > 1 && onChange(currentPage - 1)}
                disabled={currentPage <= 1}
            >
                ‹
            </button>

            {Array.from(
                { length: pageInfo.endPage - pageInfo.startPage + 1 },
                (_, i) => pageInfo.startPage + i
            ).map(pageNum => (
                <button
                    key={pageNum}
                    onClick={() => onChange(pageNum)}
                    className={`pagination-page ${currentPage === pageNum ? "active" : ""}`}
                >
                    {pageNum}
                </button>
            ))}

            <button
                className="pagination-btn"
                onClick={() => currentPage < pageInfo.maxPage && onChange(currentPage + 1)}
                disabled={currentPage >= pageInfo.maxPage}
            >
                ›
            </button>
        </div>
    );
};

export default Pagination;