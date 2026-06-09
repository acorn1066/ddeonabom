import { useState } from "react";

const Report = () => {

    const [reportType, setReportType] = useState("post");
    const [status, setStatus] = useState("전체");
    const [keyword, setKeyword] = useState("");

    const handleSearch = () => {
        console.log(reportType, status, keyword);
    };

    return (
        <section className="flex-1 p-8">

            {/* 페이지 제목 */}
            <div className="mb-8">
                <h1 className="text-3xl font-bold">
                    신고 관리
                </h1>

                <p className="mt-2 text-gray-500">
                    신고 내역 조회 및 처리
                </p>
            </div>

            {/* 게시글 / 댓글 탭 */}
            <div className="mb-6 flex gap-3">

                <button
                    onClick={() => setReportType("post")}
                    className={`rounded-lg px-6 py-2 font-semibold shadow
                        ${
                            reportType === "post"
                                ? "bg-red-600 text-white hover:bg-red-700"
                                : "border border-gray-300 bg-white hover:bg-gray-100"
                        }`}
                >
                    게시글 신고
                </button>

                <button
                    onClick={() => setReportType("reply")}
                    className={`rounded-lg px-6 py-2 font-semibold shadow
                        ${
                            reportType === "reply"
                                ? "bg-red-600 text-white hover:bg-red-700"
                                : "border border-gray-300 bg-white hover:bg-gray-100"
                        }`}
                >
                    댓글 신고
                </button>

            </div>

            {/* 검색 영역 */}
            <div className="mb-6 rounded-xl border bg-white p-6 shadow-sm">

                <h2 className="mb-4 text-lg font-semibold">
                    신고 검색
                </h2>

                <div className="flex gap-3">

                    <select
                        value={status}
                        onChange={(e) => setStatus(e.target.value)}
                        className="rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-red-500"
                    >
                        <option>전체</option>
                        <option>미처리</option>
                        <option>처리완료</option>
                    </select>

                    <input
                        type="text"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        placeholder="신고자 또는 신고사유 검색"
                        className="flex-1 rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-red-500"
                    />

                    <button
                        onClick={handleSearch}
                        className="rounded-lg bg-red-600 px-6 py-2 text-white hover:bg-red-700"
                    >
                        검색
                    </button>

                </div>

            </div>

            {/* 신고 목록 */}
            <div className="rounded-xl border bg-white shadow-sm">

                {/* 목록 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">
                        신고 목록
                    </h2>
                </div>

                {/* 테이블 헤더 */}
                <div className="grid grid-cols-10 border-b bg-gray-50 p-4 font-semibold">

                    <div className="col-span-1 text-center">
                        번호
                    </div>

                    <div className="col-span-4 text-center">
                        신고대상
                    </div>

                    <div className="col-span-2 text-center">
                        신고자
                    </div>

                    <div className="col-span-2 text-center">
                        신고일
                    </div>

                    <div className="col-span-1 text-center">
                        상태
                    </div>

                </div>

                {/* 데이터 영역 */}
                <div className="p-16 text-center text-gray-400">
                    신고 데이터가 표시되는 영역
                </div>

            </div>

        </section>
    );
};

export default Report;