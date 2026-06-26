import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom"
import "./Page.css";
import Pagination from "../components/Pagination";

const Members = () => {
    const [members, setMembers] = useState([]);
    const [keyword, setKeyword] = useState("");

    const [searchParams, setSearchParams] = useSearchParams()
    const currentPage = parseInt(searchParams.get('page') || '1')

    const [pageInfo, setPageInfo] = useState(null)



    const fetchMembers = (page) => {
        fetch(`/react/admin/members?page=${page}&keyword=${keyword}`)
            .then(res => res.json())
            .then(data => {
                setMembers(data.list);
                setPageInfo(data.pi);
            });
    };

    const handleSearch = () => {
        if (currentPage === 1) {
            fetchMembers(1);
        } else {
            setSearchParams({ page: "1" });
        }
    };

    useEffect(() => {
        fetchMembers(currentPage);
    }, [currentPage]);

    const changePage = page => {
        setSearchParams({ page: page.toString() })
    }

    const handleStatusToggle = (member, newStatus) => {
        fetch("/react/admin/members", {
            method: "PATCH",
            headers: { "Content-Type": "application/json ; charset=UTF-8" },
            body: JSON.stringify({
                id: member.id,
                val: newStatus
            }),
        })
            .then((res) => res.json())
            .then((data) => {
                if (data === 1) {
                    setMembers((prev) =>
                        prev.map((m) =>
                            m.id === member.id
                                ? { ...m, status: newStatus }
                                : m
                        )
                    );
                }
            });
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
            <div className="mb-6 rounded-2xl border bg-white p-6 shadow-sm">
                <h2 className="mb-4 text-lg font-semibold">
                    회원 검색
                </h2>

                <div className="flex gap-3">
                    <input
                        type="text"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        onKeyDown={(e) => e.key === "Enter" && handleSearch()}
                        placeholder="아이디, 닉네임, 이메일 검색"
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

            {/* 회원 목록 */}
            <div className="overflow-hidden rounded-2xl border bg-white shadow-md">

                {/* 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">
                        회원 목록
                    </h2>
                </div>

                <table className="w-full table-auto">

                    {/* 헤더 */}
                    <thead>
                        <tr className="border-b bg-gray-100 text-gray-700">

                            <th className="p-4 text-center font-semibold">아이디</th>
                            <th className="p-4 text-center font-semibold">닉네임</th>
                            <th className="p-4 text-center font-semibold">이메일</th>
                            <th className="p-4 text-center font-semibold">전화번호</th>
                            <th className="p-4 text-center font-semibold">가입일</th>
                            <th className="p-4 text-center font-semibold"> 상태</th>

                        </tr>
                    </thead>

                    {/* 데이터 */}
                    <tbody>
                        {members.map((item) => (
                            <tr key={item.id} className="border-b transition hover:bg-gray-50">

                                <td className="p-4 text-center font-medium">{item.id}</td>
                                <td className="p-4 text-center">{item.nickname}</td>
                                <td className="max-w-xs p-4 text-center">{item.email}</td>
                                <td className="p-4 text-center">{item.phone ?? '-'}</td>
                                <td className="p-4 text-center text-sm text-gray-500">{item.enrollDate.split("T")[0]}</td>
                                <td className="p-4">
                                    <div className="flex justify-center gap-2">

                                        <button
                                            onClick={() =>
                                                item.status === "B"
                                                    ? handleStatusToggle(item, "Y")
                                                    : null
                                            }
                                            className={`rounded-lg px-4 py-1 text-sm font-semibold border transition ${item.status === "N" ? "cursor-not-allowed opacity-40" : "cursor-pointer"
                                                } ${item.status === "Y"
                                                    ? "border-green-500 bg-green-500 text-white"
                                                    : "border-gray-300 bg-white text-gray-500"
                                                }`}
                                        >
                                            활동
                                        </button>

                                        <button
                                            onClick={() =>
                                                item.status === "Y"
                                                    ? handleStatusToggle(item, "B")
                                                    : null
                                            }
                                            className={`rounded-lg px-4 py-1 text-sm font-semibold border transition ${item.status === "N" ? "cursor-not-allowed opacity-40" : "cursor-pointer"
                                                } ${item.status === "B"
                                                    ? "border-red-500 bg-red-500 text-white"
                                                    : "border-gray-300 bg-white text-gray-500"
                                                }`}
                                        >
                                            정지
                                        </button>

                                        <button
                                            disabled
                                            className={`rounded-lg px-4 py-1 text-sm font-semibold border cursor-not-allowed ${item.status === "N"
                                                ? "border-gray-500 bg-gray-500 text-white"
                                                : "border-gray-200 bg-white text-gray-300"
                                                }`}
                                        >
                                            탈퇴
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        ))}

                        {members.length === 0 && (
                            <tr>
                                <td
                                    colSpan="6"
                                    className="p-12 text-center text-gray-400"
                                >
                                    조회된 회원이 없습니다.
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

export default Members;