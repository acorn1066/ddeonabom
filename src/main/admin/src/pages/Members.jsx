import { useEffect, useState } from "react";

const Members = () => {
    const [keyword, setKeyword] = useState("");
    const [members, setMembers] = useState([]);

    useEffect(() => {
        fetch("/react/admin/members", {
            credentials: "include",
        })
            .then((res) => res.json())
            .then((data) => {
                setMembers(data);
            })
            .catch(console.error);
    }, []);

    const handleSearch = () => {
        console.log("검색:", keyword);
        // 필요하면 API 연결
    };

    const changeStatus = (memberNo, status) => {
        fetch(
            `/react/admin/member/status?memberNo=${memberNo}&status=${status}`,
            {
                method: "POST",
                credentials: "include",
            }
        )
            .then(() => {
                setMembers((prev) =>
                    prev.map((member) =>
                        member.memberNo === memberNo
                            ? { ...member, status }
                            : member
                    )
                );
            })
            .catch(console.error);
    };

    return (
        <section className="flex-1 p-8">

            {/* 페이지 제목 */}
            <div className="mb-8">
                <h1 className="text-3xl font-bold">회원 관리</h1>
                <p className="mt-2 text-gray-500">회원 조회 및 관리</p>
            </div>

            {/* 검색 영역 */}
            <div className="mb-6 rounded-xl border bg-white p-6 shadow-sm">
                <h2 className="mb-4 text-lg font-semibold">회원 검색</h2>

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
                        className="cursor-pointer rounded-lg bg-blue-600 px-6 py-2 text-white hover:bg-blue-700"
                    >
                        검색
                    </button>
                </div>
            </div>

            {/* 회원 목록 */}
            <div className="rounded-xl border bg-white shadow-sm">

                {/* 제목 */}
                <div className="border-b p-4">
                    <h2 className="text-xl font-bold">회원 목록</h2>
                </div>

                {/* 헤더 */}
                <div className="grid grid-cols-12 border-b bg-gray-50 p-4 font-semibold">
                    <div className="col-span-1 text-center">번호</div>
                    <div className="col-span-5 text-center">아이디</div>
                    <div className="col-span-3 text-center">가입일</div>
                    <div className="col-span-3 text-center">관리</div>
                </div>

                {/* 데이터 */}
                {members.map((member) => (
                    <div
                        key={member.memberNo}
                        className="grid grid-cols-12 border-b p-4"
                    >
                        <div className="col-span-1 text-center">
                            {member.memberNo}
                        </div>

                        <div className="col-span-5 text-center">
                            {member.id}
                        </div>

                        <div className="col-span-3 text-center">
                            {member.enrollDate}
                        </div>

                        {/* 🔥 상태 버튼 영역 (수정 핵심) */}
                        <div className="col-span-3 flex items-center justify-center gap-2">

                            {/* 활동 버튼 */}
                            <button
                                onClick={() => changeStatus(member.memberNo, "Y")}
                                className={`px-3 py-1 rounded text-sm font-semibold border ${member.status?.trim() === "Y" ? "bg-green-500 text-white border-green-500" : "bg-gray-200 text-gray-500 border-gray-300"}`}
                            >
                                활동
                            </button>

                            {/* 정지 버튼 */}
                            <button
                                onClick={() => changeStatus(member.memberNo, "N")}
                                className={`px-3 py-1 rounded text-sm font-semibold border${member.status?.trim() === "N" ? "bg-red-500 text-white border-red-500" : "bg-gray-200 text-gray-500 border-gray-300"} `}
                            >
                                정지
                            </button>

                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
};

export default Members;