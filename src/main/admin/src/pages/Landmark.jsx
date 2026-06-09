/*  관
 *
 *  ※ 데이터 수집/업데이트는 setInterval 데모로 동작합니다.
 *    collect() / update() 안의 표시된 자리를 실제 fetch 로 교체하세요.
 */
import { useState, useRef, useEffect } from 'react'

/* ── 아이콘 ─────────────────────────────────────────────── */
const Icon = {
  pin: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.7" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <path d="M12 21s7-5.5 7-11a7 7 0 1 0-14 0c0 5.5 7 11 7 11Z" />
      <circle cx="12" cy="10" r="2.5" />
    </svg>
  ),
  download: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <path d="M12 3v12" /><path d="m7 11 5 5 5-5" /><path d="M5 21h14" />
    </svg>
  ),
  refresh: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <path d="M3 12a9 9 0 0 1 15-6.7L21 8" /><path d="M21 3v5h-5" />
      <path d="M21 12a9 9 0 0 1-15 6.7L3 16" /><path d="M3 21v-5h5" />
    </svg>
  ),
  clock: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.7" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <circle cx="12" cy="12" r="9" /><path d="M12 7v5l3 2" />
    </svg>
  ),
  bolt: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.7" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <path d="M13 2 4 14h7l-1 8 9-12h-7l1-8Z" />
    </svg>
  ),
}

/* 날짜 포맷: 2026.06.08 14:32 */
function fmt(d) {
  if (!d) return null
  const p = (n) => String(n).padStart(2, '0')
  return `${d.getFullYear()}.${p(d.getMonth() + 1)}.${p(d.getDate())} ${p(d.getHours())}:${p(d.getMinutes())}`
}

/* ── 상태 카드 ─────────────────────────────────────────── */
function StatCard({ label, value, caption, accent }) {
  const big = !value || value.length <= 7
  return (
    <div className="rounded-xl border border-line bg-white p-5 shadow-card">
      <div className="text-[13px] font-semibold text-muted">{label}</div>
      <div className={`my-2 font-extrabold tracking-[-0.03em] tabular-nums ${big ? 'text-[32px] leading-none' : 'text-2xl'} ${accent ? 'text-cobalt' : 'text-ink'}`}>
        {value ?? <span className="font-bold text-faint">—</span>}
      </div>
      <div className="text-[13px] text-faint">{caption}</div>
    </div>
  )
}

/* ── 관광지 관리 ───────────────────────────────────────── */
export default function Landmark() {
  const TOTAL_PAGES = 412   // TourAPI totalCount ÷ numOfRows
  const PER_PAGE = 100
  const DAILY_LIMIT = 1000

  // 실제로는 서버 상태로 초기화하세요 (여기서는 데모 시드값)
  const [count, setCount] = useState(9840)
  const [page, setPage] = useState(98)            // 마지막으로 완료한 페이지
  const [lastCollect, setLastCollect] = useState(new Date('2026-06-08T14:32:00'))
  const [lastUpdate, setLastUpdate] = useState(new Date('2026-06-05T03:10:00'))
  const [calls, setCalls] = useState(320)         // 오늘 사용한 호출 수

  const [busy, setBusy] = useState(null)          // 'collect' | 'update' | null
  const timer = useRef(null)

  const pct = TOTAL_PAGES ? Math.round((page / TOTAL_PAGES) * 100) : 0
  const callPct = Math.min(100, Math.round((calls / DAILY_LIMIT) * 100))
  const done = page >= TOTAL_PAGES

  useEffect(() => () => clearInterval(timer.current), [])

  // 이어서 수집 — 마지막 페이지 다음부터, 일일 호출 한도까지 끊어서
  function collect() {
    if (busy || done) return
    setBusy('collect')
    const budget = DAILY_LIMIT - calls
    const target = Math.min(TOTAL_PAGES, page + Math.min(60, budget))
    timer.current = setInterval(() => {
      setPage((p) => {
        if (p >= target) {
          clearInterval(timer.current)
          setBusy(null)
          setLastCollect(new Date())
          return p
        }
        setCount((c) => c + PER_PAGE)
        setCalls((k) => k + 1)
        return p + 1
      })
    }, 55)
    // ── 실제 구현 ──────────────────────────────────────
    // const res = await fetch('/api/landmarks/collect', { method: 'POST' })
    // const { count, page, calls } = await res.json()
    // setCount(count); setPage(page); setCalls(calls); setLastCollect(new Date()); setBusy(null)
  }

  // 전체 업데이트 — 수집된 전체 데이터를 최신으로 갱신
  function update() {
    if (busy) return
    setBusy('update')
    timer.current = setTimeout(() => {
      setBusy(null)
      setLastUpdate(new Date())
    }, 1800)
    // ── 실제 구현 ──────────────────────────────────────
    // await fetch('/api/landmarks/update', { method: 'POST' })
    // setLastUpdate(new Date()); setBusy(null)
  }

  return (
    <main className="mx-auto my-10 max-w-6xl px-6">

      {/* 페이지 헤더 */}
      <div className="mb-7 flex items-end justify-between gap-4">
        <div>
          <div className="mb-1.5 flex items-center gap-2 whitespace-nowrap text-[13px] font-semibold text-cobalt">
            <Icon.pin width="15" height="15" />
            한국관광공사 TourAPI
          </div>
          <h1 className="text-[28px] font-extrabold tracking-[-0.03em] text-ink">관광지 관리</h1>
          <p className="mt-1 text-sm text-muted">API 데이터 수집 및 업데이트</p>
        </div>
        <span className="inline-flex shrink-0 items-center gap-1.5 whitespace-nowrap rounded-full border border-line bg-white px-3 py-1.5 text-[12px] font-semibold text-ok shadow-card">
          <span className="h-1.5 w-1.5 rounded-full bg-ok" />
          API 연결됨
        </span>
      </div>

      {/* 상태 카드 */}
      <div className="mb-5 grid grid-cols-3 gap-4">
        <StatCard label="수집된 관광지" value={count ? count.toLocaleString() : null} caption="총 등록 수" accent />
        <StatCard label="마지막 수집" value={fmt(lastCollect)} caption={`${page} / ${TOTAL_PAGES} 페이지까지 완료`} />
        <StatCard label="마지막 업데이트" value={fmt(lastUpdate)} caption="전체 갱신 기준" />
      </div>

      {/* 수집 패널 */}
      <div className="rounded-xl border border-line bg-white p-6 shadow-card">
        <h2 className="text-[17px] font-bold text-ink">데이터 수집</h2>
        <p className="mt-1 max-w-xl text-sm leading-relaxed text-muted">
          API 호출 제한(하루 {DAILY_LIMIT.toLocaleString()}회)으로 인해 끊어서 수집해요.
          <span className="font-medium text-ink"> 이어서 수집</span>은 마지막 페이지 다음부터 시작합니다.
        </p>

        {/* 오늘 호출 사용량 */}
        <div className="mt-6 rounded-lg border border-line bg-sunk/60 px-4 py-3">
          <div className="flex items-center justify-between text-[13px]">
            <span className="font-semibold text-muted">오늘 호출 사용량</span>
            <span className="tabular-nums text-faint">
              <span className={calls >= DAILY_LIMIT ? 'font-bold text-cobalt' : 'font-semibold text-ink'}>{calls.toLocaleString()}</span>
              {' / '}{DAILY_LIMIT.toLocaleString()}회
            </span>
          </div>
          <div className="mt-2 h-1.5 w-full overflow-hidden rounded-full bg-line">
            <div className="h-full rounded-full bg-cobalt/40 transition-all duration-300" style={{ width: `${callPct}%` }} />
          </div>
        </div>

        {/* 진행 상황 */}
        <div className="mt-5">
          <div className="mb-2 flex items-center justify-between text-sm">
            <span className="font-semibold text-muted">수집 진행 상황</span>
            <span className="tabular-nums text-faint">
              <span className="font-semibold text-ink">{page.toLocaleString()}</span> / {TOTAL_PAGES.toLocaleString()} 페이지
              <span className="ml-2">({pct}%)</span>
            </span>
          </div>
          <div className="h-2.5 w-full overflow-hidden rounded-full bg-line">
            <div className="h-full rounded-full bg-cobalt transition-[width] duration-150 ease-out" style={{ width: `${pct}%` }} />
          </div>
        </div>

        {/* 버튼 */}
        <div className="mt-6 flex items-center gap-3">
          <button
            onClick={collect}
            disabled={busy || done}
            className="inline-flex h-11 items-center gap-2 rounded-lg bg-cobalt px-6 text-sm font-semibold text-white shadow-card transition hover:-translate-y-px hover:bg-cobalt-ink disabled:cursor-not-allowed disabled:opacity-50 disabled:hover:translate-y-0"
          >
            {busy === 'collect'
              ? <><Icon.refresh className="animate-spin" width="17" height="17" /> 수집 중…</>
              : done
                ? <><Icon.bolt width="17" height="17" /> 수집 완료</>
                : <><Icon.download width="17" height="17" /> 이어서 수집</>}
          </button>
          <button
            onClick={update}
            disabled={busy}
            className="inline-flex h-11 items-center gap-2 rounded-lg border border-line bg-white px-6 text-sm font-semibold text-ink transition hover:border-faint hover:bg-sunk disabled:cursor-not-allowed disabled:opacity-50"
          >
            {busy === 'update'
              ? <><Icon.refresh className="animate-spin" width="17" height="17" /> 갱신 중…</>
              : <><Icon.refresh width="17" height="17" /> 전체 업데이트</>}
          </button>

          {busy === 'collect' && (
            <span className="ml-1 inline-flex items-center gap-1.5 text-[13px] text-muted">
              <Icon.clock width="14" height="14" className="text-faint" />
              한 번에 최대 60페이지씩 수집해요
            </span>
          )}
          {done && busy !== 'collect' && (
            <span className="ml-1 text-[13px] font-medium text-ok">모든 페이지를 수집했어요 · 전체 업데이트로 갱신하세요</span>
          )}
        </div>
      </div>
    </main>
  )
}
