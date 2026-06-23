import { useEffect, useState } from "react";
import ChartSection from "../components/ChartSection";

const Dashboard = () => {

    const [stats, setStats] = useState({
        memberCount: 0,
        boardCount: 0,
        replyCount: 0,
        reportCount: 0,
    });

    const [activity, setActivity] = useState({
        schedule: null,
        qlist: null,
        travel: null,
    });

    useEffect(() => {
        fetch("/react/admin/dashboard", {
            credentials: "include",
        })
            .then(res => res.json())
            .then(data => {
                setStats(data);
            })
            .catch(err => console.log(err));

        fetch("/react/admin/activity", {
            credentials: "include"
        })
            .then(res => res.json())
            .then(data => setActivity(data))
            .catch(err => console.log(err));
    }, []);

    return (
        <section className="flex-1 p-8 bg-gray-100">

            <h1 className="mb-8 text-3xl font-bold">
                관리자 대시보드
            </h1>

            <div className="grid grid-cols-2 gap-6">

                {/* 총 회원 수 */}
                <div className="rounded-xl bg-white p-6 shadow-sm border">
                    <p className="text-gray-500">총 회원 수</p>
                    <p className="mt-3 text-4xl font-bold">
                        {stats.memberCount}
                    </p>
                </div>

                {/* 총 게시글 수 */}
                <div className="rounded-xl bg-white p-6 shadow-sm border">
                    <p className="text-gray-500">총 게시글 수</p>
                    <p className="mt-3 text-4xl font-bold">
                        {stats.boardCount}
                    </p>
                </div>

                {/* 총 댓글 수 */}
                <div className="rounded-xl bg-white p-6 shadow-sm border">
                    <p className="text-gray-500">총 댓글 수</p>
                    <p className="mt-3 text-4xl font-bold">
                        {stats.replyCount}
                    </p>
                </div>

                {/* 신고 건 수 */}
                <div className="rounded-xl bg-white p-6 shadow-sm border">
                    <p className="text-gray-500">신고 건 수</p>
                    <p className="mt-3 text-4xl font-bold text-red-500">
                        {stats.reportCount}
                    </p>
                </div>

            </div>

            <div className="mt-6 grid grid-cols-2 gap-6">
                <ChartSection title="일주일 로그인 활동" data={null} />
                <ChartSection title="일주일 공유게시판 활동" data={activity.schedule} />
                <ChartSection title="일주일 후기게시판 활동" data={activity.travel} />
                <ChartSection title="일주일 질문게시판 활동" data={activity.qlist} />
            </div>

        </section>
    );
};

export default Dashboard;