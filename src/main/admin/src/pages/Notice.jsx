import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Notice = () => {

    const navigate = useNavigate();
    const [keyword, setKeyword] = useState("");

    const handleSearch = () => {
        console.log("검색:", keyword);
    };

    return (
        <section className="flex-1 p-8">

            {/* 페이지 제목 */}
            <div className="mb-8 flex items-center justify-between">

                <div>
                    <h1 className="text-3xl font-bold">
                        공지사항 관리
                    </h1>

                    <p className="mt-2 text-gray-500">
                        공지사항 조회 및 관리
                    </p>
                </div>

                <button
                    onClick={() => navigate("/admin/nwrite")}
                    className="rounded-lg bg-blue-600 px-6 py-2 text-white hover:bg-blue-700"
                >
                    공지 등록
                </button>

            </div>

            {/* 검색 영역 */}
            <div className="mb-6 rounded-xl border bg-white p-6 shadow-sm">

                <h2 className="mb-4 text-lg font-semibold">
                    공지사항 검색
                </h2>

                <div className="flex gap-3">

                    <input
                        type="text"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        placeholder="공지사항 제목 검색"
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

            {/* 공지사항 목록 */}
            <div className="rounded-xl border bg-white shadow-sm">

                {/* 목록 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">
                        공지사항 목록
                    </h2>
                </div>

                {/* 헤더 */}
                <div className="grid grid-cols-12 border-b bg-gray-50 p-4 font-semibold">

                    <div className="col-span-1 text-center">
                        번호
                    </div>

                    <div className="col-span-5 text-center">
                        제목
                    </div>

                    <div className="col-span-2 text-center">
                        작성일
                    </div>

                    <div className="col-span-2 text-center">
                        상태
                    </div>

                    <div className="col-span-2 text-center">
                        관리
                    </div>

                </div>

                {/* 데이터 영역 */}
                <div className="p-16 text-center text-gray-400">
                    공지사항 데이터가 표시되는 영역
                </div>

            </div>

        </section>
    );
};

export default Notice;