import { useState } from "react";

const Posts = () => {

    const [searchType, setSearchType] = useState("전체");
    const [keyword, setKeyword] = useState("");

    const handleSearch = () => {
        console.log(searchType, keyword);
    };

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

            {/* 검색 영역 */}
            <div className="mb-6 rounded-xl border bg-white p-6 shadow-sm">

                <h2 className="mb-4 text-lg font-semibold">
                    게시글 검색
                </h2>

                <div className="flex gap-3">

                    <select
                        value={searchType}
                        onChange={(e) => setSearchType(e.target.value)}
                        className="cursor-pointer rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option >전체</option>
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
            <div className="rounded-xl border bg-white shadow-sm">

                {/* 목록 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">
                        게시글 목록
                    </h2>
                </div>

                {/* 테이블 헤더 */}
                <div className="grid grid-cols-12 border-b bg-gray-50 p-4 font-semibold">

                    <div className="col-span-1 text-center">
                        번호
                    </div>

                    <div className="col-span-5 text-center">
                        제목
                    </div>

                    <div className="col-span-2 text-center">
                        작성자
                    </div>

                    <div className="col-span-2 text-center">
                        작성일
                    </div>

                    <div className="col-span-2 text-center">
                        상태
                    </div>

                </div>

                {/* 데이터 영역 */}
                <div className="p-16 text-center text-gray-400">
                    게시글 데이터가 표시되는 영역
                </div>

            </div>

        </section>
    );
};

export default Posts;