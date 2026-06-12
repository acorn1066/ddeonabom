import { useEffect, useState } from "react";

const Posts = () => {
    const [posts, setPosts] = useState([])
    const [searchType, setSearchType] = useState("전체");
    const [keyword, setKeyword] = useState("");
    const [boardType, setBoardType] = useState("공유");

    const handleSearch = () => {
        console.log(searchType, keyword);
    };

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
            .then(() => {
                setPosts(prev =>
                    prev.map(p =>
                        p.postNo === post.postNo
                            ? { ...p, status: newStatus }
                            : p
                    )
                );
            })
            .catch(console.error);
    };

    useEffect(() => {

        const urlMap = {
            공유: "/react/admin/posts/schedule",
            후기: "/react/admin/posts/review",
            질문: "/react/admin/posts/question"
        };

        fetch(urlMap[boardType])
            .then(res => res.json())
            .then(data => setPosts(data))
            .catch(console.error);

    }, [boardType]);

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
                        onClick={() => setBoardType(board)}
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
                                    <td className="p-4 text-center">{post.title}</td>
                                    <td className="p-4 text-center">{post.nickname}</td>
                                    <td className="p-4 text-center">{post.createDate.split('T')[0]}</td>
                                    <td className="p-4">
                                        <div className="flex justify-center gap-2">

                                            <button onClick={() =>
                                                    post.status === "N"
                                                        ? changeStatus(post, "Y")
                                                        : null
                                                }
                                                className={`rounded-lg px-4 py-1 text-sm font-semibold border transition cursor-pointer ${post.status === "Y" ? "border-green-500 bg-green-500 text-white" : "border-gray-300 bg-white text-gray-500"}`}>게시
                                            </button>

                                            <button onClick={() =>
                                                    post.status === "Y"
                                                        ? changeStatus(post, "N")
                                                        : null
                                                }
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

        </section>
    );
};

export default Posts;