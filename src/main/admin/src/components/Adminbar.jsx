import { Link } from "react-router-dom";

const AdminBar = () => {
  return (
    <aside className="w-64 min-h-screen bg-white border-r shadow-sm">
      {/* 로고 */}
      <div className="p-6 border-b">
        <h1 className="text-2xl font-bold text-center">
          떠나봄 관리자
        </h1>
      </div>

      {/* 메뉴 */}
      <nav className="p-4">
        <ul className="space-y-2">
          <li>
            <Link
              to="/dashboard"
              className="block rounded-lg px-4 py-3 hover:bg-gray-100"
            >
              📊 대시보드
            </Link>
          </li>

          <li>
            <Link
              to="/notice"
              className="block rounded-lg px-4 py-3 hover:bg-gray-100"
            >
              📢 공지사항 관리
            </Link>
          </li>

          <li>
            <Link
              to="/members"
              className="block rounded-lg px-4 py-3 hover:bg-gray-100"
            >
              👤 회원 관리
            </Link>
          </li>

          <li>
            <Link
              to="/posts"
              className="block rounded-lg px-4 py-3 hover:bg-gray-100"
            >
              📝 게시글 관리
            </Link>
          </li>

          <li>
            <Link
              to="/report"
              className="block rounded-lg px-4 py-3 hover:bg-gray-100"
            >
              🚨 신고 관리
            </Link>
          </li>

          <li>
            <Link
              to="/landmark"
              className="block rounded-lg px-4 py-3 hover:bg-gray-100"
            >
              🏞️ 관광지 관리
            </Link>
          </li>
        </ul>
      </nav>
    </aside>
  );
};

export default AdminBar;