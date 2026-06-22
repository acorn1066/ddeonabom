import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom"
import "./Page.css";
import Pagination from "../components/Pagination";
import { useBoards } from "../hooks/useBoards";

const Posts = () => {

    const { boards: posts, setBoards: setPosts, setPageInfo,
        pageInfo, currentPage, changePage, resetToFirstPage, handleStatusToggle: changeStatus,
        selectBoard: selectPost, showModal, handleBoardClick: handlePostClick, closeModal } = useBoards("posts", "postNo");

    const [keyword, setKeyword] = useState("");
    const [boardType, setBoardType] = useState("공유");
    const [searchType, setSearchType] = useState("전체");

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
                            resetToFirstPage();
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

                                            <button onClick={() => post.status === "N" ? changeStatus(post.postNo, "Y", { boardType: post.boardType }) : null}
                                                className={`rounded-lg px-4 py-1 text-sm font-semibold border transition cursor-pointer ${post.status === "Y" ? "border-green-500 bg-green-500 text-white" : "border-gray-300 bg-white text-gray-500"}`}>게시
                                            </button>

                                            <button onClick={() => post.status === "Y" ? changeStatus(post.postNo, "N", { boardType: post.boardType }) : null}
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

            <Pagination pageInfo={pageInfo} currentPage={currentPage} onChange={changePage} />

            {showModal && selectPost && (
                <div
                    className="fixed inset-0 z-50 flex items-center justify-center bg-black/50"
                    onClick={closeModal}
                >
                    <div
                        className="w-full max-w-lg rounded-2xl bg-white shadow-xl"
                        onClick={(e) => e.stopPropagation()}
                    >
                        {/* 공통 헤더 */}
                        <div className="flex items-center justify-between border-b p-4">
                            <h1 className="text-lg font-bold">{selectPost.title}</h1>
                            <button onClick={closeModal} className="text-gray-400 hover:text-gray-600">✕</button>
                        </div>

                        {/* 게시판별 본문 */}
                        <div className="max-h-96 overflow-y-auto p-4">

                            {boardType === "질문" && (
                                <>
                                    <p className="mb-4 text-gray-800">{selectPost.content}</p>
                                    <p className="text-sm text-gray-500">작성자: {selectPost.nickname}</p>
                                </>
                            )}

                            {boardType === "후기" && (
                                <>
                                    <h2 className="mb-2 text-base font-semibold text-gray-700">{selectPost.contentTitle}</h2>
                                    <p className="mb-4 text-gray-800">{selectPost.content}</p>
                                    <p className="text-sm text-gray-500">작성자: {selectPost.nickname}</p>
                                </>
                            )}

                            {boardType === "공유" && (
                                <p className="text-sm text-gray-500">작성자: {selectPost.nickname}</p>
                            )}

                        </div>

                        {/* 공통 버튼 + 공유 전용 버튼 */}
                        <div className="flex justify-end gap-2 border-t p-4">

                            {boardType === "공유" && (
                                <button
                                    onClick={() => navigate(`/board/share/${selectPost.postNo}`)}
                                    className="rounded-lg bg-indigo-500 px-4 py-2 text-white hover:bg-indigo-600 cursor-pointer"
                                >
                                    게시글 보기
                                </button>
                            )}

                            <button
                                onClick={() => selectPost.status === 'Y'
                                    ? changeStatus(selectPost.postNo, 'N', { boardType: selectPost.boardType })
                                    : changeStatus(selectPost.postNo, 'Y', { boardType: selectPost.boardType })
                                }
                                className={selectPost.status === 'N'
                                    ? "rounded-lg bg-blue-600 px-4 py-2 text-white cursor-pointer"
                                    : "rounded-lg bg-gray-800 px-4 py-2 text-white cursor-pointer"
                                }
                            >
                                {selectPost.status === 'N' ? '게시글 올리기' : '게시글 내리기'}
                            </button>

                            <button onClick={closeModal} className="rounded-lg bg-gray-200 px-4 py-2 cursor-pointer">닫기</button>

                        </div>
                    </div>
                </div>
            )}
            {showModal && <div className="modal-backdrop fade show"></div>}

        </section>
    );
};

export default Posts;