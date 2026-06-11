import { useEffect, useState } from "react";

const Posts = () => {
    const [posts, setPosts] = useState([])
    const [searchType, setSearchType] = useState("전체");
    const [keyword, setKeyword] = useState("");
    const [boardType, setBoardType] = useState("공유");

    const handleSearch = () => {
        console.log(searchType, keyword);
    };

    useEffect(() =>{
        fetch('/react/admin/posts')
        .then(res => res.json())
        .then(data => setPosts(data))
        .catch(err => console.log(err))
    },[])

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
                        ${
                            boardType === board
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

                        <tr>
                            <td
                                colSpan="4"
                                className="p-16 text-center text-gray-400"
                            >
                                {boardType}게시판 데이터가 표시되는 영역
                            </td>
                        </tr>

                    </tbody>

                </table>

            </div>

        </section>
    );
};

export default Posts;