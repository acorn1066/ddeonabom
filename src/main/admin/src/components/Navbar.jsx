import { NavLink } from 'react-router-dom'

const Navbar = () => {
  return (
    <header className="sticky top-0 z-50 h-16 border-b border-white/10 bg-gray-900 text-white">
      <div className="mx-auto flex h-full max-w-7xl items-center justify-between px-6">
        <a href="/dashboard" className="flex items-center gap-2">
          <span className="text-xl font-extralight tracking-wide">떠나봄</span>
          <span className="ml-1 border-l border-white/20 pl-3 text-xs font-medium text-white/50">관리자</span>
        </a>
        <nav className="flex items-center gap-8 text-sm font-medium">
          <NavLink to="/dashboard"
            className={({ isActive }) => isActive
              ? 'relative py-2 font-semibold after:absolute after:-bottom-0.5 after:left-0 after:right-0 after:h-0.5 after:rounded after:bg-blue-500'
              : 'py-2 text-white/70 transition-colors hover:text-white'
            }>대시보드</NavLink>
          <NavLink to="/members"
            className={({ isActive }) => isActive
              ? 'relative py-2 font-semibold after:absolute after:-bottom-0.5 after:left-0 after:right-0 after:h-0.5 after:rounded after:bg-blue-500'
              : 'py-2 text-white/70 transition-colors hover:text-white'
            }>회원 관리</NavLink>
          <NavLink to="/posts"
            className={({ isActive }) => isActive
              ? 'relative py-2 font-semibold after:absolute after:-bottom-0.5 after:left-0 after:right-0 after:h-0.5 after:rounded after:bg-blue-500'
              : 'py-2 text-white/70 transition-colors hover:text-white'
            }>게시글 관리</NavLink>
          <NavLink to="/landmark"
            className={({ isActive }) => isActive
              ? 'relative py-2 font-semibold after:absolute after:-bottom-0.5 after:left-0 after:right-0 after:h-0.5 after:rounded after:bg-blue-500'
              : 'py-2 text-white/70 transition-colors hover:text-white'
            }>관광지 관리</NavLink>
        </nav>
        <div className="flex items-center gap-3">
          <span className="text-xs tabular-nums text-white/50">admin@pathfinder</span>
          <a href="/" className="inline-flex h-9 items-center rounded-lg border border-white/20 px-4 text-sm font-semibold text-white transition hover:bg-white/10">사이트로</a>
        </div>
      </div>
    </header>
  )
}

export default Navbar