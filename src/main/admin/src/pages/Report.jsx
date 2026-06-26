import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import "./Page.css";
import Pagination from "../components/Pagination";
import { useBoards } from "../hooks/useBoards";
import AdminModal from "../components/AdminModal";

const statusLabel = { N: "미처리", Y: "처리완료", R: "반려" };
const statusBadgeClass = {
    N: "bg-gray-100 text-gray-600",
    Y: "bg-blue-100 text-blue-700",
    R: "bg-red-100 text-red-700"
};
const statusMap = { 전체: "", 미처리: "N", 처리완료: "Y", 반려: "R" };
const boardTypeLabel = {
    review: "후기 게시판",
    question: "질문 게시판",
    reply: "댓글"
};

const reasonLabel = {
    A: "광고",
    B: "욕설",
    C: "음란",
    D: "도배"
};

const Report = () => {
    const {
        boards: reports, setBoards: setReports, setPageInfo,
        pageInfo, currentPage, changePage, resetToFirstPage,
        handleStatusToggle: changeReportStatus,
        selectBoard: selectReport, showModal,
        handleBoardClick: handleReportClick, closeModal
    } = useBoards("reports", "reportNo");

    const [reportType, setReportType] = useState("board");
    const [keyword, setKeyword] = useState("");
    const [status, setStatus] = useState("전체");

    useEffect(() => {
        fetchReports(currentPage);
    }, [currentPage, reportType]);

    const fetchReports = (page) => {
        fetch(`/react/admin/reports?targetType=${reportType}&status=${statusMap[status]}&page=${page}`)
            .then(res => res.json())
            .then(data => {
                setReports(data.list || []);
                setPageInfo(data.pi || null);
            })
            .catch(err => console.log(err));
    };

    const processReport = (reportNo, targetType, targetNo) => {
        fetch(`/react/admin/reports/process`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ reportNo, targetType, targetNo })
        })
            .then(res => res.json())
            .then(() => {
                setReports(prev =>
                    prev.map(r => r.targetType === targetType && r.targetNo === targetNo
                        ? { ...r, reportStatus: "Y" }
                        : r)
                );
            })
            .catch(err => console.log(err));
    };
    const rejectReport = (targetType, targetNo) => {
        fetch(`/react/admin/reports`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ targetType, targetNo, reportStatus: "R" })
        })
            .then(res => res.json())
            .then(() => {
                setReports(prev =>
                    prev.map(r =>
                        r.targetType === targetType && r.targetNo === targetNo
                            ? { ...r, reportStatus: "R" }
                            : r
                    )
                );
            })
            .catch(err => console.log(err));
    };


    const handleSearch = () => {
        if (currentPage === 1) {
            fetchReports(1);
        } else {
            resetToFirstPage();
        }
    };

    return (
        <section className="flex-1 p-8">

            <div className="mb-8">
                <h1 className="text-3xl font-bold">신고 관리</h1>
                <p className="mt-2 text-gray-500">신고 내역 조회 및 처리</p>
            </div>

            {/* 탭 */}
            <div className="mb-6 flex gap-3">
                {[["board", "게시글 신고"], ["reply", "댓글 신고"]].map(([type, label]) => (
                    <button
                        key={type}
                        onClick={() => {
                            setReportType(type);
                            resetToFirstPage();
                        }}
                        className={`rounded-lg px-6 py-2 font-semibold shadow
                            ${reportType === type
                                ? "bg-red-600 text-white hover:bg-red-700"
                                : "cursor-pointer border border-gray-300 bg-white hover:bg-gray-100"
                            }`}
                    >
                        {label}
                    </button>
                ))}
            </div>

            {/* 검색 */}
            <div className="mb-6 rounded-xl border bg-white p-6 shadow-sm">
                <h2 className="mb-4 text-lg font-semibold">신고 검색</h2>
                <div className="flex gap-3">
                    <select
                        value={status}
                        onChange={(e) => setStatus(e.target.value)}
                        className="cursor-pointer rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-red-500"
                    >
                        {Object.keys(statusMap).map(s => <option key={s}>{s}</option>)}
                    </select>
                    <input
                        type="text"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        placeholder="신고자 검색"
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

            {/* 목록 */}
            <div className="overflow-hidden rounded-2xl border bg-white shadow-md">
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">신고 목록</h2>
                </div>
                <table className="w-full table-auto">
                    <thead>
                        <tr className="border-b bg-gray-100 text-gray-700">
                            <th className="p-4 text-center font-semibold">번호</th>
                            <th className="p-4 text-center font-semibold">신고유형</th>
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
                                    <td className="p-4 text-center cursor-pointer" onClick={() => handleReportClick(report)}>{report.reportNo}</td>
                                    <td className="p-4 text-center cursor-pointer" onClick={() => handleReportClick(report)}>{boardTypeLabel[report.targetType]}</td>
                                    <td className="p-4 text-center cursor-pointer" onClick={() => handleReportClick(report)}>{report.targetTitle}</td>
                                    <td className="p-4 text-center cursor-pointer" onClick={() => handleReportClick(report)}>{report.reporterName}</td>
                                    <td className="p-4 text-center cursor-pointer" onClick={() => handleReportClick(report)}>{report.reportDate.split('T')[0]}</td>
                                    <td className="p-4 text-center">
                                        <span className={`rounded-full px-3 py-1 text-sm font-semibold ${statusBadgeClass[report.reportStatus]}`}>
                                            {statusLabel[report.reportStatus]}
                                        </span>
                                    </td>
                                    <td className="p-4">
                                        {report.reportStatus === "N" ? (
                                            <div className="flex justify-center gap-2">
                                                <button
                                                    onClick={() => processReport(report.reportNo, report.targetType, report.targetNo)}
                                                    className="cursor-pointer rounded-lg border border-green-500 bg-green-500 px-3 py-1 text-sm font-semibold text-white transition hover:bg-green-600 "
                                                >
                                                    처리
                                                </button>
                                                <button
                                                    onClick={() => rejectReport(report.targetType, report.targetNo)}
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
                                <td colSpan="7" className="p-16 text-center text-gray-400">신고 데이터가 없습니다.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            <Pagination pageInfo={pageInfo} currentPage={currentPage} onChange={changePage} />

            {/* 모달 */}
            {showModal && selectReport && (
                <AdminModal
                    title="신고 정보"
                    onClose={closeModal}
                    footer={
                        <>
                            {(selectReport.targetType === "review" || selectReport.targetType === "question") && (() => {
                                const urlMap = {
                                    review: `http://localhost:8080/reviews/detail?travelNo=${selectReport.targetNo}`,
                                    question: `http://localhost:8080/qList/detail?qNo=${selectReport.targetNo}`,
                                };
                                const isDisabled = selectReport.reportStatus === "Y";
                                return (
                                    <button
                                        onClick={() => !isDisabled && (window.location.href = urlMap[selectReport.targetType])}
                                        disabled={isDisabled}
                                        className={`rounded-lg px-4 py-2 text-white ${isDisabled
                                            ? "bg-indigo-300 cursor-not-allowed"
                                            : "bg-indigo-500 hover:bg-indigo-600 cursor-pointer"
                                            }`}
                                    >
                                        게시글 보기
                                    </button>
                                );
                            })()}
                            {selectReport.reportStatus === "N" && (
                                <>
                                    <button
                                        onClick={() => { processReport(selectReport.reportNo, selectReport.targetType, selectReport.targetNo); closeModal(); }}
                                        className="rounded-lg bg-green-600 px-4 py-2 text-white cursor-pointer"
                                    >
                                        처리
                                    </button>
                                    <button
                                        onClick={() => { rejectReport(selectReport.targetType, selectReport.targetNo); closeModal(); }}
                                        className="rounded-lg bg-red-600 px-4 py-2 text-white cursor-pointer"
                                    >
                                        반려
                                    </button>
                                </>
                            )}


                            <button onClick={closeModal} className="rounded-lg bg-gray-200 px-4 py-2 cursor-pointer">닫기</button>
                        </>
                    }
                >
                    <div className="space-y-3">
                        <p><strong>신고번호 :</strong> {selectReport.reportNo}</p>
                        <p><strong>신고유형 :</strong> {boardTypeLabel[selectReport.targetType]}</p>
                        <p><strong>신고대상 :</strong> {selectReport.targetTitle}</p>
                        <p><strong>신고자 :</strong> {selectReport.reporterName}</p>
                        <p><strong>신고일 :</strong> {selectReport.reportDate.split('T')[0]}</p>
                        <p><strong>상태 :</strong> {statusLabel[selectReport.reportStatus]}</p>
                        <p><strong>신고사유 :</strong> {selectReport.reason} - {reasonLabel[selectReport.reason]}</p>
                    </div>
                </AdminModal>
            )}

        </section >
    );
};

export default Report;