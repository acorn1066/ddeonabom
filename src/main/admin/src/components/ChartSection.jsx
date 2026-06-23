import { Chart } from "chart.js/auto";
import { useEffect, useRef } from "react";

const ChartSection = ({ title, data }) => {
    const canvasRef = useRef();
    const chartRef = useRef();

    const getDateFormat = (date) => {
        const year = (date.getFullYear() + "").slice(-2);
        const month = ("0" + (date.getMonth() + 1)).slice(-2);
        const day = ("0" + date.getDate()).slice(-2);
        return year + "-" + month + "-" + day;
    };

    const getThatDays = (today, n) => {
        return new Date(new Date().setDate(today.getDate() - n));
    };

    const sevenChart = (data) => {
       // console.log(canvasRef.current)
        //console.log(data)
        const today = new Date();
        const ctx = canvasRef.current;

        if (chartRef.current) chartRef.current.destroy();

        const labels = [6, 5, 4, 3, 2, 1, 0].map(n => getDateFormat(getThatDays(today, n)));

        // 배열 즉 게시판은 배열로 객체 즉 로그인 기록은 객체로
        const counts = labels.map(date => {
            if (Array.isArray(data)) {
                const found = data.find(item => item.CREATEDATE === date);
                return found ? Number(found.CNT) : 0;
            } else {
                return data[date] ?? 0;
            }
        });

        chartRef.current = new Chart(ctx, {
            type: "line",
            data: {
                labels,
                datasets: [{
                    data: counts,
                    lineTension: 0,
                    backgroundColor: "transparent",
                    borderColor: "#3b82f6",
                    borderWidth: 2,
                    pointBackgroundColor: "#3b82f6",
                }],
            },
            options: {
                plugins: {
                    legend: { display: false },
                    tooltip: { boxPadding: 3 },
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: { stepSize: 1 }
                    }
                }
            },
        });
    };

    useEffect(() => {
        if (!data) return;
        sevenChart(data);

        return () => {
            if (chartRef.current) chartRef.current.destroy();
        };
    }, [data]);

    return (
        <div className="rounded-xl bg-white p-6 shadow-sm border">
            <h4 className="mb-4 text-lg font-semibold">{title}</h4>
            <canvas ref={canvasRef} height="150"></canvas>
        </div>
    );
};

export default ChartSection;