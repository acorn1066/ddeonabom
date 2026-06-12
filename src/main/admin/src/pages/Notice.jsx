import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const Notice = () => {
    const [notice, setNotice] = useState([])
    const navigate = useNavigate();
    const [keyword, setKeyword] = useState("");

    const handleSearch = () => {
        console.log("검색:", keyword);
    };

    useEffect(() => {
        fetch('/react/admin/notice')
            .then(data => setMembers(data))
            .catch(err => console.log(err))
    }, [])

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
                        {notice.map((notice) => (
                            <tr key={notice.noticeNo} className="border-b hover:bg-gray-50">

                                <td className="p-4 text-center">{notice.noticeNo}</td>
                                <td className="p-4">{notice.title}</td>
                                <td className="p-4 text-center">{notice.memberName}</td>
                                <td className="p-4 text-center">{notice.createDate?.split("T")[0]}</td>
                                <td className="p-4 text-center">{notice.modifyDate?.split("T")[0]}</td>
                                <td className="p-4 text-center">수정 / 삭제</td>
                            </tr>
                        ))}
                    </tbody>

                </table>

            </div>

        </section>
    );
};

export default Notice;