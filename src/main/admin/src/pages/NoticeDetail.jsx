import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const NoticeDetail = () => {

    const { noticeNo } = useParams();

    const [notice, setNotice] = useState({
        noticeNo: "",
        title: "",
        content: "",
        memberName: "",
        createDate: "",
        modifyDate: "",
        status: "",
    });

    const navigate = useNavigate();

    useEffect(() => {
        fetch(`/react/admin/notice/${noticeNo}`)
            .then(res => res.json())
            .then(data => {
                setNotice(data);
            })
            .catch(err => console.log(err));
    }, [noticeNo]);


    return (
        <section className="flex-1 p-8">

            {/* 페이지 제목 */}
            <div className="mb-8">

                <h1 className="text-3xl font-bold">
                    공지사항 상세
                </h1>

                <p className="mt-2 text-gray-500">
                    공지사항 내용을 확인합니다.
                </p>

            </div>

            {/* 상세 카드 */}
            <div className="overflow-hidden rounded-2xl border bg-white shadow-md">

                {/* 제목 영역 */}
                <div className="border-b bg-gray-50 p-6">

                    <div className="mb-3">
                        <span
                            className={`rounded-full px-3 py-1 text-sm font-semibold
                            ${notice.status === "Y"
                                    ? "bg-green-100 text-green-700"
                                    : "bg-red-100 text-red-700"
                                }`}
                        >
                            {notice.status === "Y" ? "게시중" : "삭제됨"}
                        </span>
                    </div>

                    <h2 className="text-2xl font-bold">
                        {notice.title}
                    </h2>

                </div>

                {/* 정보 영역 */}
                <div className="grid grid-cols-2 gap-4 border-b p-6 text-sm text-gray-600">

                    <div>
                        <span className="font-semibold">
                            공지번호 :
                        </span>{" "}
                        {notice.noticeNo}
                    </div>

                    <div>
                        <span className="font-semibold">
                            작성자 :
                        </span>{" "}
                        {notice.memberName}
                    </div>

                    <div>
                        <span className="font-semibold">
                            작성일 :
                        </span>{" "}
                        {notice.createDate?.split("T")[0]}
                    </div>

                    <div>
                        <span className="font-semibold">
                            수정일 :
                        </span>{" "}
                        {notice.modifyDate
                            ? notice.modifyDate.split("T")[0]
                            : "-"}
                    </div>

                </div>

                {/* 내용 */}
                <div className="min-h-[400px] whitespace-pre-wrap p-8 leading-8">
                    {notice.content}
                </div>

            </div>

            {/* 버튼 영역 */}
            <div className="mt-8 flex justify-end gap-3">

                <button
                    onClick={() => navigate("/notice")}
                    className="cursor-pointer rounded-lg border px-5 py-2 hover:bg-gray-100"
                >
                    목록
                </button>

                <button
                    onClick={() => navigate(`/notice/edit/${notice.noticeNo}`)}
                    className="cursor-pointer rounded-lg bg-blue-600 px-5 py-2 text-white hover:bg-blue-700"
                >
                    수정
                </button>

            </div>

        </section>
    );
};

export default NoticeDetail;