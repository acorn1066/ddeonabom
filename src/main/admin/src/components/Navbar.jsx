import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <header className="sticky top-0 z-50 h-16 border-b bg-gray-900 text-white">
      <div className="flex h-full items-center justify-between px-6">

        {/* 로고 */}
        <Link to="/dashboard" className="flex items-center gap-2">
          <span className="text-xl font-bold">떠나봄</span>
          <span className="border-l border-white/20 pl-3 text-sm text-white/60">
            관리자
          </span>
        </Link>

        {/* 우측 메뉴 */}
        <div className="flex items-center gap-4">

          {/* 관리자 정보 */}
          <span className="text-sm text-white/60">
            관리자
          </span>

          {/* 메인 사이트 이동 */}
          <a
            href="http://localhost:8080"
            className="rounded-lg border border-white/20 px-4 py-2 text-sm hover:bg-white/10"
          >
            사이트로
          </a>

        </div>
      </div>
    </header>
  );
};

export default Navbar;