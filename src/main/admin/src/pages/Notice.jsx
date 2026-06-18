import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import "./Page.css";
import Pagination from "../components/Pagination";
import { useBoards } from "../hooks/useBoards";


const Notice = () => {
    const { boards: notice, setBoards: setNotice, setPageInfo,
        pageInfo, currentPage, changePage, handleStatusToggle: changeStatus } = useBoards("notice", "noticeNo");

    const [keyword, setKeyword] = useState("");

    const navigate = useNavigate(); 

    const handleSearch = () => {
        console.log("검색:", keyword);
    };

    useEffect(() => {
        fetchNotice(currentPage)
    }, [currentPage])

    const fetchNotice = page => {
        fetch(`/react/admin/notice?page=${page}`)
            .then(res => res.json())
            .then(data => {

                setNotice(data.list || []);
                setPageInfo(data.pi || null);
            })
            .catch(err => console.log(err))
    }

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
                    onClick={() => navigate("/notice/write")}
                    className="cursor-pointer rounded-lg bg-blue-600 px-6 py-2 text-white transition hover:bg-blue-700"
                >
                    공지 등록
                </button>

            </div>

            {/* 검색 영역 */}
            <div className="mb-6 rounded-2xl border bg-white p-6 shadow-sm">

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
                        className="cursor-pointer rounded-lg bg-blue-600 px-6 py-2 text-white transition hover:bg-blue-700"
                    >
                        검색
                    </button>

                </div>

            </div>

            {/* 공지사항 목록 */}
            <div className="overflow-hidden rounded-2xl border bg-white shadow-md">

                {/* 목록 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">
                        공지사항 목록
                    </h2>
                </div>

                <table className="w-full table-auto">

                    <thead>
                        <tr className="border-b bg-gray-100 text-gray-700">

                            <th className="p-4 text-center font-semibold">번호</th>
                            <th className="p-4 text-center font-semibold">제목</th>
                            <th className="p-4 text-center font-semibold">작성자</th>
                            <th className="p-4 text-center font-semibold">작성일</th>
                            <th className="p-4 text-center font-semibold">수정일</th>
                            <th className="p-4 text-center font-semibold">관리</th>

                        </tr>
                    </thead>

                    <tbody>
                        {notice.length > 0 ? (
                            notice.map((n) => (
                                <tr key={n.noticeNo} className="border-b hover:bg-gray-50">

                                    <td className="p-4 text-center">{n.noticeNo}</td>
                                    <td className="p-4" className="cursor-pointer hover:text-blue-600" onClick={() => navigate(`/notice/detail/${n.noticeNo}`)}>{n.title}</td>
                                    <td className="p-4 text-center">{n.memberName}</td>
                                    <td className="p-4 text-center">{n.createDate.split("T")[0]}</td>
                                    <td className="p-4 text-center">{n.modifyDate ? n.modifyDate.split("T")[0] : "-"}</td>
                                    <td className="p-4">
                                        <div className="flex justify-center gap-2">

                                            <button onClick={() => n.status === "N" ? changeStatus(n.noticeNo, "Y") : null}
                                                className={`rounded-lg px-4 py-1 text-sm font-semibold border transition cursor-pointer ${n.status === "Y" ? "border-green-500 bg-green-500 text-white" : "border-gray-300 bg-white text-gray-500"}`}>게시
                                            </button>

                                            <button onClick={() => n.status === "Y" ? changeStatus(n.noticeNo, "N") : null}
                                                className={`rounded-lg px-4 py-1 text-sm font-semibold border transition cursor-pointer ${n.status === "N" ? "border-red-500 bg-red-500 text-white" : "border-gray-300 bg-white text-gray-500"}`}>삭제
                                            </button>

                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="6" className="p-16 text-center text-gray-400">
                                    공지사항이 없습니다.
                                </td>
                            </tr>
                        )}
                    </tbody>

                </table>

            </div>

            <Pagination pageInfo={pageInfo} currentPage={currentPage} onChange={changePage} />
        </section>
    );
};

export default Notice;