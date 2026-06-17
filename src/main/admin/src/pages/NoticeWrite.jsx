import { useState } from "react";
import { useNavigate } from "react-router-dom";

const NoticeWrite = () => {

    const navigate = useNavigate();

    const [notice, setNotice] = useState({
        title: "",
        content: "",
    });

    const changeHandler = (e) => {
        setNotice({
            ...notice,
            [e.target.name]: e.target.value,
        });
    };

    const submitHandler = async (e) => {
        e.preventDefault();

        try {
            const res = await fetch("/react/admin/write/notice", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(notice),
            });

            if (res.ok) {
                alert("공지사항 등록 완료");
                navigate("/notice");
            }
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <section className="flex-1 p-8">

            <div className="mb-8">
                <h1 className="text-3xl font-bold">
                    공지사항 작성
                </h1>

                <p className="mt-2 text-gray-500">
                    새로운 공지사항을 등록합니다.
                </p>
            </div>

            <div className="rounded-xl border bg-white p-8 shadow-sm">

                <form onSubmit={submitHandler}>

                    <div className="mb-6">
                        <label className="mb-2 block font-semibold">
                            제목
                        </label>

                        <input
                            type="text"
                            name="title"
                            value={notice.title}
                            onChange={changeHandler}
                            placeholder="공지사항 제목을 입력하세요"
                            className="w-full rounded-lg border px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="mb-6">
                        <label className="mb-2 block font-semibold">
                            내용
                        </label>

                        <textarea
                            name="content"
                            rows="15"
                            value={notice.content}
                            onChange={changeHandler}
                            placeholder="공지사항 내용을 입력하세요"
                            className="w-full rounded-lg border px-4 py-3 resize-none focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="flex justify-end gap-3">

                        <button
                            type="submit"
                            className="rounded-lg bg-blue-600 px-6 py-2 text-white hover:bg-blue-700"
                        >
                            등록
                        </button>

                        <button
                            type="button"
                            onClick={() => navigate(-1)}
                            className="rounded-lg border px-6 py-2 hover:bg-gray-100"
                        >
                            취소
                        </button>

                    </div>

                </form>

            </div>

        </section>
    );
};

export default NoticeWrite;