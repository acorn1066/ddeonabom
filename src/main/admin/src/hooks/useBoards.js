import { useState } from "react";
import { useSearchParams } from "react-router-dom";

export const useBoards = (fetchUrl, idField = "boardId") => {
    const [boards, setBoards] = useState([]);
    const [pageInfo, setPageInfo] = useState(null);

    const [searchParams, setSearchParams] = useSearchParams();
    const currentPage = parseInt(searchParams.get("page") || "1");

    const changePage = page => {
        setSearchParams({ page: page.toString() });
    };

    const resetToFirstPage = () => {
        setSearchParams({ page: "1" });
    };

    const [selectBoard, setSelectBoard] = useState(null);
    const [showModal, setShowModal] = useState(false);

    const handleBoardClick = board => {
        setSelectBoard(board);
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setSelectBoard(null);
    };

    const handleStatusToggle = (id, newStatus, extraBody = {}) => {
        const statusField = extraBody.reportStatus !== undefined ? "reportStatus" : "status";

        fetch(`/react/admin/${fetchUrl}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json;charset=UTF-8" },
            body: JSON.stringify({
                [idField]: id,
                [statusField]: newStatus,
                ...extraBody
            })
        })
            .then(res => res.json())
            .then(data => {
                if (data === 1) {
                    setBoards(prev => prev.map(b =>
                        b[idField] === id ? { ...b, [statusField]: newStatus } : b
                    ));
                    if (selectBoard && selectBoard[idField] === id) {
                        setSelectBoard(prev => ({ ...prev, [statusField]: newStatus }));
                    }
                } else {
                    alert("상태 변경에 실패하여 페이지를 새로고침합니다.");
                    window.location.reload();
                }
            })
            .catch(err => console.log(err));
    };

    const [keyword, setKeyword] = useState("");

    const handleSearch = (fetchFn) => {
        if (currentPage === 1) {
            fetchFn(1);
        } else {
            resetToFirstPage();
        }
    };

    return {
        boards, setBoards,
        pageInfo, setPageInfo,
        currentPage, changePage, resetToFirstPage,
        handleStatusToggle,
        selectBoard, showModal, handleBoardClick, closeModal, keyword, setKeyword, handleSearch
    };
};