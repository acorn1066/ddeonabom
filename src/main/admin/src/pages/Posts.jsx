import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom"
import "./Page.css";

const Posts = () => {
    const [posts, setPosts] = useState([])
    const [searchType, setSearchType] = useState("전체");
    const [keyword, setKeyword] = useState("");
    const [boardType, setBoardType] = useState("공유");

    const [searchParams, setSearchParams] = useSearchParams()
    const currentPage = parseInt(searchParams.get('page') || '1')
    const [pageInfo, setPageInfo] = useState(null)

    const handleSearch = () => {
        console.log(searchType, keyword);
    };

    useEffect(() => {
        fetchPosts(currentPage)
    }, [currentPage, boardType])

    const fetchPosts = page => {

        const categoryMap = {
            공유: "schedule",
            후기: "review",
            질문: "question"
        };

        fetch(`/react/admin/posts?category=${categoryMap[boardType]}&page=${page}`)
            .then(res => res.json())
            .then(data => {
                // console.log(data);

                setPosts(data.list || []);
                setPageInfo(data.pi || null);
            })
            .catch(err => console.log(err))
    }

    const changePage = page => {
        setSearchParams({ page: page.toString() })
    }

    const changeStatus = (post, newStatus) => {

        fetch("/react/admin/posts/status", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            body: JSON.stringify({
                postNo: post.postNo,
                boardType: post.boardType,
                status: newStatus
            })
        })
            .then(res => res.json())
            .then(data => {

                if (data === 1) {
                    setPosts(prev => prev.map(p =>
                        p.postNo === post.postNo ? { ...p, status: newStatus } : p
                    ));
                    if (selectPost && selectPost.postNo === post.postNo) {
                        setSelectPost({ ...selectPost, status: newStatus });
                    }

                } else {
                    alert("상태 변경에 실패하여 페이지를 새로고침합니다.");
                    window.location.reload();
                }
            })
            .catch(err => console.log(err));
    };
    const [selectPost, setSelectPost] = useState(null)
    const [showModal, setShowModal] = useState(false)

    const handlePostClick = post => {
        setSelectPost(post)
        setShowModal(true)
    }

    const closeModal = () => {
        setShowModal(false)
        setSelectPost(null)
    }


    return (
        <section className="flex-1 p-8">

            {/* 페이지 제목 */}
            <div className="mb-8">
                <h1 className="text-3xl font-bold">
                    게시글 관리
                </h1>

                <p className="mt-2 text-gray-500">
                    게시글 조회 및 관리
                </p>
            </div>

            {/* 게시판 선택 */}
            <div className="mb-6 flex gap-3">

                {["공유", "후기", "질문"].map((board) => (
                    <button
                        key={board}
                        onClick={() => {
                            setBoardType(board)
                            setSearchParams({ page: "1" })
                        }}
                        className={`cursor-pointer rounded-lg border px-6 py-2 font-semibold shadow-sm transition-all
                        ${boardType === board
                                ? "border-blue-600 bg-blue-600 text-white"
                                : "border-gray-300 bg-white text-gray-700 hover:bg-gray-50"
                            }`}
                    >
                        {board}게시판
                    </button>
                ))}

            </div>

            {/* 검색 영역 */}
            <div className="mb-6 rounded-2xl border bg-white p-6 shadow-sm">

                <h2 className="mb-4 text-lg font-semibold">
                    게시글 검색
                </h2>

                <div className="flex gap-3">

                    <select
                        value={searchType}
                        onChange={(e) => setSearchType(e.target.value)}
                        className="cursor-pointer rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option>전체</option>
                        <option>제목</option>
                        <option>작성자</option>
                    </select>

                    <input
                        type="text"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        placeholder="검색어를 입력하세요"
                        className="flex-1 rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />

                    <button
                        onClick={handleSearch}
                        className="cursor-pointer rounded-lg bg-blue-600 px-6 py-2 text-white hover:bg-blue-700"
                    >
                        검색
                    </button>

                </div>

            </div>

            {/* 게시글 목록 */}
            <div className="overflow-hidden rounded-2xl border bg-white shadow-md">

                {/* 목록 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">
                        {boardType}게시판 목록
                    </h2>
                </div>

                <table className="w-full table-auto">

                    <thead>
                        <tr className="border-b bg-gray-100 text-gray-700">

                            <th className="p-4 text-center font-semibold">제목</th>
                            <th className="p-4 text-center font-semibold">작성자</th>
                            <th className="p-4 text-center font-semibold">작성일</th>
                            <th className="p-4 text-center font-semibold">상태</th>

                        </tr>
                    </thead>

                    <tbody>
                        {posts.length > 0 ? (
                            posts.map((post, index) => (

                                <tr key={index} className="border-b hover:bg-gray-50">
                                    <td className="p-4 text-center cursor-pointer" onClick={() => handlePostClick(post)}>{post.title}</td>
                                    <td className="p-4 text-center cursor-pointer" onClick={() => handlePostClick(post)}>{post.nickname}</td>
                                    <td className="p-4 text-center cursor-pointer" onClick={() => handlePostClick(post)}>{post.createDate.split('T')[0]}</td>
                                    <td className="p-4">
                                        <div className="flex justify-center gap-2">

                                            <button onClick={() => post.status === "N" ? changeStatus(post, "Y") : null}
                                                className={`rounded-lg px-4 py-1 text-sm font-semibold border transition cursor-pointer ${post.status === "Y" ? "border-green-500 bg-green-500 text-white" : "border-gray-300 bg-white text-gray-500"}`}>게시
                                            </button>

                                            <button onClick={() => post.status === "Y" ? changeStatus(post, "N") : null}
                                                className={`rounded-lg px-4 py-1 text-sm font-semibold border transition cursor-pointer ${post.status === "N" ? "border-red-500 bg-red-500 text-white" : "border-gray-300 bg-white text-gray-500"}`}>삭제
                                            </button>

                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="4" className="p-16 text-center text-gray-400">
                                    게시글이 없습니다.
                                </td>
                            </tr>
                        )}
                    </tbody>

                </table>

            </div>

            {pageInfo && (
                <div className="pagination-container">

                    <button
                        className="pagination-btn"
                        onClick={() => currentPage > 1 && changePage(currentPage - 1)}
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
                            onClick={() => changePage(pageNum)}
                            className={`pagination-page ${currentPage === pageNum ? "active" : ""
                                }`}
                        >
                            {pageNum}
                        </button>
                    ))}

                    <button
                        className="pagination-btn"
                        onClick={() =>
                            currentPage < pageInfo.maxPage &&
                            changePage(currentPage + 1)
                        }
                        disabled={currentPage >= pageInfo.maxPage}
                    >
                        ›
                    </button>
                </div>
            )}

            {showModal && selectPost && (
                <div
                    className="fixed inset-0 z-50 flex items-center justify-center bg-black/50"
                    onClick={closeModal}
                >
                    <div
                        className="w-full max-w-lg rounded-2xl bg-white shadow-xl"
                        onClick={(e) => e.stopPropagation()}
                    >
                        <div className="flex items-center justify-between border-b p-4">
                            <h1 className="text-lg font-bold">{selectPost.title}</h1>
                            <button onClick={closeModal} className="text-gray-400 hover:text-gray-600">✕</button>
                        </div>
                        <div className="max-h-96 overflow-y-auto p-4">{selectPost.content}</div>
                        <div className="p-4 text-right text-sm text-gray-500">작성자 : {selectPost.nickname}</div>
                        <div className="flex justify-end gap-2 border-t p-4">
                            <button
                                className={selectPost.status === 'N' ? "rounded-lg bg-blue-600 px-4 py-2 text-white" : "rounded-lg bg-gray-800 px-4 py-2 text-white"}
                                onClick={() => selectPost.status === 'Y' ? changeStatus(selectPost, 'N') : changeStatus(selectPost, 'Y')}
                            >
                                {selectPost.status === 'N' ? '게시글 올리기' : '게시글 내리기'}
                            </button>
                            <button onClick={closeModal} className="rounded-lg bg-gray-200 px-4 py-2">닫기</button>
                        </div>
                    </div>
                </div>
            )}
            {showModal && <div className="modal-backdrop fade show"></div>}

        </section>
    );
};

export default Posts;