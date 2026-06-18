import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import "./Page.css";

const statusLabel = {
    N: "미처리",
    Y: "처리완료",
    C: "확인완료",
    R: "반려"
};

const statusBadgeClass = {
    N: "bg-gray-100 text-gray-600",
    Y: "bg-blue-100 text-blue-700",
    C: "bg-green-100 text-green-700",
    R: "bg-red-100 text-red-700"
};

const statusMap = {
    전체: "",
    미처리: "N",
    처리완료: "Y",
    확인완료: "C",
    반려: "R"
};

const Report = () => {

    const [reportType, setReportType] = useState("post");
    const [status, setStatus] = useState("전체");
    const [keyword, setKeyword] = useState("");
    const [reports, setReports] = useState([]);

    const [searchParams, setSearchParams] = useSearchParams();
    const currentPage = parseInt(searchParams.get('page') || '1');
    const [pageInfo, setPageInfo] = useState(null);

    useEffect(() => {
        fetchReports(currentPage);
    }, [currentPage, reportType]);

    const fetchReports = page => {
        fetch(`/react/admin/reports?type=${reportType}&status=${statusMap[status]}&page=${page}`)
            .then(res => res.json())
            .then(data => {
                setReports(data.list || []);
                setPageInfo(data.pi || null);
            })
            .catch(err => console.log(err));
    };

    const handleSearch = () => {
        if (currentPage === 1) {
            fetchReports(1);
        } else {
            setSearchParams({ page: "1" });
        }
    };

    const changePage = page => {
        setSearchParams({ page: page.toString() });
    };

    const changeReportStatus = (report, newStatus) => {

        fetch("/react/admin/reports/status", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            body: JSON.stringify({
                reportNo: report.reportNo,
                reportType: reportType,
                status: newStatus
            })
        })
            .then(res => res.json())
            .then(data => {

                if (data === 1) {
                    setReports(prev => prev.map(r =>
                        r.reportNo === report.reportNo ? { ...r, status: newStatus } : r
                    ));
                } else {
                    alert("처리에 실패하여 페이지를 새로고침합니다.");
                    window.location.reload();
                }
            })
            .catch(err => console.log(err));
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
                    onClick={() => {
                        setReportType("post");
                        setSearchParams({ page: "1" });
                    }}
                    className={`rounded-lg px-6 py-2 font-semibold shadow
                        ${
                            reportType === "post"
                                ? "bg-red-600 text-white hover:bg-red-700"
                                : "cursor-pointer border border-gray-300 bg-white hover:bg-gray-100"
                        }`}
                >
                    게시글 신고
                </button>

                <button
                    onClick={() => {
                        setReportType("reply");
                        setSearchParams({ page: "1" });
                    }}
                    className={`rounded-lg px-6 py-2 font-semibold shadow
                        ${
                            reportType === "reply"
                                ? "bg-red-600 text-white hover:bg-red-700"
                                : "cursor-pointer border border-gray-300 bg-white hover:bg-gray-100"
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
                        className="cursor-pointer rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-red-500"
                    >
                        <option>전체</option>
                        <option>미처리</option>
                        <option>처리완료</option>
                        <option>확인완료</option>
                        <option>반려</option>
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
                        className="cursor-pointer rounded-lg bg-red-600 px-6 py-2 text-white hover:bg-red-700"
                    >
                        검색
                    </button>

                </div>

            </div>

            {/* 신고 목록 */}
            <div className="overflow-hidden rounded-2xl border bg-white shadow-md">

                {/* 목록 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">
                        신고 목록
                    </h2>
                </div>

                <table className="w-full table-auto">

                    <thead>
                        <tr className="border-b bg-gray-100 text-gray-700">
                            <th className="p-4 text-center font-semibold">번호</th>
                            <th className="p-4 text-center font-semibold">신고대상</th>
                            <th className="p-4 text-center font-semibold">신고자</th>
                            <th className="p-4 text-center font-semibold">신고일</th>
                            <th className="p-4 text-center font-semibold">상태</th>
                            <th className="p-4 text-center font-semibold">관리</th>
                        </tr>
                    </thead>

                    <tbody>
                        {reports.length > 0 ? (
                            reports.map((report) => (
                                <tr key={report.reportNo} className="border-b hover:bg-gray-50">

                                    <td className="p-4 text-center">{report.reportNo}</td>
                                    <td className="p-4 text-center">{report.targetTitle}</td>
                                    <td className="p-4 text-center">{report.reporterName}</td>
                                    <td className="p-4 text-center">{report.reportDate?.split("T")[0]}</td>
                                    <td className="p-4 text-center">
                                        <span className={`rounded-full px-3 py-1 text-sm font-semibold ${statusBadgeClass[report.status]}`}>
                                            {statusLabel[report.status]}
                                        </span>
                                    </td>
                                    <td className="p-4">
                                        {report.status === "N" ? (
                                            <div className="flex justify-center gap-2">
                                                <button
                                                    onClick={() => changeReportStatus(report, "Y")}
                                                    className="cursor-pointer rounded-lg border border-green-500 bg-green-500 px-3 py-1 text-sm font-semibold text-white transition hover:bg-green-600"
                                                >
                                                    처리
                                                </button>
                                                <button
                                                    onClick={() => changeReportStatus(report, "R")}
                                                    className="cursor-pointer rounded-lg border border-gray-300 bg-white px-3 py-1 text-sm font-semibold text-gray-600 transition hover:bg-gray-100"
                                                >
                                                    반려
                                                </button>
                                            </div>
                                        ) : (
                                            <div className="text-center text-gray-400">-</div>
                                        )}
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="6" className="p-16 text-center text-gray-400">
                                    신고 데이터가 없습니다.
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
                            className={`pagination-page ${currentPage === pageNum ? "active" : ""}`}
                        >
                            {pageNum}
                        </button>
                    ))}

                    <button
                        className="pagination-btn"
                        onClick={() => currentPage < pageInfo.maxPage && changePage(currentPage + 1)}
                        disabled={currentPage >= pageInfo.maxPage}
                    >
                        ›
                    </button>
                </div>
            )}

        </section>
    );
};

export default Report;