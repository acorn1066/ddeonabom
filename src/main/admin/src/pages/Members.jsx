import { useState } from "react";

const Members = () => {

    const [keyword, setKeyword] = useState("");

    const handleSearch = () => {
        console.log("검색:", keyword);
    };

    return (
        <section className="flex-1 p-8">

            {/* 페이지 제목 */}
            <div className="mb-8">
                <h1 className="text-3xl font-bold">
                    회원 관리
                </h1>

                <p className="mt-2 text-gray-500">
                    회원 조회 및 관리
                </p>
            </div>

            {/* 검색 영역 */}
            <div className="mb-6 rounded-xl border bg-white p-6 shadow-sm">

                <h2 className="mb-4 text-lg font-semibold">
                    회원 검색
                </h2>

                <div className="flex gap-3">

                    <input
                        type="text"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        placeholder="아이디 검색"
                        className="flex-1 rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />

                    <button
                        onClick={handleSearch}
                        className="rounded-lg bg-blue-600 px-6 py-2 text-white hover:bg-blue-700"
                    >
                        검색
                    </button>

                </div>

            </div>

            {/* 회원 목록 */}
            <div className="rounded-xl border bg-white shadow-sm">

                {/* 목록 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">
                        회원 목록
                    </h2>
                </div>

                {/* 헤더 */}
                <div className="grid grid-cols-12 border-b bg-gray-50 p-4 font-semibold">
                    <div className="col-span-1 text-center">번호</div>
                    <div className="col-span-5 text-center">아이디</div>
                    <div className="col-span-3 text-center">가입일</div>
                    <div className="col-span-3 text-center">관리</div>
                </div>

                {/* 데이터 영역 */}
                <div className="p-16 text-center text-gray-400">
                    회원 데이터가 표시되는 영역
                </div>

            </div>

        </section>
    );
};

export default Members;