import { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";

export default function NoticeDetail() {
  const { noticeNo } = useParams();
  const navigate = useNavigate();

  const [notice, setNotice] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let ignore = false;

    async function fetchNotice() {
      setLoading(true);
      setError(null);
      try {
        const res = await fetch(`/api/notices/${noticeNo}`);
        if (!res.ok) throw new Error("공지사항을 불러오지 못했어요.");
        const data = await res.json();
        if (!ignore) setNotice(data);
      } catch (e) {
        if (!ignore) setError(e.message);
      } finally {
        if (!ignore) setLoading(false);
      }
    }

    fetchNotice();
    return () => {
      ignore = true;
    };
  }, [noticeNo]);

  if (loading) {
    return (
      <main className="mx-auto max-w-post px-6 pb-24 pt-12">
        <div className="animate-pulse space-y-4">
          <div className="h-4 w-32 rounded bg-sunk" />
          <div className="h-8 w-2/3 rounded bg-sunk" />
          <div className="h-4 w-40 rounded bg-sunk" />
          <div className="mt-6 h-40 w-full rounded bg-sunk" />
        </div>
      </main>
    );
  }

  if (error) {
    return (
      <main className="mx-auto max-w-post px-6 pb-24 pt-12">
        <p className="text-sm text-muted">{error}</p>
        <button
          onClick={() => navigate("/notices")}
          className="mt-4 inline-flex h-9 items-center rounded-lg border border-line px-4 text-sm font-semibold text-ink hover:border-brand hover:text-brand"
        >
          목록으로
        </button>
      </main>
    );
  }

  if (!notice) return null;

  return (
    <main className="mx-auto max-w-post px-6 pb-24 pt-12">
      {/* 브레드크럼 */}
      <div className="mb-5 text-13 text-muted">
        <Link to="/notices" className="hover:text-brand">
          공지사항
        </Link>
        {notice.category && <> › <span>{notice.category}</span></>}
      </div>

      <div className="border-b border-line pb-5">
        <div className="mb-3 flex items-center gap-2">
          {notice.pinned && (
            <span className="inline-flex h-7.5 items-center rounded-full bg-brand px-3 text-13 font-semibold text-white">
              고정
            </span>
          )}
          <span className="inline-flex h-7.5 items-center rounded-full border border-line px-3 text-13 font-medium text-muted">
            {notice.category ?? "공지"}
          </span>
        </div>

        <h1 className="mb-4 text-28 font-extrabold leading-snug tracking-tight">
          {notice.title}
        </h1>

        <div className="flex items-center gap-2.5">
          <span className="flex h-9.5 w-9.5 items-center justify-center rounded-full bg-sunk font-bold text-muted">
            {notice.writer?.[0] ?? "관"}
          </span>

          <div>
            <div className="text-sm font-bold">{notice.writer ?? "운영자"}</div>

            <div className="flex items-center gap-1 text-13 text-muted tabular-nums">
              <span>{formatDate(notice.createDate)}</span>
              <span>·</span>
              <span>조회 {notice.count}</span>
            </div>

            {notice.modifyDate && (
              <div className="flex items-center gap-1 text-13 text-muted tabular-nums">
                <span>{formatDate(notice.modifyDate)}</span>
                <span className="invisible">·</span>
                <span>수정됨</span>
              </div>
            )}
          </div>
        </div>
      </div>

      {/* 본문 */}
      <div
        className="py-6 text-base leading-loose text-ink/85"
        style={{ whiteSpace: "pre-wrap" }}
      >
        {notice.content}
      </div>

      {/* 첨부파일 (있을 때만) */}
      {notice.attachments?.length > 0 && (
        <div className="mb-6 rounded-xl border border-line bg-sunk/40 p-4">
          <div className="mb-2 text-13 font-bold text-muted">첨부파일</div>
          <ul className="space-y-1">
            {notice.attachments.map((file) => (
              <li key={file.id}>
                <a
                  href={file.url}
                  className="text-sm text-brand hover:underline"
                  download
                >
                  {file.name}
                </a>
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* 이전글 / 다음글 */}
      <div className="mt-2 divide-y divide-line border-y border-line text-sm">
        {notice.prev && (
          <Link
            to={`/notices/${notice.prev.noticeNo}`}
            className="flex items-center gap-3 py-3 hover:text-brand"
          >
            <span className="w-12 flex-none text-13 text-muted">다음글</span>
            <span className="truncate">{notice.prev.title}</span>
          </Link>
        )}
        {notice.next && (
          <Link
            to={`/notices/${notice.next.noticeNo}`}
            className="flex items-center gap-3 py-3 hover:text-brand"
          >
            <span className="w-12 flex-none text-13 text-muted">이전글</span>
            <span className="truncate">{notice.next.title}</span>
          </Link>
        )}
      </div>

      <div className="mt-6">
        <button
          onClick={() => navigate("/notices")}
          className="inline-flex h-9 items-center rounded-lg border border-line px-4 text-sm font-semibold text-ink hover:border-brand hover:text-brand"
        >
          목록으로
        </button>
      </div>
    </main>
  );
}

function formatDate(dateString) {
  if (!dateString) return "";
  const d = new Date(dateString);
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  return `${yyyy}.${mm}.${dd}`;
}